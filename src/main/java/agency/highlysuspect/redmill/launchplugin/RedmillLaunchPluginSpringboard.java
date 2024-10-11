package agency.highlysuspect.redmill.launchplugin;

import agency.highlysuspect.redmill.CheekyGlobalState;
import agency.highlysuspect.redmill.Consts;
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
		Consts.LOG.warn("Hold on to your butts I'm about to do something really irresponsible");
		
		try {
			Field lphField = Launcher.class.getDeclaredField("launchPlugins");
			lphField.setAccessible(true);
			LaunchPluginHandler lph = (LaunchPluginHandler) lphField.get(Launcher.INSTANCE);
			
			Field pluginsField = LaunchPluginHandler.class.getDeclaredField("plugins");
			pluginsField.setAccessible(true);
			Map<String, ILaunchPluginService> plugins = (Map<String, ILaunchPluginService>) pluginsField.get(lph);
			
			Map<String, ILaunchPluginService> newPlugins = new LinkedHashMap<>(plugins);
			newPlugins.put(Consts.REDMILL_LAUNCH_PLUGIN_SERVICE, new RedmillLaunchPluginService());
			
			pluginsField.set(lph, newPlugins);
			
			Consts.LOG.info("Successfully performed the ol reddit switcheroo");
		} catch (Exception e) {
			throw new RuntimeException("Uh oh that's not good", e);
		}
	}
	
	@Override
	public void initialize(IEnvironment environment) {
		Path gameDir = environment.getProperty(IEnvironment.Keys.GAMEDIR.get())
			.orElseThrow(() -> new IllegalStateException("No gamedir?"));
		
		CheekyGlobalState.loadConfig(gameDir);
	}
	
	@Override
	public @NotNull List<? extends ITransformer<?>> transformers() {
		return List.of();
	}
}
