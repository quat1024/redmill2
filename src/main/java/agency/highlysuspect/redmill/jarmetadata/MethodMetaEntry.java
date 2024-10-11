package agency.highlysuspect.redmill.jarmetadata;

import agency.highlysuspect.redmill.util.StringInterner;
import com.google.gson.JsonObject;
import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Function;

public class MethodMetaEntry {
	//Class that defines this method
	String definer;
	
	//Uppermost class that defines the same method.
	//i.e. the top of the @Override chain.
	String trueOwner;
	
	String name;
	String desc;
	
	/**
	 * @apiNote does NOT fill trueOwner!
	 */
	public MethodMetaEntry(String definer, MethodNode node, StringInterner mem) {
		this.definer = definer;
		this.name = mem.intern(node.name);
		this.desc = mem.intern(node.desc);
	}
	
	public MethodMetaEntry(String definer, JsonObject json, StringInterner mem) {
		this.definer = definer;
		if(json.has("trueOwner")) {
			this.trueOwner = mem.intern(json.get("trueOwner").getAsString());
		} else {
			this.trueOwner = this.definer;
		}
		
		this.name = mem.intern(json.get("name").getAsString());
		this.desc = mem.intern(json.get("desc").getAsString());
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("name", name);
		json.addProperty("desc", desc);
		if(trueOwner != null && !trueOwner.equals(definer)) {
			json.addProperty("trueOwner", trueOwner);
		}
		return json;
	}
	
	//TODO: do I actually need to recurse into superinterfaces?
	// I think mappings files include copies of methods defined in interfaces
	public void resolveTrueOwner(Function<String, ClassMetaEntry> classResolver) {
		if(trueOwner != null) return;
		
		//first, assume the method is unique
		trueOwner = definer;
		
		//then crawl upwards looking for the highest superclass that defines the same method
		//note: we skip interfaces. in MCP, when a method that belongs to an interface is renamed,
		//the mapping is just repeated inside every single class that implements that interface.
		//so the "true owner" should be the highest *class*
		boolean foundInSuperclass = false;
		ClassMetaEntry cme = classResolver.apply(definer);
		while(cme != null) {
			MethodMetaEntry mme = cme.getMethod(name, desc);
			if(mme != null) {
				trueOwner = cme.name;
				foundInSuperclass = true;
				//but keep going
			}

			cme = classResolver.apply(cme.superclass);
		}
		
		//that said, sometimes the method really is defined in an interface
		if(!foundInSuperclass)
			return;
		
		Queue<ClassMetaEntry> queue = new ArrayDeque<>();
		queue.add(classResolver.apply(definer));

		while((cme = queue.poll()) != null) {
			MethodMetaEntry mme = cme.getMethod(name, desc);

			if(mme != null) {
				trueOwner = cme.name;
				//but keep going, since we're looking for the *highest* class in the hierarchy that defines this method
			}

			if(cme.superclass != null) {
				ClassMetaEntry superclass = classResolver.apply(cme.superclass);
				if(superclass != null) queue.add(superclass);
			}

			for(String iface : cme.superinterfaces) {
				ClassMetaEntry superinterface = classResolver.apply(iface);
				if(superinterface != null) queue.add(superinterface);
			}
		}
	}
}
