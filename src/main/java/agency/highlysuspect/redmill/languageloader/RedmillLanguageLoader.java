package agency.highlysuspect.redmill.languageloader;

import agency.highlysuspect.redmill.CheekyGlobalState;
import agency.highlysuspect.redmill.Consts;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingException;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.IModLanguageLoader;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

public class RedmillLanguageLoader implements IModLanguageLoader {
	@Override
	public String name() {
		return Consts.REDMILL_LANGUAGE_LOADER;
	}
	
	@Override
	public String version() {
		return "1337";
	}
	
	private static final Type CPW_MOD = Type.getObjectType("cpw/mods/fml/common/Mod");
	
	@Override
	public ModContainer loadMod(IModInfo info, ModFileScanData modFileScanResults, ModuleLayer layer) throws ModLoadingException {
		//tell RedmillLaunchPluginService about these classes
		modFileScanResults.getClasses().forEach(classData -> {
			CheekyGlobalState.PREMILLED_CLASSES.add(classData.clazz());
		});
		
		//find the old modid, and find the module the mod lives inside
		String oldModid = CheekyGlobalState.MODERN_TO_OLD_MODIDS.get(info.getModId());
		String moduleName = CheekyGlobalState.OLDID_TO_MODULE_NAME.get(oldModid); //several mods might share this module
		Module module = layer.findModule(moduleName).orElseThrow(() -> new RuntimeException("Couldn't find a module named " + moduleName));
		
		//find the applicable entrypoint
		String entrypoint = null;
		for(ModFileScanData.AnnotationData annotationData : modFileScanResults.getAnnotations()) {
			if(annotationData.annotationType().equals(CPW_MOD)) {
				Object oldModidMaybe = annotationData.annotationData().get("modid");
				if(oldModidMaybe instanceof String oldModid2 && oldModid2.equals(oldModid)) {
					System.out.println("APPLICABLE entrypoint ! " + annotationData.clazz().getClassName());
					entrypoint = annotationData.clazz().getClassName();
					break;
				}
			}
		}
		
		return new RedmillModContainer(info, entrypoint, module);
	}
}
