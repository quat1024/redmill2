package agency.highlysuspect.redmill.modfilereader;

import cpw.mods.jarhandling.JarContents;
import cpw.mods.jarhandling.SecureJar;
import net.neoforged.fml.loading.moddiscovery.ModFile;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.neoforgespi.language.*;
import net.neoforged.neoforgespi.locating.*;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public class McmodInfoModFileReader implements IModFileReader {
	@Override
	public @Nullable IModFile read(JarContents jar, ModFileDiscoveryAttributes attributes) {
		Optional<URI> mcmodInfoFile = jar.findFile("mcmod.info");
		if(mcmodInfoFile.isPresent()) {
			System.out.println("Got jar with MCMOD info: " + jar + " attrs " + attributes);
			
			try {
				//parse mcmod.info
				IConfigurable mcmodInfo = new McmodInfo(mcmodInfoFile.get().toURL().openStream());
				
				SecureJar sj = SecureJar.from(jar);
				MutableObject<ModFileInfo> bleh = new MutableObject<>(null);
				ModFile modFile = (ModFile) IModFile.create(sj, (__) -> bleh.getValue());
				
				//mod file info
				ModFileInfo modFileInfo = new ModFileInfo(modFile, mcmodInfo, (__) -> {}, List.of());
				bleh.setValue(modFileInfo);
				
				return modFile;
			} catch (Exception e) {
				throw new RuntimeException("Couldn't redmill a jar", e);
			}
		}
		
		return null;
	}
}
