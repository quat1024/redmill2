package agency.highlysuspect.redmill.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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
		
		assignClassNode(classNode, newNode);
	}
	
	static void assignClassNode(ClassNode target, ClassNode source) {
		for(Field field : ClassNode.class.getDeclaredFields()) {
			if(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) continue;
			
			try {
				field.setAccessible(true);
				field.set(target, field.get(source));
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
