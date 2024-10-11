package agency.highlysuspect.redmill.jarmetadata;

import agency.highlysuspect.redmill.util.Collectors2;
import agency.highlysuspect.redmill.util.StringDeduplicator;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.objectweb.asm.tree.ClassNode;

import java.util.*;
import java.util.stream.Collectors;

public class ClassMetaEntry {
	String name;
	ClassMetaEntry superclass;
	List<ClassMetaEntry> superinterfaces;
	
	List<MethodMetaEntry> methods;
	
	//TODO: linear scan
	public MethodMetaEntry getMethod(String name, String desc) {
		for(MethodMetaEntry m : methods) if(m.name.equals(name) && m.desc.equals(desc)) {
			return m;
		}
		return null;
	}
	
	public static void resolveAllTrueOwners(Map<String, ClassMetaEntry> classMeta) {
		classMeta.values().forEach(cme -> cme.methods.forEach(MethodMetaEntry::resolveTrueOwner));
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("name", name);
		
		if(superclass != null) json.addProperty("superName", superclass.name);
		
		json.add("superInterfaces", superinterfaces.stream()
			.map(cme -> cme.name)
			.collect(Collectors2.toJsonArray(JsonPrimitive::new)));
		
		json.add("methods", methods.stream()
			.map(MethodMetaEntry::toJson)
			.collect(Collectors2.toJsonArray()));
		return json;
	}
	
	public static class Pre {
		String name;
		String superName;
		List<String> superInterfaces;
		
		List<MethodMetaEntry.Pre> methods;
		
		private static Pre stub(String name) {
			Pre p = new Pre();
			p.name = name;
			p.superName = "java/lang/Object";
			p.superInterfaces = Collections.emptyList();
			p.methods = Collections.emptyList();
			return p;
		}
		
		public static Pre fromJson(JsonObject json, StringDeduplicator mem) {
			Pre p = new Pre();
			p.name = mem.dedup(json.get("name").getAsString());
			p.superName = mem.dedup(json.get("superName").getAsString());
			p.superInterfaces = json.getAsJsonArray("superInterfaces")
				.asList()
				.stream()
				.map(JsonElement::getAsString)
				.map(mem::dedup)
				.collect(Collectors.toList());
			p.methods = json.getAsJsonArray("methods")
				.asList()
				.stream()
				.map(JsonElement::getAsJsonObject)
				.map(obj -> MethodMetaEntry.Pre.fromJson(p, obj, mem))
				.collect(Collectors.toList());
			return p;
		}
		
		public static Pre fromClassNode(ClassNode node, StringDeduplicator mem) {
			Pre p = new Pre();
			p.name = mem.dedup(node.name);
			p.superName = mem.dedup(node.superName);
			p.superInterfaces = mem.dedupList(new ArrayList<>(node.interfaces));
			p.methods = node.methods.stream()
				.map(methodNode -> MethodMetaEntry.Pre.fromMethodNodePre(p.name, methodNode, mem))
				.collect(Collectors.toList());
			return p;
		}
		
		public static Map<String, ClassMetaEntry> upgradeAll(Map<String, Pre> pres) {
			Map<String, ClassMetaEntry> result = new HashMap<>();
			
			ClassMetaEntry javaLangObject = new ClassMetaEntry();
			javaLangObject.name = "java/lang/Object";
			javaLangObject.superclass = null;
			javaLangObject.superinterfaces = Collections.emptyList();
			javaLangObject.methods = Collections.emptyList();
			result.put(javaLangObject.name, javaLangObject);
			
			//1. construct all the ClassMetaEntries
			for(Pre pre : pres.values()) {
				ClassMetaEntry cme = new ClassMetaEntry();
				cme.name = pre.name;
				result.put(cme.name, cme);
			}
			
			//2. link everything together
			for(Pre pre : pres.values()) {
				ClassMetaEntry cme = result.get(pre.name);
				
				ClassMetaEntry superMetaEntry = result.get(pre.superName);
				
				//superMetaEntry belongs to a different jar :(
				if(superMetaEntry == null) superMetaEntry = javaLangObject;
				
				cme.superclass = superMetaEntry;
				cme.superinterfaces = pre.superInterfaces.stream()
					.map(result::get)
					.map(itf -> itf == null ? javaLangObject : itf)
					.collect(Collectors.toList());
				cme.methods = pre.methods.stream()
					.map(m -> m.upgrade(result::get))
					.collect(Collectors.toList());
			}
			
			return result;
		}
	}
}
