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

public class RFMLRelaunchLog implements IFMLRelaunchLog {
	public static IFMLRelaunchLog log = new RFMLRelaunchLog();
	static File minecraftHome;
	private static boolean configured;
//	private static Thread consoleLogThread;
//	private static PrintStream errCache;
	private LegacyLogger myLog;
//	private static FileHandler fileHandler;
	private static IFMLLogFormatter formatter;
	
	private RFMLRelaunchLog() {
	}
	
	private static void configureLogging() {
		//LegacyLogManager.getLogManager().reset();
		//LegacyLogger var0 = LegacyLogger.getLogger("global");
		//var0.setLevel(Level.OFF);
		log.set_myLog(LegacyLogger.getLogger("ForgeModLoader"));
		LegacyLogger var1 = LegacyLogger.getLogger("STDOUT");
		var1.setParent(log.get_myLog());
		LegacyLogger var2 = LegacyLogger.getLogger("STDERR");
		var2.setParent(log.get_myLog());
		log.get_myLog().setLevel(Level.ALL);
		log.get_myLog().setUseParentHandlers(false);
//		consoleLogThread = new Thread(new RFMLRelaunchLog$RConsoleLogThread((IFMLRelaunchLog.I1)null));
//		consoleLogThread.start();
		formatter = new RFMLLogFormatter();
		
//		try {
//			File var3 = new File(minecraftHome, RFMLRelauncher.get_logFileNamePattern());
//			fileHandler = new RFMLRelaunchLog$R1(var3.getPath(), 0, 3);
//		} catch (Exception var4) {
//		}
		
		resetLoggingHandlers();
//		errCache = System.err;
//		System.setOut(new PrintStream(new RFMLRelaunchLog$RLoggingOutStream(var1), true));
//		System.setErr(new PrintStream(new RFMLRelaunchLog$RLoggingOutStream(var2), true));
		configured = true;
	}
	
	private static void resetLoggingHandlers() {
		//RFMLRelaunchLog$RConsoleLogThread.get_wrappedHandler().setLevel(Level.parse(System.getProperty("fml.log.level", "INFO")));
		//log.myLog.addHandler(new RFMLRelaunchLog$RConsoleLogWrapper((IFMLRelaunchLog.I1)null));
		//RFMLRelaunchLog$RConsoleLogThread.get_wrappedHandler().setFormatter(formatter);
		//fileHandler.setLevel(Level.ALL);
		//fileHandler.setFormatter(formatter);
		//log.myLog.addHandler(fileHandler);
	}
	
	public static void loadLogConfiguration(File var0) {
		if (var0 != null && var0.exists() && var0.canRead()) {
			try {
				LegacyLogManager.getLogManager().readConfiguration(new FileInputStream(var0));
				resetLoggingHandlers();
			} catch (Exception var2) {
				log((Level)Level.SEVERE, (Throwable)var2, "Error reading logging configuration file %s", var0.getName());
			}
		}
		
	}
	
	public static void log(String var0, Level var1, String var2, Object... var3) {
		makeLog(var0);
		LegacyLogger.getLogger(var0).log(var1, String.format(var2, var3));
	}
	
	public static void log(Level var0, String var1, Object... var2) {
		if (!configured) {
			configureLogging();
		}
		
		log.get_myLog().log(var0, String.format(var1, var2));
	}
	
	public static void log(String var0, Level var1, Throwable var2, String var3, Object... var4) {
		makeLog(var0);
		LegacyLogger.getLogger(var0).log(var1, String.format(var3, var4), var2);
	}
	
	public static void log(Level var0, Throwable var1, String var2, Object... var3) {
		if (!configured) {
			configureLogging();
		}
		
		log.get_myLog().log(var0, String.format(var2, var3), var1);
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
	
	public LegacyLogger getLogger() {
		return this.get_myLog();
	}
	
	public static void makeLog(String var0) {
		LegacyLogger var1 = LegacyLogger.getLogger(var0);
		//var1.setParent(log.get_myLog());
	}
	
	public static IFMLRelaunchLog get_log() {
		return log;
	}
	
	public static void set_log(IFMLRelaunchLog var0) {
		log = var0;
	}
	
	public static File get_minecraftHome() {
		return minecraftHome;
	}
	
	public static void set_minecraftHome(File var0) {
		minecraftHome = var0;
	}
	
	public static boolean get_configured() {
		return configured;
	}
	
	public static void set_configured(boolean var0) {
		configured = var0;
	}
	
	@Deprecated(forRemoval = true)
	public static Thread get_consoleLogThread() {
		throw new UnsupportedOperationException("Red Mill: console log thread not implemented");
	}
	
	public static void set_consoleLogThread(Thread var0) {
		//consoleLogThread = var0;
	}
	
	public static PrintStream get_errCache() {
		//Red Mill: doesn't override System.err so the errCache is not required
		return System.err;
	}
	
	public static void set_errCache(PrintStream var0) {
		//errCache = var0;
	}
	
	public LegacyLogger get_myLog() {
		return this.myLog;
	}
	
	public void set_myLog(LegacyLogger var1) {
		this.myLog = var1;
	}
	
	@Deprecated(forRemoval = true)
	public static FileHandler get_fileHandler() {
		throw new UnsupportedOperationException("Red Mill: file handler not implemented");
		//return fileHandler;
	}
	
	public static void set_fileHandler(FileHandler var0) {
		//fileHandler = var0;
	}
	
	public static IFMLLogFormatter get_formatter() {
		return formatter;
	}
	
	public static void set_formatter(IFMLLogFormatter var0) {
		formatter = var0;
	}
}
