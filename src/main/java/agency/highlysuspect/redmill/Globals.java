package agency.highlysuspect.redmill;

import agency.highlysuspect.redmill.jarmetadata.RedmillJarMetadata;
import agency.highlysuspect.redmill.languageloader.RedmillModContainer;
import agency.highlysuspect.redmill.mcp.Members;
import agency.highlysuspect.redmill.mcp.Srg;
import agency.highlysuspect.redmill.util.StringInterner;
import com.google.common.collect.Sets;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
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
			minecraft147Meta = new RedmillJarMetadata(hierIn, mem);
			
			Members fields = new Members().read(fieldsIn, mem);
			Members methods = new Members().read(methodsIn, mem);
			Srg srg = new Srg().read(joinedIn, mem);
			minecraft147Srg = srg.named(fields, methods);
			
			minecraft147Srg.writeTo(Paths.get(".").resolve("redmill-dump").resolve("joined-named.srg"));
			
		} catch (Exception e) {
			throw mkRethrow(e, "Failed to load red mill metadata");
		}
	}
	
	/// class list ///
	
	public static final Set<Type> TO_BE_MILLED = Sets.newConcurrentHashSet();
	
	public static void classesToBeMilled(ModFileScanData scan, ModContainerExt ext) {
		scan.getClasses().forEach(classData -> {
			TO_BE_MILLED.add(classData.clazz());
			EXT_BY_CLASS_INTERNAL_NAME.put(classData.clazz().getInternalName(), ext);
		});
	}
	
	/// ModContainerExt ///
	
	private static final Map<String, ModContainerExt> EXT_BY_OLD_MODID = new ConcurrentHashMap<>();
	private static final Map<String, ModContainerExt> EXT_BY_NEW_MODID = new ConcurrentHashMap<>();
	private static final Map<String, ModContainerExt> EXT_BY_CLASS_INTERNAL_NAME = new ConcurrentHashMap<>();
	
	//TODO expose these if they are useful
	private static final Map<IModInfo, ModContainerExt> EXT_BY_MODINFO = new ConcurrentHashMap<>();
	private static final Map<ModContainer, ModContainerExt> EXT_BY_MODCONTAINER = new ConcurrentHashMap<>();
	
	public static void addModContainerExt(ModContainerExt e) {
		EXT_BY_OLD_MODID.put(e.oldModid, e);
		EXT_BY_NEW_MODID.put(e.modernModid, e);
	}
	
	public static void associateWithModInfo(ModContainerExt ext, IModInfo info) {
		EXT_BY_MODINFO.put(info, ext);
		ext.modernModInfo = info;
	}
	
	public static void associateWithModContainer(ModContainerExt ext, RedmillModContainer mc) {
		EXT_BY_MODCONTAINER.put(mc, ext);
		ext.modernModContainer = mc;
	}
	
	public static ModContainerExt getModContainerByOldId(String oldId) {
		return EXT_BY_OLD_MODID.get(oldId);
	}
	
	public static ModContainerExt getModContainerByNewId(String newId) {
		return EXT_BY_NEW_MODID.get(newId);
	}
	
	/**
	 * TODO: classnames should be associated with modfileinfos or something, not modcontainers
	 */
	@Deprecated
	public static ModContainerExt getModContainerByClassInternalName(String internalName) {
		return EXT_BY_CLASS_INTERNAL_NAME.get(internalName);
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
