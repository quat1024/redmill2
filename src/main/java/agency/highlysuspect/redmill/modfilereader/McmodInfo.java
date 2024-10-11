package agency.highlysuspect.redmill.modfilereader;

import agency.highlysuspect.redmill.Consts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.neoforged.fml.loading.moddiscovery.ModFile;
import net.neoforged.neoforgespi.language.IConfigurable;
import net.neoforged.neoforgespi.language.IModInfo;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class McmodInfo implements IConfigurable {
	@SuppressWarnings("Convert2Diamond")
	public McmodInfo(InputStream in) throws Exception {
		entries = new Gson().fromJson(new InputStreamReader(in), new TypeToken<List<McmodInfoEntry>>(){});
	}
	
	public final List<McmodInfoEntry> entries;
	
	/**
	 * @see net.neoforged.fml.loading.moddiscovery.ModFileInfo#ModFileInfo(ModFile, IConfigurable, Consumer)
	 */
	@Override
	public <T> Optional<T> getConfigElement(String... key) {
		return switch(key[0]) {
			case "modLoader" -> Optional.of((T) Consts.REDMILL_LANGUAGE_LOADER);
			case "loaderVersion" -> Optional.of((T) IModInfo.UNBOUNDED.toString());
			case "license" -> Optional.of((T) "Unknown (via Redmill)");
			default -> Optional.empty();
		};
	}
	
	@Override
	public List<? extends IConfigurable> getConfigList(String... key) {
		return switch(key[0]) {
			case "mods" -> entries;
			default -> List.of();
		};
	}
}
