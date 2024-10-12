package agency.highlysuspect.redmill;

import agency.highlysuspect.redmill.jarmetadata.RedmillJarMetadata;
import agency.highlysuspect.redmill.languageloader.RedmillModContainer;
import agency.highlysuspect.redmill.mcp.Members;
import agency.highlysuspect.redmill.mcp.Srg;
import agency.highlysuspect.redmill.util.StringInterner;
import net.neoforged.fml.loading.progress.StartupNotificationManager;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.locating.IModFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Globals {
	
	/// config ///
	
	public static RedmillConfig CFG = new RedmillConfig();
	
	public static void loadConfig(Path gameDir) {
		try {
			CFG = RedmillConfig.load(gameDir.resolve("config"));
		} catch (Exception e) {
			Consts.LOG.error("Failed to load redmill config. Using defaults", e);
			CFG = new RedmillConfig();
		}
	}
	
	/// hierarchy ///
	
	public static RedmillJarMetadata minecraft147Meta;
	public static Srg minecraft147Srg;
	
	static {
		StringInterner mem = new StringInterner();
		
		try(
			InputStream hierIn = Globals.class.getResourceAsStream("/redmill-stuff/minecraft-1.4.7-hier.json");
			InputStream fieldsIn = Globals.class.getResourceAsStream("/redmill-stuff/minecraft-1.4.7-fields.csv");
			InputStream methodsIn = Globals.class.getResourceAsStream("/redmill-stuff/minecraft-1.4.7-methods.csv");
			InputStream joinedIn = Globals.class.getResourceAsStream("/redmill-stuff/minecraft-1.4.7-joined.srg");
		) {
			StartupNotificationManager.addModMessage("(Red Mill) Loading metadata");
			minecraft147Meta = new RedmillJarMetadata(hierIn, mem);
			
			StartupNotificationManager.addModMessage("(Red Mill) Loading MCP data");
			Members fields = new Members().read(fieldsIn, mem);
			Members methods = new Members().read(methodsIn, mem);
			Srg srg = new Srg().read(joinedIn, mem);
			minecraft147Srg = srg.named(fields, methods);
		} catch (Exception e) {
			throw mkRethrow(e, "Failed to load red mill metadata");
		}
	}
	
	/// ModFileExt ///
	
	private static final Map<IModFile, ModFileExt> MOD_FILE_EXT = new ConcurrentHashMap<>();
	
	public static void addModFileExt(ModFileExt ext) {
		MOD_FILE_EXT.put(ext.modernModFile, ext);
	}
	
	public static ModFileExt getModFileExt(IModFile file) {
		return MOD_FILE_EXT.get(file);
	}
	
	/// ModContainerExt ///
	
	private static final Map<String, ModContainerExt> EXT_BY_OLD_MODID = new ConcurrentHashMap<>();
	private static final Map<String, ModContainerExt> EXT_BY_NEW_MODID = new ConcurrentHashMap<>();
	
	//TODO expose these if they are useful
	private static final Map<IModInfo, ModContainerExt> EXT_BY_MODINFO = new ConcurrentHashMap<>();
	
	public static void addModContainerExt(ModContainerExt e) {
		EXT_BY_OLD_MODID.put(e.oldModid, e);
		EXT_BY_NEW_MODID.put(e.modernModid, e);
	}
	
	public static void associateWithModInfo(ModContainerExt ext, IModInfo info) {
		EXT_BY_MODINFO.put(info, ext);
		ext.modernModInfo = info;
	}
	
	public static void associateWithModContainer(ModContainerExt ext, RedmillModContainer rmc) {
		ext.modernModContainer = rmc;
	}
	
	public static ModContainerExt getModContainerByOldId(String oldId) {
		return EXT_BY_OLD_MODID.get(oldId);
	}
	
	public static ModContainerExt getModContainerByNewId(String newId) {
		return EXT_BY_NEW_MODID.get(newId);
	}
	
	public static Collection<ModContainerExt> getAllModContainers() {
		return EXT_BY_NEW_MODID.values();
	}
	
	/// util ///
	
	//Forge screen usually doesn't display the full throwable message, this is an attempt to
	//show more of the error to the user
	public static RuntimeException mkRethrow(Throwable parent, String messagePrefix) {
		RuntimeException e = new RuntimeException(messagePrefix + ": " + parent.getMessage(), parent);
		//forge loves to swallow errors and only actually crash weeks later, so log it now
		Consts.LOG.error(e);
		return e;
	}
}
