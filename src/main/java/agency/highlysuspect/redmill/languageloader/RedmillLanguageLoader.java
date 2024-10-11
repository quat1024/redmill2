package agency.highlysuspect.redmill.languageloader;

import agency.highlysuspect.redmill.CheekyGlobalState;
import agency.highlysuspect.redmill.Consts;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingException;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.IModLanguageLoader;
import net.neoforged.neoforgespi.language.ModFileScanData;

public class RedmillLanguageLoader implements IModLanguageLoader {
	@Override
	public String name() {
		return Consts.REDMILL_LANGUAGE_LOADER;
	}
	
	@Override
	public String version() {
		return "1337";
	}
	
	@Override
	public ModContainer loadMod(IModInfo info, ModFileScanData modFileScanResults, ModuleLayer layer) throws ModLoadingException {
		modFileScanResults.getClasses().forEach(classData -> {
			System.out.println("PREMILLED CLASS: " + classData.clazz());
			CheekyGlobalState.PREMILLED_CLASSES.add(classData.clazz());
		});
		
		return new RedmillModContainer(info);
	}
}
