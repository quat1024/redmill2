package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.Side;
import net.neoforged.fml.loading.FMLEnvironment;

import java.io.File;
import java.nio.file.Paths;

public class FMLPreInitializationEvent {
	public Side getSide() {
		return Side.redmill$fromModern(FMLEnvironment.dist);
	}
	
	public File getSuggestedConfigurationFile() {
		//TODO put the modid here
		return Paths.get(".", "config", "SUGGESTED_CONFIG.cfg").toFile();
	}
}
