package agency.highlysuspect.redmill.modfilereader;

import agency.highlysuspect.redmill.CheekyGlobalState;
import agency.highlysuspect.redmill.Consts;
import agency.highlysuspect.redmill.ModContainerExt;
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
import java.util.List;
import java.util.Optional;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

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
					if(CheekyGlobalState.CFG.refuseCoremods && mainAttrs.getValue("FMLCorePlugin") != null) {
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
				
				//TODO: find a better spot for this?
				for(McmodInfoEntryConfig e : mcmodInfoConfig.entries) {
					CheekyGlobalState.addModContainerExt(new ModContainerExt(e, moduleId));
				}
				
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
				throw new RuntimeException("Couldn't initialize mcmod.info jar " + jar.getPrimaryPath(), e);
			}
		}
		
		return null;
	}
}
