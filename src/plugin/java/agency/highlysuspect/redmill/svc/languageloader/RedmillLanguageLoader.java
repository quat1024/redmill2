package agency.highlysuspect.redmill.svc.languageloader;

import agency.highlysuspect.redmill.svc.Consts;
import agency.highlysuspect.redmill.svc.Globals;
import agency.highlysuspect.redmill.svc.jarmetadata.RedmillJarMetadata;
import agency.highlysuspect.redmill.svc.launchplugin.RedmillLaunchPluginService;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingException;
import net.neoforged.neoforgespi.IIssueReporting;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.IModLanguageLoader;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.locating.IModFile;

import java.util.*;

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
		Consts.windowLog("Making mod container for {}", info.getModId());
		
		return new RedmillModContainer(info, modFileScanData, layer); // <-- does the real work
	}
	
	@Override
	public void validate(IModFile file, Collection<ModContainer> loadedContainers, IIssueReporting reporter) {
		validatedContainers.addAll(loadedContainers);
		
		if(validatedContainers.size() == Globals.getAllModContainers().size()) {
			//the last redmill mod has been loaded
			Consts.windowLog("Found all mods. Preparing transformer");
			
			//after all the mods are loaded, a complete classpath is available for resolveAllTrueOwners
			List<RedmillJarMetadata> allMetadata = Globals.getAllModFiles().stream()
				.map(mf -> mf.jarMetadata)
				.toList();
			for(RedmillJarMetadata meta : allMetadata) {
				Set<RedmillJarMetadata> resolveClasspath = new LinkedHashSet<>();
				resolveClasspath.add(Globals.minecraft147Meta);
				resolveClasspath.addAll(allMetadata);
				resolveClasspath.remove(meta); //omit myself
				
				meta.resolveAllTrueOwners(resolveClasspath);
			}
			
			RedmillLaunchPluginService.INSTANCE.doneModLoading(Globals.getAllModFiles(), Globals.getAllModContainers());
		}
	}
}
