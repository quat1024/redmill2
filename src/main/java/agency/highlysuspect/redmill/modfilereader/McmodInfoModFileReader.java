package agency.highlysuspect.redmill.modfilereader;

import agency.highlysuspect.redmill.CheekyGlobalState;
import cpw.mods.jarhandling.JarContents;
import cpw.mods.jarhandling.JarMetadata;
import cpw.mods.jarhandling.SecureJar;
import net.neoforged.fml.loading.moddiscovery.ModFile;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.neoforgespi.locating.*;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;

import java.lang.module.ModuleDescriptor;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class McmodInfoModFileReader implements IModFileReader {
	@Override
	public @Nullable IModFile read(JarContents jar, ModFileDiscoveryAttributes attributes) {
		Optional<URI> mcmodInfoFile = jar.findFile("mcmod.info");
		if(mcmodInfoFile.isPresent()) {
			System.out.println("Got jar with MCMOD info: " + jar + " attrs " + attributes);
			
			try {
				//parse mcmod.info
				McmodInfo mcmodInfo = new McmodInfo(mcmodInfoFile.get().toURL().openStream());
				String moduleId = mcmodInfo.entries.getFirst().getModernModid();
				
				//TODO: find a better spot for this?
				for(McmodInfoEntry e : mcmodInfo.entries) {
					CheekyGlobalState.MODERN_TO_OLD_MODIDS.put(e.getModernModid(), e.modid);
					CheekyGlobalState.OLDID_TO_MODULE_NAME.put(e.modid, moduleId);
				}
				
				//make sure to set the module id predictably, when making the sj
				//Attributes.Name blah = new Attributes.Name(moduleId);
				
				ModuleDescriptor desc = ModuleDescriptor.newOpenModule(moduleId)
					.packages(findPackages(jar.getPrimaryPath()))
					.build();
				
				SecureJar sj = SecureJar.from(jar, new JarMetaWithModuleId(desc));
				
				//circular dependency between these two classes
				MutableObject<ModFileInfo> bleh = new MutableObject<>(null);
				ModFile modFile = (ModFile) IModFile.create(sj, (__) -> bleh.getValue());
				ModFileInfo modFileInfo = new ModFileInfo(modFile, mcmodInfo, (__) -> {}, List.of());
				bleh.setValue(modFileInfo);
				
				return modFile;
			} catch (Exception e) {
				throw new RuntimeException("Couldn't redmill a jar", e);
			}
		}
		
		return null;
	}
	
	private record JarMetaWithModuleId(ModuleDescriptor desc) implements JarMetadata {
		@Override
		public String name() {
			return desc.name();
		}
		
		@Override
		public @Nullable String version() {
			return null;
		}
		
		@Override
		public ModuleDescriptor descriptor() {
			return desc;
		}
	}
	
	private Set<String> findPackages(Path path) {
		Set<String> pkgs = new HashSet<>();
		
		try(ZipInputStream zin = new ZipInputStream(Files.newInputStream(path))) {
			ZipEntry e;
			while((e = zin.getNextEntry()) != null) {
				if(e.isDirectory()) continue;
				
				String filename = e.getName();
				if(filename.endsWith(".class")) {
					int lastIndexOfSlash = filename.lastIndexOf('/');
					if(lastIndexOfSlash == -1) {
						System.out.println("Class " + filename + " in " + path + " is unpackaged!!");
						continue;
					}
					
					String packageName = filename.substring(0, lastIndexOfSlash).replace('/', '.');
					pkgs.add(packageName);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return pkgs;
	}
}
