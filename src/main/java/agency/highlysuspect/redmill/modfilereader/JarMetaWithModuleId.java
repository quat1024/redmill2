package agency.highlysuspect.redmill.modfilereader;

import cpw.mods.jarhandling.JarMetadata;
import org.jetbrains.annotations.Nullable;

import java.lang.module.ModuleDescriptor;

/**
 * TODO: is SimpleJarMetadata (from JarMetadata.from) better than this? It'll have services and stuff
 * @param desc
 */
public record JarMetaWithModuleId(ModuleDescriptor desc) implements JarMetadata {
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
