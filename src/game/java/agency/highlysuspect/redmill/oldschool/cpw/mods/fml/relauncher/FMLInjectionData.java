//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher;

import agency.highlysuspect.redmill.oldschool.net.minecraftforge.common.ForgeVersion;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IEnvironment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

public class FMLInjectionData {
	static File minecraftHome;
	static String major;
	static String minor;
	static String rev;
	static String build;
	static String mccversion;
	static String mcpversion;
	public static List containers = new ArrayList();
	
	public FMLInjectionData() {
	}
	
//	static void build(File var0, RelaunchClassLoader var1) {
//		minecraftHome = var0;
//		InputStream var2 = var1.getResourceAsStream("fmlversion.properties");
//		Properties var3 = new Properties();
//		if (var2 != null) {
//			try {
//				var3.load(var2);
//			} catch (IOException var5) {
//				FMLRelaunchLog.log(Level.SEVERE, var5, "Could not get FML version information - corrupted installation detected!", new Object[0]);
//			}
//		}
//
//		major = var3.getProperty("fmlbuild.major.number", "missing");
//		minor = var3.getProperty("fmlbuild.minor.number", "missing");
//		rev = var3.getProperty("fmlbuild.revision.number", "missing");
//		build = var3.getProperty("fmlbuild.build.number", "missing");
//		mccversion = var3.getProperty("fmlbuild.mcversion", "missing");
//		mcpversion = var3.getProperty("fmlbuild.mcpversion", "missing");
//	}
	public static void redmill$build() {
		minecraftHome = Launcher.INSTANCE.environment().getProperty(IEnvironment.Keys.GAMEDIR.get())
			.orElseThrow(() -> new IllegalStateException("No gamedir?"))
			.toFile();
		
		major = String.valueOf(ForgeVersion.getMajorVersion());
		minor = String.valueOf(ForgeVersion.getMinorVersion());
		rev = String.valueOf(ForgeVersion.getRevisionVersion());
		build = String.valueOf(ForgeVersion.getBuildVersion());
		mccversion = "1.4.7";
		mcpversion = "0";
	}
	
	public static Object[] data() {
		return new Object[]{major, minor, rev, build, mccversion, mcpversion, minecraftHome, containers};
	}
	
	public static File get_minecraftHome() {
		return minecraftHome;
	}
	
	public static void set_minecraftHome(File var0) {
		minecraftHome = var0;
	}
	
	public static String get_major() {
		return major;
	}
	
	public static void set_major(String var0) {
		major = var0;
	}
	
	public static String get_minor() {
		return minor;
	}
	
	public static void set_minor(String var0) {
		minor = var0;
	}
	
	public static String get_rev() {
		return rev;
	}
	
	public static void set_rev(String var0) {
		rev = var0;
	}
	
	public static String get_build() {
		return build;
	}
	
	public static void set_build(String var0) {
		build = var0;
	}
	
	public static String get_mccversion() {
		return mccversion;
	}
	
	public static void set_mccversion(String var0) {
		mccversion = var0;
	}
	
	public static String get_mcpversion() {
		return mcpversion;
	}
	
	public static void set_mcpversion(String var0) {
		mcpversion = var0;
	}
	
	public static List get_containers() {
		return containers;
	}
	
	public static void set_containers(List var0) {
		containers = var0;
	}
}
