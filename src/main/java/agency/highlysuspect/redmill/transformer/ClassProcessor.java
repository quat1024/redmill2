package agency.highlysuspect.redmill.transformer;

import org.objectweb.asm.tree.ClassNode;

import java.util.function.Consumer;

public interface ClassProcessor extends Consumer<ClassNode> {
	static ClassProcessor composite(ClassProcessor... processors) {
		return classNode -> {
			for(ClassProcessor processor : processors) {
				processor.accept(classNode);
			}
		};
	}
}
