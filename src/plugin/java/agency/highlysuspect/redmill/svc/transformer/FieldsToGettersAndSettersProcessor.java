package agency.highlysuspect.redmill.svc.transformer;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

/**
 * Rewrites all Minecraft (and Forge) field reads/writes to getters/setters. These are easier to override :)
 */
public class FieldsToGettersAndSettersProcessor implements ClassProcessor, Opcodes {
	@Override
	public void accept(ClassNode node) {
		for(MethodNode method : node.methods) {
			ListIterator<AbstractInsnNode> iter = method.instructions.iterator();
			while(iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				if(insn instanceof FieldInsnNode fieldNode && ClassProcessor.isMinecraftish(fieldNode.owner)) {
					switch(insn.getOpcode()) {
						case GETSTATIC -> {
							MethodInsnNode getter = new MethodInsnNode(
								INVOKESTATIC,
								fieldNode.owner,
								"get_" + fieldNode.name,
								"()" + fieldNode.desc,
								false
							);
							iter.remove();
							iter.add(getter);
						}
						case PUTSTATIC -> {
							MethodInsnNode setter = new MethodInsnNode(
								INVOKESTATIC,
								fieldNode.owner,
								"set_" + fieldNode.name,
								"(" + fieldNode.desc + ")V",
								false
							);
							iter.remove();
							iter.add(setter);
						}
						case GETFIELD -> {
							MethodInsnNode getter = new MethodInsnNode(
								INVOKEVIRTUAL,
								fieldNode.owner,
								"get_" + fieldNode.name,
								"()" + fieldNode.desc,
								false
							);
							iter.remove();
							iter.add(getter);
						}
						case PUTFIELD -> {
							MethodInsnNode setter = new MethodInsnNode(
								INVOKEVIRTUAL,
								fieldNode.owner,
								"set_" + fieldNode.name,
								"(" + fieldNode.desc + ")V",
								false
							);
							iter.remove();
							iter.add(setter);
						}
					}
				}
			}
		}
	}
}
