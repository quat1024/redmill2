package agency.highlysuspect.redmill.svc.transformer;

import agency.highlysuspect.redmill.svc.mcp.Srg;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.ClassNode;

public class McpClassNamingProcessor implements ClassProcessor {
	public McpClassNamingProcessor(Srg srg) {
		remapper = new Remapper() {
			@Override
			public String map(String internalName) {
				return srg.classMappings.getOrDefault(internalName, internalName);
			}
		};
	}
	
	private final Remapper remapper;
	
	@Override
	public void accept(ClassNode node) {
		ClassNode newNode = new ClassNode();
		node.accept(new ClassRemapper(newNode, remapper));
		ClassProcessor.assignClassNode(node, newNode);
	}
}
