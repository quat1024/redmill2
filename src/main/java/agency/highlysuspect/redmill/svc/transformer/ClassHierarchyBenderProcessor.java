package agency.highlysuspect.redmill.svc.transformer;

import agency.highlysuspect.redmill.svc.mcp.DescriptorMapper;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

/**
 * Does a bit:
 * - All INVOKEVIRTUALs on classes from Minecraft are rewritten to use a proxy interface.
 * - If a class implements an interface from Minecraft, it instead implements the proxy interface.
 * - All NEWs and INVOKESPECIALs on classes from Minecraft are rewritten to use a proxy class.
 * - If a class extends from a Minecraft class, it instead extends from the proxy class.
 * - Method descriptors are rewritten to use the interfaces.
 * - Fields are rewritten to use the interfaces.
 */
public class ClassHierarchyBenderProcessor implements ClassProcessor, Opcodes {
	private static boolean isMinecraftish(String owner) {
		return owner.startsWith("net/minecraft") || owner.startsWith("cpw");
	}
	
	private static String prefix2(String s, String prefix) {
		return prefix + s.replace("$", "$" + prefix);
	}
	
	private static String prefix(String internalName, String prefix) {
		int lastSlash = internalName.lastIndexOf('/');
		if(lastSlash == -1) return prefix2(internalName, prefix);
		
		String leadPart = internalName.substring(0, lastSlash);
		String trailingPart = internalName.substring(lastSlash + 1);
		return leadPart + "/" + prefix2(trailingPart, prefix);
	}
	
	private static String proxyInterfaceName(String internalName) {
		return "agency/highlysuspect/redmill/oldschool/" + prefix(internalName, "I");
	}
	
	private static String proxyClassName(String internalName) {
		return "agency/highlysuspect/redmill/oldschool/" + prefix(internalName, "R");
	}
	
	private static String proxyDescriptorToInterfaceName(String desc) {
		return DescriptorMapper.map(desc, cls -> {
			if(isMinecraftish(cls)) return proxyInterfaceName(cls);
			else return cls;
		});
	}
	
	private static String proxyDescriptorToClassName(String desc) {
		return DescriptorMapper.map(desc, cls -> {
			if(isMinecraftish(cls)) return proxyClassName(cls);
			else return cls;
		});
	}
	
	@Override
	public void accept(ClassNode node) {
		node.signature = null; //for decompilers (TODO remap it)
		
		//rewrite superclass
		if(isMinecraftish(node.superName)) {
			node.superName = proxyClassName(node.superName);
		}
		
		//rewrite interfaces
		node.interfaces.replaceAll(internalName -> {
			if(isMinecraftish(internalName)) return proxyInterfaceName(internalName);
			else return internalName;
		});
		
		//rewrite fields
		for(FieldNode field : node.fields) {
			field.desc = proxyDescriptorToInterfaceName(field.desc);
			field.signature = null; //for decompilers (TODO remap it)
			
			//rewrite annotations
			if(field.visibleAnnotations != null) for(AnnotationNode annotation : field.visibleAnnotations) {
				annotation.desc = proxyDescriptorToClassName(annotation.desc);
			}
		}
		
		//rewrite annotations
		if(node.visibleAnnotations != null) for(AnnotationNode annotation : node.visibleAnnotations) {
			annotation.desc = proxyDescriptorToClassName(annotation.desc);
		}
		
		//rewrite methods
		for(MethodNode method : node.methods) {
			//rewrite method arguments and return types
			method.desc = proxyDescriptorToInterfaceName(method.desc);
			
			//rewrite annotations
			if(method.visibleAnnotations != null) for(AnnotationNode annotation : method.visibleAnnotations) {
				annotation.desc = proxyDescriptorToClassName(annotation.desc);
			}
			
			//clear some decompiler gunk (TODO remap it)
			if(method.localVariables != null)
				method.localVariables.clear();
			method.signature = null;
			
			ListIterator<AbstractInsnNode> iter = method.instructions.iterator();
			while(iter.hasNext()) {
				//inside of the method
				AbstractInsnNode insn = iter.next();
				
				//rewrite LDC .class literals
				if(insn instanceof LdcInsnNode ldcNode) {
					if(ldcNode.cst instanceof Type ldcType &&
						ldcType.getSort() == Type.OBJECT &&
						isMinecraftish(ldcType.getInternalName())
					) {
						ldcNode.cst = Type.getObjectType(proxyClassName(ldcType.getInternalName()));
					}
				}
				
				//rewrite new, anewarray, instanceof, and checkcast
				//note that it's called .desc even though it's an internal name. just an asm wart
				else if(insn instanceof TypeInsnNode typeNode && isMinecraftish(typeNode.desc)) {
					if(typeNode.getOpcode() == NEW || typeNode.getOpcode() == ANEWARRAY) {
						typeNode.desc = proxyClassName(typeNode.desc);
					} else {
						typeNode.desc = proxyInterfaceName(typeNode.desc);
					}
				}
				
				//rewrite method invokes
				else if(insn instanceof MethodInsnNode methodNode) {
					methodNode.desc = proxyDescriptorToInterfaceName(methodNode.desc);
					
					//also rewrite the target of method calls into minecraft
					if(isMinecraftish(methodNode.owner)) {
						switch(methodNode.getOpcode()) {
							case INVOKESPECIAL, INVOKESTATIC -> {
								methodNode.owner = proxyClassName(methodNode.owner);
							}
							case INVOKEVIRTUAL, INVOKEINTERFACE -> {
								methodNode.owner = proxyInterfaceName(methodNode.owner);
								methodNode.setOpcode(INVOKEINTERFACE);
								methodNode.itf = true;
							}
						}
					}
				}
				
				//fields.
				//Field nodes into Minecraft have already be rewritten to getters/setters by a prev processor
				//so this'll just be non-minecraft field accesses. Also this time .desc is an actual descriptor
				else if(insn instanceof FieldInsnNode fieldNode) {
					fieldNode.desc = proxyDescriptorToInterfaceName(fieldNode.desc);
				}
			}
		}
	}
}
