package agency.highlysuspect.redmill;

import agency.highlysuspect.redmill.languageloader.RedmillModContainer;
import com.google.common.collect.Sets;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.nio.file.Path;
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
	
	/// class list ///
	
	public static final Set<Type> TO_BE_MILLED = Sets.newConcurrentHashSet();
	
	public static void classesToBeMilled(ModFileScanData scan) {
		scan.getClasses().forEach(classData -> TO_BE_MILLED.add(classData.clazz()));
	}
	
	/// ModContainerExt ///
	
	private static final Map<String, ModContainerExt> EXT_BY_OLD_MODID = new ConcurrentHashMap<>();
	private static final Map<String, ModContainerExt> EXT_BY_NEW_MODID = new ConcurrentHashMap<>();
	
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
}
