package agency.highlysuspect.redmill;

import agency.highlysuspect.redmill.jarmetadata.RedmillJarMetadata;
import agency.highlysuspect.redmill.modfilereader.McmodInfoEntryConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.IModInfo;

public class ModContainerExt {
	public ModContainerExt(McmodInfoEntryConfig e, String javaModuleName, RedmillJarMetadata jarMetadata) {
		this.oldModid = e.modid;
		this.modernModid = e.getModernModid();
		this.javaModuleName = javaModuleName;
		this.jarMetadata = jarMetadata;
	}
	
	public final String oldModid;
	public final String modernModid;
	public final String javaModuleName;
	public final RedmillJarMetadata jarMetadata;
	
	//see Globals for where these are set
	public IModInfo modernModInfo;
	public ModContainer modernModContainer;
}
