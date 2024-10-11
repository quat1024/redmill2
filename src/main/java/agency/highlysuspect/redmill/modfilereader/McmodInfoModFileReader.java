package agency.highlysuspect.redmill.modfilereader;

import agency.highlysuspect.redmill.Globals;
import agency.highlysuspect.redmill.Consts;
import agency.highlysuspect.redmill.ModContainerExt;
import agency.highlysuspect.redmill.jarmetadata.RedmillJarMetadata;
import agency.highlysuspect.redmill.util.StringInterner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.jarhandling.JarContents;
import cpw.mods.jarhandling.JarMetadata;
import cpw.mods.jarhandling.SecureJar;
import cpw.mods.jarhandling.impl.SimpleJarMetadata;
import net.neoforged.fml.loading.moddiscovery.ModFile;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModFileReader;
import net.neoforged.neoforgespi.locating.ModFileDiscoveryAttributes;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

@SuppressWarnings("UnstableApiUsage")
public class McmodInfoModFileReader implements IModFileReader {
	@Override
	public @Nullable IModFile read(JarContents jar, ModFileDiscoveryAttributes attributes) {
		Optional<URI> mcmodInfoFile = jar.findFile("mcmod.info");
		if(mcmodInfoFile.isPresent()) {
			Consts.LOG.info("Got jar with MCMOD info: {} attrs {}", jar.getPrimaryPath(), attributes);
			
			try {
				//does this have a coremod in it?
				//Note that jar.getManifest() is blank at this point in time, so read the file manually
				Optional<URI> manifestMf = jar.findFile("META-INF/MANIFEST.MF");
				if(manifestMf.isPresent()) {
					Manifest actualManifest = new Manifest(manifestMf.get().toURL().openStream());
					Attributes mainAttrs = actualManifest.getMainAttributes();
					if(Globals.CFG.refuseCoremods && mainAttrs.getValue("FMLCorePlugin") != null) {
						throw new IllegalStateException("Mod " + jar.getPrimaryPath() + " contains a coremod. This is not supported.");
					}
				}
				
				//parse mcmod.info
				McmodInfoConfig mcmodInfoConfig = new McmodInfoConfig(mcmodInfoFile.get().toURL().openStream());
				
				if(mcmodInfoConfig.entries.isEmpty()) {
					Consts.LOG.warn("Jar {} doesn't list any mods in its mcmod.info! Refusing to load it.", jar.getPrimaryPath());
					return null;
				}
				
				McmodInfoEntryConfig firstMod = mcmodInfoConfig.entries.getFirst();
				String moduleId = firstMod.getModernModid();
				String moduleVersion = firstMod.getModernVersion();
				
				//TODO: hoist this maybe? not sure where to put it lol
				StringInterner mem = new StringInterner();
				RedmillJarMetadata rmMeta = new RedmillJarMetadata(jar.getPrimaryPath(), mem);
				
				//TODO: find a better spot for this?
				for(McmodInfoEntryConfig e : mcmodInfoConfig.entries) {
					//TODO: RedmillJarMetadata belongs to the jar, not the mod container
					// should really define some sort of ModFileExt. this will do for now
					Globals.addModContainerExt(new ModContainerExt(e, moduleId, rmMeta));
				}
				
				//TODO: debug dumpin
				Path dir = Paths.get(".").resolve("redmill-dump");
				Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
				Files.createDirectories(dir);
				Files.writeString(dir.resolve(moduleId + ".json"), gson.toJson(rmMeta.toJson()));
				
				//create securejar
				JarMetadata jarMeta = new SimpleJarMetadata(moduleId, moduleVersion, jar::getPackages, jar.getMetaInfServices());
				SecureJar sj = SecureJar.from(jar, jarMeta);
				
				//circular dependency between these two classes by ModFileInfoParser
				MutableObject<ModFileInfo> bleh = new MutableObject<>(null);
				ModFile modFile = (ModFile) IModFile.create(sj, (__) -> bleh.getValue());
				ModFileInfo modFileInfo = new ModFileInfo(modFile, mcmodInfoConfig, __ -> {}, List.of());
				bleh.setValue(modFileInfo);
				
				return modFile;
			} catch (Exception e) {
				throw Globals.mkRethrow(e, "Couldn't initialize mcmod.info jar " + jar.getPrimaryPath());
			}
		}
		
		return null;
	}
}
