package agency.highlysuspect.redmill.jarmetadata;

import agency.highlysuspect.redmill.util.StringDeduplicator;
import com.google.gson.JsonObject;
import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Function;

public class MethodMetaEntry {
	//Class that defines this method
	ClassMetaEntry definer;
	
	String name;
	String desc;
	
	//Uppermost class that defines the same method.
	//i.e. the top of the @Override chain.
	ClassMetaEntry trueOwner;
	
	//TODO: do I actually need to recurse into superinterfaces?
	// I think mappings files include copies of methods defined in interfaces
	public void resolveTrueOwner() {
		if(trueOwner != null) return;
		
		//first, assume the true owner is me
		trueOwner = definer;
		
		Queue<ClassMetaEntry> queue = new ArrayDeque<>();
		queue.add(definer);
		
		ClassMetaEntry cme;
		while((cme = queue.poll()) != null) {
			MethodMetaEntry mme = cme.getMethod(name, desc);
			
			if(mme != null) {
				trueOwner = cme;
				//but keep going, since we're looking for the *highest* class in the hierarchy that defines this method
			}
			
			if(cme.superclass != null) queue.add(cme.superclass);
			if(cme.superinterfaces != null) queue.addAll(cme.superinterfaces);
		}
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("name", name);
		json.addProperty("desc", desc);
		if(trueOwner != null && !trueOwner.name.equals(definer.name)) {
			json.addProperty("trueOwner", trueOwner.name);
		}
		return json;
	}
	
	//easier to write in json.
	public static class Pre {
		public String definer;
		public String name;
		public String desc;
		public String trueOwner;
		
		public static Pre fromJson(ClassMetaEntry.Pre definer, JsonObject json, StringDeduplicator mem) {
			Pre pre = new Pre();
			pre.definer = definer.name;
			pre.name = mem.dedup(json.get("name").getAsString());
			pre.desc = mem.dedup(json.get("desc").getAsString());
			
			if(json.has("trueOwner")) {
				pre.trueOwner = mem.dedup(json.get("trueOwner").getAsString());
			} else {
				pre.trueOwner = pre.definer;
			}
			return pre;
		}
		
		public static Pre fromMethodNodePre(String definer, MethodNode node, StringDeduplicator mem) {
			Pre pre = new Pre();
			pre.definer = definer;
			pre.name = mem.dedup(node.name);
			pre.desc = mem.dedup(node.desc);
			pre.trueOwner = null;
			return pre;
		}
		
		public MethodMetaEntry upgrade(Function<String, ClassMetaEntry> inflater) {
			MethodMetaEntry mme = new MethodMetaEntry();
			mme.definer = inflater.apply(definer);
			mme.name = name;
			mme.desc = desc;
			mme.trueOwner = inflater.apply(trueOwner);
			return mme;
		}
	}
}
