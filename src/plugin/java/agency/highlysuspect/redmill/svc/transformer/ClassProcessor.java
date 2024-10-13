package agency.highlysuspect.redmill.svc.transformer;

import agency.highlysuspect.redmill.svc.Globals;
import org.objectweb.asm.tree.ClassNode;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;

public interface ClassProcessor extends Consumer<ClassNode> {
	static ClassProcessor composite(ClassProcessor... processors) {
		return classNode -> {
			for(ClassProcessor processor : processors) {
				try {
					processor.accept(classNode);
				} catch (Exception e) {
					throw Globals.mkRethrow(e, processor.getClass().getSimpleName() + " threw exception");
				}
			}
		};
	}
	
	static boolean isMinecraftish(String owner) {
		return owner.startsWith("net/minecraft") || owner.startsWith("cpw");
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
