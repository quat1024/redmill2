//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher;

import agency.highlysuspect.redmill.game.logging.LegacyLogManager;
import agency.highlysuspect.redmill.game.logging.LegacyLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.logging.FileHandler;
import java.util.logging.Level;

public interface IFMLRelaunchLog {
	public LegacyLogger getLogger();
	
	public LegacyLogger get_myLog();
	public void set_myLog(LegacyLogger var1);
}
