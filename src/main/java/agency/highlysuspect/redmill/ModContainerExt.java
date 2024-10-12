package agency.highlysuspect.redmill;

import agency.highlysuspect.redmill.languageloader.RedmillModContainer;
import agency.highlysuspect.redmill.modfilereader.McmodInfoEntryConfig;
import net.neoforged.neoforgespi.language.IModInfo;

public class ModContainerExt {
	public ModContainerExt(McmodInfoEntryConfig e) {
		this.oldModid = e.modid;
		this.modernModid = e.getModernModid();
	}
	
	public final String oldModid;
	public final String modernModid;
	
	//see Globals for where these are set
	public IModInfo modernModInfo;
	public RedmillModContainer modernModContainer;
}
