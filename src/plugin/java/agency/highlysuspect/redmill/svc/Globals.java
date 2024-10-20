package agency.highlysuspect.redmill.svc;

import agency.highlysuspect.redmill.svc.jarmetadata.RedmillJarMetadata;
import agency.highlysuspect.redmill.svc.languageloader.RedmillModContainer;
import agency.highlysuspect.redmill.svc.mcp.Members;
import agency.highlysuspect.redmill.svc.mcp.Srg;
import agency.highlysuspect.redmill.svc.util.StringInterner;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.locating.IModFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

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
	public static Srg leftoversSrg;
	
	public static List<Pattern> dontsplitPositive = new ArrayList<>();
	public static List<Pattern> dontsplitNegative = new ArrayList<>();
	
	static {
		StringInterner mem = new StringInterner();
		
		try(
			InputStream hierIn = Globals.class.getResourceAsStream("/redmill-stuff/minecraft-1.4.7-hier.json");
			InputStream fieldsIn = Globals.class.getResourceAsStream("/redmill-stuff/minecraft-1.4.7-fields.csv");
			InputStream methodsIn = Globals.class.getResourceAsStream("/redmill-stuff/minecraft-1.4.7-methods.csv");
			InputStream joinedIn = Globals.class.getResourceAsStream("/redmill-stuff/minecraft-1.4.7-joined.srg");
			InputStream leftoversIn = Globals.class.getResourceAsStream("/redmill-stuff/leftovers.srg");
			InputStream dontsplitIn = Globals.class.getResourceAsStream("/redmill-stuff/dontsplit.txt");
		) {
			Consts.windowLog("Loading metadata/SRG");
			minecraft147Meta = new RedmillJarMetadata(hierIn, mem);
			
			//TODO: testing
			//minecraft147Meta = new RedmillJarMetadata(Paths.get("./redmill-dump/client.jar"), mem);
			//minecraft147Meta.resolveAllTrueOwners(minecraft147Meta);
			//minecraft147Meta.dump(Paths.get("./redmill-dump/minecraft-1.4.7-hier.json"), false);
			
			Members fields = new Members().read(fieldsIn, mem);
			Members methods = new Members().read(methodsIn, mem);
			Srg srg = new Srg().read(joinedIn, mem);
			minecraft147Srg = srg.named(fields, methods);
			
			leftoversSrg = new Srg().read(leftoversIn, mem);
			
			//dontsplit
			Scanner foo = new Scanner(Objects.requireNonNull(dontsplitIn));
			while(foo.hasNextLine()) {
				String line = foo.nextLine().trim();
				if(line.isEmpty() || line.startsWith("#")) continue;
				
				if(line.startsWith("!")) dontsplitNegative.add(Pattern.compile(line.substring(1)));
				else dontsplitPositive.add(Pattern.compile(line));
			}
			
			Consts.windowLog("Done loading metadata/SRG");
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
	
	public static Collection<ModFileExt> getAllModFiles() {
		return MOD_FILE_EXT.values();
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
		StringBuilder message = new StringBuilder(messagePrefix).append(";\n");
		
		Throwable t = parent;
		while(t != null) {
			if(t.getMessage() != null) {
				message.append(t.getClass().getSimpleName())
					.append(": ")
					.append(t.getMessage())
					.append(";\n");
			}
			t = t.getCause();
		}
		
		RuntimeException e = new RuntimeException(message.toString(), parent);
		//forge loves to swallow errors and only actually crash weeks later, so log it now
		Consts.LOG.error(e);
		return e;
	}
}
