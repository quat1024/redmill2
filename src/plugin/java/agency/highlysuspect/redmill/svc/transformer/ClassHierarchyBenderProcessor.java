package agency.highlysuspect.redmill.svc.transformer;

import agency.highlysuspect.redmill.svc.mcp.DescriptorMapper;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.Collection;

/**
 * Ok, so, in the model, all classes from Minecraft and Forge are split into an oldschool interface
 * and an oldschool implementation class.
 * If the mod calls a method from Minecraft, it should call the interface.
 * If the mod instantiates a class from Minecraft, it should instantiate the implementation.
 *
 * So this processor supports this model with a bunch of rewriting:
 * - Classes extending Minecraft classes are rewritten to extend the implementation class instead
 * - Classes implementing Minecraft interfaces are rewritten to implement the interface instead
 * - Field, field loads, and field stores of Minecraft classes are rewritten to the interface
 * - NEW/ANEWARRAY are rewritten to the implementation class
 * - INSTANCEOF and CHECKCAST use the interface
 * - INVOKESTATIC and INVOKESPECIAL use the implementation
 * - INVOKEVIRTUALs into Minecraft are rewritten to INVOKEINTERFACE, naturally
 * - INVOKEINTERFACEs into Minecraft are rewritten to the interface
 * - Annotations are rewritten to the implementation
 */
public class ClassHierarchyBenderProcessor implements ClassProcessor, Opcodes {
	//Foo$Bar -> IFoo$IBar
	private static String prefix2(String s, String prefix) {
		return prefix + s.replace("$", "$" + prefix);
	}
	
	//net/minecraft/something/Foo$Bar -> net/minecraft/something/IFoo$IBar
	private static String prefix(String internalName, String prefix) {
		int lastSlash = internalName.lastIndexOf('/');
		if(lastSlash == -1) return prefix2(internalName, prefix); //unpackaged class (!)
		
		String leadPart = internalName.substring(0, lastSlash + 1); //"net/minecraft/something/"
		String trailingPart = internalName.substring(lastSlash + 1);
		return leadPart + prefix2(trailingPart, prefix);
	}
	
	//rewrites a class internal name to the INTERFACE
	private static String classToItf(String internalName) {
		return "agency/highlysuspect/redmill/oldschool/" + prefix(internalName, "I");
	}
	
	//rewrites a class internal name, IF it belongs to minecraft, to the INTERFACE
	private static String mcClassToItf(String internalName) {
		return ClassProcessor.isMinecraftish(internalName) ? classToItf(internalName) : internalName;
	}
	
	//rewrites all classes found in a descriptor that belong to minecraft to the INTERFACE
	private static String mcDescToItf(String desc) {
		return DescriptorMapper.map(desc, ClassHierarchyBenderProcessor::mcClassToItf);
	}
	
	//rewrites a class internal name to the PROXY
	private static String classToProxy(String internalName) {
		return "agency/highlysuspect/redmill/oldschool/" + prefix(internalName, "R");
	}
	
	//rewrites a class internal name, IF it belongs to minecraft, to the PROXY
	private static String mcClassToProxy(String internalName) {
		return ClassProcessor.isMinecraftish(internalName) ? classToProxy(internalName) : internalName;
	}
	
	//rewrites all classes found in a descriptor that belong to minecraft to the PROXY
	private static String mcDescToProxy(String desc) {
		return DescriptorMapper.map(desc, ClassHierarchyBenderProcessor::mcClassToProxy);
	}
	
	//similar for annotations; in asm the annotation list is usually null if there are no annotations
	private static void mcAnnotationsToProxy(@Nullable Collection<AnnotationNode> annotations) {
		if(annotations != null) annotations.forEach(a -> a.desc = mcDescToProxy(a.desc));
	}
	
	@Override
	public void accept(ClassNode node) {
		node.signature = null; //for decompilers (TODO remap it)
		
		//TODO just for processing forge.jar thru this...
		if(ClassProcessor.isForge(node.name)) {
			node.name = mcClassToProxy(node.name);
		}
		
		//rewrite superclass
		node.superName = mcClassToProxy(node.superName);
		
		//rewrite annotations
		mcAnnotationsToProxy(node.visibleAnnotations);
		mcAnnotationsToProxy(node.invisibleAnnotations);
		
		//rewrite implements
		node.interfaces.replaceAll(ClassHierarchyBenderProcessor::mcClassToItf);
		
		for(FieldNode field : node.fields) {
			//clear some decompiler gunk (TODO remap it? lol)
			field.signature = null;
			
			//rewrite field types
			field.desc = mcDescToItf(field.desc);
			
			//rewrite field annotations
			mcAnnotationsToProxy(field.visibleAnnotations);
			mcAnnotationsToProxy(field.invisibleAnnotations);
		}
		
		for(MethodNode method : node.methods) {
			//clear some decompiler gunk (TODO remap it? lol)
			if(method.localVariables != null)
				method.localVariables.clear();
			method.signature = null;
			
			//rewrite method arguments and return types
			method.desc = mcDescToItf(method.desc);
			
			//rewrite method annotations
			mcAnnotationsToProxy(method.visibleAnnotations);
			mcAnnotationsToProxy(method.invisibleAnnotations);
			
			for(AbstractInsnNode insn : method.instructions) {
				//inside of the method
				//rewrite LDC .class literals
				if(insn instanceof LdcInsnNode ldcNode && ldcNode.cst instanceof Type ldcType && ldcType.getSort() == Type.OBJECT) {
					ldcNode.cst = Type.getObjectType(mcClassToProxy(ldcType.getInternalName()));
				}
				
				//rewrite new, anewarray, instanceof, and checkcast
				//note that it's called .desc even though it's an internal name. just an asm wart
				else if(insn instanceof TypeInsnNode typeNode) {
					if(typeNode.getOpcode() == NEW || typeNode.getOpcode() == ANEWARRAY) {
						typeNode.desc = mcClassToProxy(typeNode.desc);
					} else {
						typeNode.desc = mcClassToItf(typeNode.desc);
					}
				}
				
				//rewrite method invokes
				else if(insn instanceof MethodInsnNode methodNode) {
					methodNode.desc = mcDescToItf(methodNode.desc);
					
					//also rewrite the target of method calls into minecraft
					if(ClassProcessor.isMinecraftish(methodNode.owner)) {
						switch(methodNode.getOpcode()) {
							case INVOKESPECIAL, INVOKESTATIC -> {
								methodNode.owner = classToProxy(methodNode.owner);
							}
							case INVOKEVIRTUAL, INVOKEINTERFACE -> {
								methodNode.owner = classToItf(methodNode.owner);
								methodNode.setOpcode(INVOKEINTERFACE);
								methodNode.itf = true;
							}
						}
					}
				}
				
				//field accesses and writes. Field accesses into Minecraft have already been rewritten
				//to getters/setters by a prev processor, so this'll just catch mods storing Minecraft classes in fields.
				//Also this time .desc is an actual descriptor
				else if(insn instanceof FieldInsnNode fieldNode) {
					fieldNode.owner = mcClassToProxy(fieldNode.owner); //TODO: for writing out forge
					fieldNode.desc = mcDescToItf(fieldNode.desc);
				}
			}
		}
	}
}
