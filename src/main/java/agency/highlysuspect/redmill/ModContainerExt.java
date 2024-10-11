package agency.highlysuspect.redmill;

import agency.highlysuspect.redmill.modfilereader.McmodInfoEntryConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.IModInfo;

public class ModContainerExt {
	public ModContainerExt(McmodInfoEntryConfig e, String javaModuleName) {
		this.oldModid = e.modid;
		this.modernModid = e.getModernModid();
		this.javaModuleName = javaModuleName;
	}
	
	public final String oldModid;
	public final String modernModid;
	public final String javaModuleName;
	
	//see Globals for where these are set
	public IModInfo modernModInfo;
	public ModContainer modernModContainer;
}
