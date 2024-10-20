//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

import agency.highlysuspect.redmill.game.logging.LegacyLogger;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.IFMLRelaunchLog;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.RFMLRelaunchLog;
import java.util.logging.Level;

public class RFMLLog implements IFMLLog {
	private static IFMLRelaunchLog coreLog = RFMLRelaunchLog.get_log();
	
	public RFMLLog() {
	}
	
	public static void log(String var0, Level var1, String var2, Object... var3) {
		IFMLRelaunchLog var10000 = coreLog;
		RFMLRelaunchLog.log(var0, var1, var2, var3);
	}
	
	public static void log(Level var0, String var1, Object... var2) {
		IFMLRelaunchLog var10000 = coreLog;
		RFMLRelaunchLog.log(var0, var1, var2);
	}
	
	public static void log(String var0, Level var1, Throwable var2, String var3, Object... var4) {
		IFMLRelaunchLog var10000 = coreLog;
		RFMLRelaunchLog.log(var0, var1, var2, var3, var4);
	}
	
	public static void log(Level var0, Throwable var1, String var2, Object... var3) {
		IFMLRelaunchLog var10000 = coreLog;
		RFMLRelaunchLog.log(var0, var1, var2, var3);
	}
	
	public static void severe(String var0, Object... var1) {
		log(Level.SEVERE, var0, var1);
	}
	
	public static void warning(String var0, Object... var1) {
		log(Level.WARNING, var0, var1);
	}
	
	public static void info(String var0, Object... var1) {
		log(Level.INFO, var0, var1);
	}
	
	public static void fine(String var0, Object... var1) {
		log(Level.FINE, var0, var1);
	}
	
	public static void finer(String var0, Object... var1) {
		log(Level.FINER, var0, var1);
	}
	
	public static void finest(String var0, Object... var1) {
		log(Level.FINEST, var0, var1);
	}
	
	public static LegacyLogger getLogger() {
		return coreLog.getLogger();
	}
	
	public static void makeLog(String var0) {
		IFMLRelaunchLog var10000 = coreLog;
		RFMLRelaunchLog.makeLog(var0);
	}
	
	public static IFMLRelaunchLog get_coreLog() {
		return coreLog;
	}
	
	public static void set_coreLog(IFMLRelaunchLog var0) {
		coreLog = var0;
	}
}
