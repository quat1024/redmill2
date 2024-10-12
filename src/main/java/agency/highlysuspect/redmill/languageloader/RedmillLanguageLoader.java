package agency.highlysuspect.redmill.languageloader;

import agency.highlysuspect.redmill.Consts;
import agency.highlysuspect.redmill.Globals;
import agency.highlysuspect.redmill.launchplugin.RedmillLaunchPluginService;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingException;
import net.neoforged.fml.loading.progress.StartupNotificationManager;
import net.neoforged.neoforgespi.IIssueReporting;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.IModLanguageLoader;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.locating.IModFile;

import java.util.ArrayList;
import java.util.Collection;

public class RedmillLanguageLoader implements IModLanguageLoader {
	Collection<ModContainer> validatedContainers = new ArrayList<>();
	
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
		StartupNotificationManager.addModMessage("(Red Mill) Loading " + info.getModId());
		
		return new RedmillModContainer(info, modFileScanData, layer); // <-- does the real work
	}
	
	@Override
	public void validate(IModFile file, Collection<ModContainer> loadedContainers, IIssueReporting reporter) {
		validatedContainers.addAll(loadedContainers);
		
		if(validatedContainers.size() == Globals.getAllModContainers().size()) {
			//the last redmill mod has been loaded
			StartupNotificationManager.addModMessage("(Red Mill) Loaded all mods, preparing transformer");
			RedmillLaunchPluginService.INSTANCE.doneModLoading(Globals.getAllModContainers());
		}
	}
}
