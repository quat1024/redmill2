package agency.highlysuspect.redmill.svc.launchplugin;

import agency.highlysuspect.redmill.svc.Globals;
import agency.highlysuspect.redmill.svc.Consts;
import cpw.mods.modlauncher.LaunchPluginHandler;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * You can't create your own ILaunchPluginServices without being on the BOOT layer, since that's where the
 * serviceloader looks. But ITransformationServices are loaded pretty early from the regular layer.
 * Use this to get the foot in the door.
 *
 * Transformation services aren't appropriate for us because they require specifying the complete set of classes
 * to transform up-front. I want to transform *every* class from *every* redmill mod.
 */
public class RedmillLaunchPluginSpringboard implements ITransformationService {
	@Override
	public @NotNull String name() {
		return Consts.REDMILL_TRANSFORMATION_SERVICE;
	}
	
	@Override
	public void onLoad(IEnvironment env, Set<String> otherServices) throws IncompatibleEnvironmentException {
		//This gets called a bit before initialize(), but the IEnvironment is more populated in initialize()
	}
	
	@Override
	public void initialize(IEnvironment environment) {
		Consts.LOG.info("RedmillLaunchPluginSpringboard#initialize. Finding gamedir");
		
		Path gameDir = environment.getProperty(IEnvironment.Keys.GAMEDIR.get())
			.orElseThrow(() -> new IllegalStateException("No gamedir?"));
		
		Consts.LOG.info("Loading config");
		Globals.loadConfig(gameDir);
		
		Consts.LOG.warn("Hold on to your butts, I'm about to do something really irresponsible");
		RedmillLaunchPluginService rlps = new RedmillLaunchPluginService();
		
		try {
			//grab the launch plugins map
			Field lphField = Launcher.class.getDeclaredField("launchPlugins");
			lphField.setAccessible(true);
			LaunchPluginHandler lph = (LaunchPluginHandler) lphField.get(Launcher.INSTANCE);
			Field pluginsField = LaunchPluginHandler.class.getDeclaredField("plugins");
			pluginsField.setAccessible(true);
			@SuppressWarnings("unchecked")
			Map<String, ILaunchPluginService> plugins = (Map<String, ILaunchPluginService>) pluginsField.get(lph);
			
			//add my plugin launch service
			Map<String, ILaunchPluginService> newPlugins = new LinkedHashMap<>(plugins);
			newPlugins.put(rlps.name(), rlps);
			
			//write it back
			pluginsField.set(lph, newPlugins);
		} catch (Exception e) {
			throw new RuntimeException("Red Mill failed to do hacky stuff: " + e.getMessage(), e);
		}
		
		Consts.LOG.info("Somehow didn't crash doing that. I'm kinda surprised. Anyway...");
	}
	
	@Override
	public @NotNull List<? extends ITransformer<?>> transformers() {
		return List.of();
	}
}
