package agency.highlysuspect.redmill.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public interface ByteLevelClassProcessor extends ClassProcessor {
	byte[] accept(ClassNode classNode, byte[] bytes);
	
	@Override
	default void accept(ClassNode classNode) {
		//don't think i need to compute frames; it needs classloading
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		classNode.accept(writer);
		
		byte[] oldBytes = writer.toByteArray();
		byte[] newBytes = accept(classNode, oldBytes);
		if(newBytes == null || newBytes == oldBytes)
			return; //no change
		
		ClassNode newNode = new ClassNode();
		ClassReader reader = new ClassReader(newBytes);
		reader.accept(newNode, 0);
		
		ClassProcessor.assignClassNode(classNode, newNode);
	}
}
