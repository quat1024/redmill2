package agency.highlysuspect.redmill.svc.transformer;

import agency.highlysuspect.redmill.svc.jarmetadata.RedmillJarMetadata;
import agency.highlysuspect.redmill.svc.mcp.Srg;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.ClassNode;

public class McpFieldMethodClassProcessor implements ClassProcessor {
	public McpFieldMethodClassProcessor(Srg srg, RedmillJarMetadata meta) {
		remapper = new Remapper() {
			@Override
			public String mapFieldName(String owner, String name, String descriptor) {
				while(owner != null) {
					String mappedField = srg.getFieldMapping(owner, name);
					if(mappedField != null) return mappedField;
					else owner = meta.getSuperclass(owner);
				}
				
				//oh well
				return name;
			}
			
			@Override
			public String mapMethodName(String owner, String name, String descriptor) {
				Srg.MethodEntry key = new Srg.MethodEntry(name, descriptor);
				
				while(owner != null) {
					Srg.MethodEntry mapped = srg.getMethodMapping(owner, key);
					if(mapped != null) return mapped.name;
					else owner = meta.getSuperclass(owner);
				}
				
				//oh well
				return name;
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
