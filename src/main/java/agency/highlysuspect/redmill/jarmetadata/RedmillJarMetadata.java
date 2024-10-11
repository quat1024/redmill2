package agency.highlysuspect.redmill.jarmetadata;

import agency.highlysuspect.redmill.util.Collectors2;
import agency.highlysuspect.redmill.util.StringInterner;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RedmillJarMetadata {
	public Map<String, ClassMetaEntry> classMeta = new HashMap<>();
	
	private RedmillJarMetadata() {}
	
	public RedmillJarMetadata(Path path, StringInterner mem) throws Exception {
		try(ZipInputStream zin = new ZipInputStream(Files.newInputStream(path))) {
			ZipEntry entry;
			while((entry = zin.getNextEntry()) != null) {
				if(!entry.isDirectory() && entry.getName().endsWith(".class")) {
					ClassReader reader = new ClassReader(zin);
					ClassNode node = new ClassNode();
					reader.accept(node, 0);
					
					ClassMetaEntry cme = new ClassMetaEntry(node, mem);
					classMeta.put(cme.name, cme);
				}
			}
		}
	}
	
	public RedmillJarMetadata(InputStream in, StringInterner mem) {
		this(new Gson().fromJson(new InputStreamReader(in), JsonArray.class), mem);
	}
	
	public RedmillJarMetadata(JsonElement json, StringInterner mem) {
		json.getAsJsonArray().forEach(e -> {
			ClassMetaEntry cme = new ClassMetaEntry(e, mem);
			classMeta.put(cme.name, cme);
		});
	}
	
	public JsonArray toJson() {
		return classMeta.values().stream()
			.map(ClassMetaEntry::toJson)
			.collect(Collectors2.toJsonArray());
	}
	
	public void resolveAllTrueOwners(RedmillJarMetadata... classpath) {
		Function<String, ClassMetaEntry> lookup = name -> {
			//first try myself
			ClassMetaEntry cme = classMeta.get(name);
			if(cme != null) return cme;
			
			//then try the classpath
			for(RedmillJarMetadata meta : classpath) {
				cme = meta.classMeta.get(name);
				if(cme != null) return cme;
			}
			
			//ah well
			return null;
		};
		
		classMeta.values().forEach(cme -> cme.methods.forEach(mme -> mme.resolveTrueOwner(lookup)));
	}
	
	public @Nullable String getSuperclass(String name) {
		ClassMetaEntry cme = classMeta.get(name);
		return cme == null ? null : cme.superclass;
	}
	
	public static RedmillJarMetadata composite(RedmillJarMetadata... others) {
		RedmillJarMetadata result = new RedmillJarMetadata();
		for(RedmillJarMetadata other : others) result.classMeta.putAll(other.classMeta);
		return result;
	}
}
