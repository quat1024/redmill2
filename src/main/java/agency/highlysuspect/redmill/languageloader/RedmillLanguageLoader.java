package agency.highlysuspect.redmill.languageloader;

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
	public ModContainer loadMod(IModInfo info, ModFileScanData modFileScanData, ModuleLayer layer) throws ModLoadingException {
		return new RedmillModContainer(info, modFileScanData, layer); // <-- does the real work
	}
}
