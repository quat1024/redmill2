package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.ISide;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.RSide;
import net.neoforged.fml.loading.FMLEnvironment;

import java.io.File;
import java.nio.file.Paths;

public class RFMLPreInitializationEvent implements IFMLPreInitializationEvent {
	public ISide getSide() {
		return RSide.redmill$fromModern(FMLEnvironment.dist);
	}
	
	public File getSuggestedConfigurationFile() {
		//TODO put the modid here
		return Paths.get(".", "config", "SUGGESTED_CONFIG.cfg").toFile();
	}
}
