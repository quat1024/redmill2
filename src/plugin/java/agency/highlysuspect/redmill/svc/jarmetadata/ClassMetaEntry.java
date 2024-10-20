package agency.highlysuspect.redmill.svc.jarmetadata;

import agency.highlysuspect.redmill.svc.util.Collectors2;
import agency.highlysuspect.redmill.svc.util.StringInterner;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.util.*;
import java.util.stream.Collectors;

public class ClassMetaEntry {
	public final String name;
	public final String superclass;
	public final List<String> superinterfaces;
	public final boolean isEnum;
	
	List<MethodMetaEntry> methods;
	
	public ClassMetaEntry(ClassNode node, StringInterner mem) {
		this.name = mem.intern(node.name);
		this.superclass = mem.intern(node.superName);
		this.superinterfaces = mem.dedupList(new ArrayList<>(node.interfaces));
		this.methods = node.methods.stream()
			.map(methodNode -> new MethodMetaEntry(this.name, methodNode, mem))
			.collect(Collectors.toList());
		this.isEnum = (node.access & Opcodes.ACC_ENUM) != 0;
	}
	
	public ClassMetaEntry(JsonElement jsonElem, StringInterner mem) {
		JsonObject json = jsonElem.getAsJsonObject();
		
		this.name = mem.intern(json.get("name").getAsString());
		this.superclass = mem.intern(json.get("superName").getAsString());
		this.superinterfaces = json.getAsJsonArray("superInterfaces")
			.asList()
			.stream()
			.map(JsonElement::getAsString)
			.map(mem::intern)
			.collect(Collectors.toList());
		this.methods = json.getAsJsonArray("methods")
			.asList()
			.stream()
			.map(JsonElement::getAsJsonObject)
			.map(obj -> new MethodMetaEntry(this.name, obj, mem))
			.collect(Collectors.toList());
		
		if(json.has("enum")) {
			this.isEnum = json.getAsJsonPrimitive("enum").getAsBoolean();
		} else {
			this.isEnum = false;
		}
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("name", name);
		
		if(superclass != null) json.addProperty("superName", superclass);
		
		json.add("superInterfaces", superinterfaces.stream()
			.collect(Collectors2.toJsonArray(JsonPrimitive::new)));
		
		json.add("methods", methods.stream()
			.map(MethodMetaEntry::toJson)
			.collect(Collectors2.toJsonArray()));
		
		if(isEnum) json.addProperty("enum", true);
		
		return json;
	}
	
	//TODO: linear scan
	public MethodMetaEntry getMethod(String name, String desc) {
		for(MethodMetaEntry m : methods) if(m.name.equals(name) && m.desc.equals(desc)) {
			return m;
		}
		return null;
	}
}
