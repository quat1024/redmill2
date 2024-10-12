package agency.highlysuspect.redmill.transformer;

import agency.highlysuspect.redmill.mcp.DescriptorMapper;
import org.objectweb.asm.Opcodes;
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
	
	private static String prefixWithI(String internalName) {
		int lastSlash = internalName.lastIndexOf('/');
		if(lastSlash == -1) return "I" + internalName;
		else return internalName.substring(0, lastSlash) + "/I" + internalName.substring(lastSlash + 1);
	}
	
	private static String proxyInterfaceName(String internalName) {
		return "agency/highlysuspect/redmill/oldschool/" + prefixWithI(internalName);
	}
	
	private static String proxyClassName(String internalName) {
		return "agency/highlysuspect/redmill/oldschool/" + internalName + "Proxy";
	}
	
	private static String proxyDescriptorToInterfaceName(String desc) {
		return DescriptorMapper.map(desc, cls -> {
			if(isMinecraftish(cls)) return proxyInterfaceName(cls);
			else return cls;
		});
	}
	
	@Override
	public void accept(ClassNode node) {
		if(isMinecraftish(node.superName)) {
			node.superName = proxyClassName(node.superName);
		}
		
		node.interfaces.replaceAll(internalName -> {
			if(isMinecraftish(internalName)) return proxyInterfaceName(internalName);
			else return internalName;
		});
		
		for(MethodNode method : node.methods) {
			method.desc = proxyDescriptorToInterfaceName(method.desc);
			
			ListIterator<AbstractInsnNode> iter = method.instructions.iterator();
			while(iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				
				//note that it's called .desc even though it's an internal name. just an asm wart
				if(insn instanceof TypeInsnNode typeNode && isMinecraftish(typeNode.desc)) {
					if(typeNode.getOpcode() == NEW || typeNode.getOpcode() == ANEWARRAY) {
						typeNode.desc = proxyClassName(typeNode.desc);
					} else {
						typeNode.desc = proxyInterfaceName(typeNode.desc);
					}
				}
				
				if(insn instanceof MethodInsnNode methodNode && isMinecraftish(methodNode.owner)) {
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
					
					methodNode.desc = proxyDescriptorToInterfaceName(methodNode.desc);
				}
				
				//Field nodes into Minecraft have already be rewritten to getters/setters
				//this'll just be local getters and setters. Also this time .desc is an actual descriptor
				if(insn instanceof FieldInsnNode fieldNode) {
					fieldNode.desc = proxyDescriptorToInterfaceName(fieldNode.desc);
				}
			}
		}
		
		for(FieldNode field : node.fields) {
			field.desc = proxyDescriptorToInterfaceName(field.desc);
		}
	}
}
