package agency.highlysuspect.redmill;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

public class RedmillConfig {
	public String modidPrefix = "";
	public boolean initMods = true;
	public boolean refuseCoremods = true;
	
	public static RedmillConfig load(Path configFolder) throws Exception {
		RedmillConfig config = new RedmillConfig();
		
		Files.createDirectories(configFolder);
		Path configFile = configFolder.resolve("redmill.properties");
		
		if(Files.notExists(configFile)) {
			Files.write(configFile, List.of(
				"# Red Mill Configuration",
				"",
				"# Prefix all modids with this.",
				"modid_prefix=",
				"",
				"# Whether to actually initialize @Mod entrypoints.",
				"init_mods=true",
				"",
				"# Whether to crash if a coremod is detected.",
				"# (Coremods do not work under Red Mill, and are not loaded.)",
				"refuse_coremods=true"
			));
		} else {
			Properties props = new Properties();
			props.load(Files.newBufferedReader(configFile));
			
			config.modidPrefix = props.getProperty("modid_prefix", config.modidPrefix);
			config.initMods = bool(props, "init_mods", config.initMods);
			config.refuseCoremods = bool(props, "refuse_coremods", config.refuseCoremods);
		}
		
		return config;
	}
	
	private static boolean bool(Properties properties, String name, boolean defaultValue) {
		return Boolean.parseBoolean(properties.getProperty(name, Boolean.toString(defaultValue)));
	}
}
