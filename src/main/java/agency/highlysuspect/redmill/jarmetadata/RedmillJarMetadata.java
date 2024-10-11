package agency.highlysuspect.redmill.jarmetadata;

import agency.highlysuspect.redmill.util.Collectors2;
import agency.highlysuspect.redmill.util.StringDeduplicator;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RedmillJarMetadata {
	public RedmillJarMetadata(Path path) throws Exception {
		StringDeduplicator mem = new StringDeduplicator();
		
		Map<String, ClassMetaEntry.Pre> preMap = new HashMap<>();
		try(ZipInputStream zin = new ZipInputStream(Files.newInputStream(path))) {
			ZipEntry entry;
			while((entry = zin.getNextEntry()) != null) {
				if(!entry.isDirectory() && entry.getName().endsWith(".class")) {
					ClassReader reader = new ClassReader(zin);
					ClassNode node = new ClassNode();
					reader.accept(node, 0);
					
					ClassMetaEntry.Pre cmePre = ClassMetaEntry.Pre.fromClassNode(node, mem);
					preMap.put(cmePre.name, cmePre);
				}
			}
		}
		
		classMeta = ClassMetaEntry.Pre.upgradeAll(preMap);
	}
	
	public RedmillJarMetadata(InputStream in) {
		this(new Gson().fromJson(new InputStreamReader(in), JsonArray.class));
	}
	
	public RedmillJarMetadata(JsonElement json) {
		StringDeduplicator mem = new StringDeduplicator();
		
		Map<String, ClassMetaEntry.Pre> preMap = new HashMap<>();
		json.getAsJsonArray().forEach(e -> {
			ClassMetaEntry.Pre cmePre = ClassMetaEntry.Pre.fromJson(e.getAsJsonObject(), mem);
			preMap.put(cmePre.name, cmePre);
		});
		
		classMeta = ClassMetaEntry.Pre.upgradeAll(preMap);
	}
	
	public Map<String, ClassMetaEntry> classMeta;
	
	public void resolveAllTrueOwners() {
		ClassMetaEntry.resolveAllTrueOwners(classMeta);
	}
	
	public JsonArray toJson() {
		return classMeta.values().stream()
			.map(ClassMetaEntry::toJson)
			.collect(Collectors2.toJsonArray());
	}
	
	public static class Pre {
		public Map<String, ClassMetaEntry.Pre> preMap = new HashMap<>();
		
		public Pre(Path path, StringDeduplicator mem) throws Exception {
			Map<String, ClassMetaEntry.Pre> preMap = new HashMap<>();
			try(ZipInputStream zin = new ZipInputStream(Files.newInputStream(path))) {
				ZipEntry entry;
				while((entry = zin.getNextEntry()) != null) {
					if(!entry.isDirectory() && entry.getName().endsWith(".class")) {
						ClassReader reader = new ClassReader(zin);
						ClassNode node = new ClassNode();
						reader.accept(node, 0);
						
						ClassMetaEntry.Pre cmePre = ClassMetaEntry.Pre.fromClassNode(node, mem);
						preMap.put(cmePre.name, cmePre);
					}
				}
			}
		}
		
		public Pre(JsonElement json, StringDeduplicator mem) {
			json.getAsJsonArray().forEach(e -> {
				ClassMetaEntry.Pre cmePre = ClassMetaEntry.Pre.fromJson(e.getAsJsonObject(), mem);
				preMap.put(cmePre.name, cmePre);
			});
		}
	}
}
