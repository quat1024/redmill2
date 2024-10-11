package agency.highlysuspect.redmill.transformer;

import agency.highlysuspect.redmill.Globals;
import org.objectweb.asm.tree.ClassNode;

import java.util.function.Consumer;

public interface ClassProcessor extends Consumer<ClassNode> {
	static ClassProcessor composite(ClassProcessor... processors) {
		return classNode -> {
			for(ClassProcessor processor : processors) {
				try {
					processor.accept(classNode);
				} catch (Exception e) {
					throw Globals.mkRethrow(e, processor.getClass().getSimpleName() + "threw exception");
				}
			}
		};
	}
}
