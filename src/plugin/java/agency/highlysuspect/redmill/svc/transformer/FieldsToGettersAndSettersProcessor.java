package agency.highlysuspect.redmill.svc.transformer;

import agency.highlysuspect.redmill.svc.jarmetadata.RedmillJarMetadata;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

/**
 * Rewrites all Minecraft (and Forge) field reads/writes to getters/setters. These are easier to override :)
 */
public class FieldsToGettersAndSettersProcessor implements ClassProcessor, Opcodes {
	public FieldsToGettersAndSettersProcessor(RedmillJarMetadata meta) {
		this.meta = meta;
	}
	
	private final RedmillJarMetadata meta;
	
	@Override
	public void accept(ClassNode node) {
		for(MethodNode method : node.methods) {
			boolean isInitializer = method.name.startsWith("<"); //init, clinit
			boolean isStatic = (method.access & ACC_STATIC) != 0;
			boolean initOrStatic = isInitializer || isStatic;
			
			ListIterator<AbstractInsnNode> iter = method.instructions.iterator();
			while(iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				if(insn instanceof FieldInsnNode fieldNode && ClassProcessor.isMinecraftish(fieldNode.owner)) {
					
					//work with my own fields without using getters/setters
					if(initOrStatic && fieldNode.owner.equals(node.name)) {
						continue;
					}
					
					switch(insn.getOpcode()) {
						case GETSTATIC -> {
							//don't write getters for enum constants
							if(meta.isEnum(fieldNode.owner)) {
								continue;
							}
							
							MethodInsnNode getter = new MethodInsnNode(
								INVOKESTATIC, fieldNode.owner,
								"get_" + fieldNode.name, "()" + fieldNode.desc, false
							);
							iter.remove();
							iter.add(getter);
						}
						case PUTSTATIC -> {
							MethodInsnNode setter = new MethodInsnNode(
								INVOKESTATIC, fieldNode.owner,
								"set_" + fieldNode.name, "(" + fieldNode.desc + ")V", false
							);
							iter.remove();
							iter.add(setter);
						}
						case GETFIELD -> {
							MethodInsnNode getter = new MethodInsnNode(
								INVOKEVIRTUAL, fieldNode.owner,
								"get_" + fieldNode.name, "()" + fieldNode.desc, false
							);
							iter.remove();
							iter.add(getter);
						}
						case PUTFIELD -> {
							MethodInsnNode setter = new MethodInsnNode(
								INVOKEVIRTUAL, fieldNode.owner,
								"set_" + fieldNode.name, "(" + fieldNode.desc + ")V", false
							);
							iter.remove();
							iter.add(setter);
						}
					}
				}
			}
		}
		
		//WRITE getters and setters (only for minecraft classes)
		if(ClassProcessor.isMinecraftish(node.name)) {
			for(FieldNode field : node.fields) {
				boolean isStatic = (field.access & ACC_STATIC) != 0;
				boolean isFinal = (field.access & ACC_FINAL) != 0;
				
				Type fieldType = Type.getType(field.desc);
				
				if(isStatic) {
					MethodNode getter = new MethodNode(
						ASM9, ACC_PUBLIC | ACC_STATIC,
						"get_" + field.name,
						"()" + fieldType.getDescriptor(),
						null, null
					);
					
					getter.instructions.add(new FieldInsnNode(GETSTATIC,
						node.name, field.name, fieldType.getDescriptor()));
					getter.instructions.add(new InsnNode(fieldType.getOpcode(IRETURN)));
					
					getter.maxStack = 1;
					getter.maxLocals = 0;
					
					node.methods.add(getter);
					
					if(!isFinal) {
						MethodNode setter = new MethodNode(
							ASM9, ACC_PUBLIC | ACC_STATIC,
							"set_" + field.name,
							"(" + fieldType.getDescriptor() + ")V",
							null, null
						);
						
						setter.instructions.add(new VarInsnNode(fieldType.getOpcode(ILOAD), 0)); //arg
						setter.instructions.add(new FieldInsnNode(PUTSTATIC,
							node.name, field.name, fieldType.getDescriptor()));
						setter.instructions.add(new InsnNode(RETURN));
						
						setter.maxStack = setter.maxLocals = 1;
						
						node.methods.add(setter);
					}
				} else {
					MethodNode getter = new MethodNode(
						ASM9, ACC_PUBLIC,
						"get_" + field.name,
						"()" + fieldType.getDescriptor(),
						null, null
					);
					
					getter.instructions.add(new VarInsnNode(ALOAD, 0));
					getter.instructions.add(new FieldInsnNode(GETFIELD,
						node.name, field.name, fieldType.getDescriptor()));
					getter.instructions.add(new InsnNode(fieldType.getOpcode(IRETURN)));
					
					getter.maxStack = getter.maxLocals = 1;
					
					node.methods.add(getter);
					
					if(!isFinal) {
						MethodNode setter = new MethodNode(
							ASM9, ACC_PUBLIC,
							"set_" + field.name,
							"(" + fieldType.getDescriptor() + ")V",
							null, null
						);
						
						setter.instructions.add(new VarInsnNode(ALOAD, 0)); //this
						setter.instructions.add(new VarInsnNode(fieldType.getOpcode(ILOAD), 1)); //arg
						setter.instructions.add(new FieldInsnNode(PUTFIELD,
							node.name, field.name, fieldType.getDescriptor()));
						setter.instructions.add(new InsnNode(RETURN));
						
						setter.maxStack = setter.maxLocals = 2;
						
						node.methods.add(setter);
					}
				}
			}
		}
	}
}
