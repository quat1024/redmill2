package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.ISide;

import java.io.File;

public interface IFMLPreInitializationEvent {
	ISide getSide();
	File getSuggestedConfigurationFile();
}
