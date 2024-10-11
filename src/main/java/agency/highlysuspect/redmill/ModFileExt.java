package agency.highlysuspect.redmill;

import agency.highlysuspect.redmill.jarmetadata.RedmillJarMetadata;
import net.neoforged.neoforgespi.locating.IModFile;

public class ModFileExt {
	public ModFileExt(IModFile modernModFile, String javaModuleName, RedmillJarMetadata jarMetadata) {
		this.modernModFile = modernModFile;
		this.javaModuleName = javaModuleName;
		this.jarMetadata = jarMetadata;
	}
	
	public final IModFile modernModFile;
	public final String javaModuleName;
	public final RedmillJarMetadata jarMetadata;
}
