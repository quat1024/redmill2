package agency.highlysuspect.redmill.svc.modfilereader;

import agency.highlysuspect.redmill.svc.Globals;
import agency.highlysuspect.redmill.svc.Consts;
import agency.highlysuspect.redmill.svc.ModContainerExt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.neoforged.fml.loading.moddiscovery.ModFile;
import net.neoforged.neoforgespi.language.IConfigurable;
import net.neoforged.neoforgespi.language.IModInfo;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public class McmodInfoConfig implements IConfigurable {
	@SuppressWarnings("Convert2Diamond")
	public McmodInfoConfig(InputStream in) {
		entries = new Gson().fromJson(new InputStreamReader(in), new TypeToken<List<agency.highlysuspect.redmill.svc.modfilereader.McmodInfoEntryConfig>>(){});
	}
	
	public final List<agency.highlysuspect.redmill.svc.modfilereader.McmodInfoEntryConfig> entries;
	
	/**
	 * @see net.neoforged.fml.loading.moddiscovery.ModFileInfo#ModFileInfo(ModFile, IConfigurable, Consumer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> Optional<T> getConfigElement(String... key) {
		return switch(key[0]) {
			case "modLoader" -> Optional.of((T) Consts.REDMILL_LANGUAGE_LOADER);
			case "loaderVersion" -> Optional.of((T) IModInfo.UNBOUNDED.toString());
			case "license" -> Optional.of((T) "Unknown License (loaded via Red Mill)");
			default -> Optional.empty();
		};
	}
	
	@Override
	public List<? extends IConfigurable> getConfigList(String... key) {
		return switch(key[0]) {
			case "mods" -> entries;
			case "dependencies" -> {
				if(key.length < 2) yield List.of();
				
				//which mod is it talking about
				for(agency.highlysuspect.redmill.svc.modfilereader.McmodInfoEntryConfig entry : entries) if(entry.getModernModid().equals(key[1])) {
					List<DepEntry> deps = new ArrayList<>();
					
					//dep on the redmill mod itself (TODO not sure if the redmill mod container is getting loaded)
					deps.add(new DepEntry("redmill2", "BEFORE"));
					
					//if it has a parent, dep on that
					if(entry.parent != null && !entry.parent.isEmpty()) {
						ModContainerExt parentExt = Globals.getModContainerByOldId(entry.parent);
						if(parentExt == null) {
							Consts.LOG.warn("Couldn't find ModContainerExt for old mod {}", entry.parent);
						} else {
							deps.add(new DepEntry(parentExt.modernModid, "AFTER"));
						}
					}
					
					yield deps;
				}
				
				yield List.of();
			}
			default -> List.of();
		};
	}
	
	//see net.neoforged.fml.loading.moddiscovery.ModInfo.ModVersion.ModVersion
	private record DepEntry(String dependsOn, String ordering) implements IConfigurable {
		@SuppressWarnings("unchecked")
		@Override
		public <T> Optional<T> getConfigElement(String... key) {
			return switch(key[0]) {
				case "modId" -> Optional.of((T) dependsOn);
				case "type" -> Optional.of((T) "required");
				case "ordering" -> Optional.of((T) ordering);
				default -> Optional.empty();
			};
		}
		
		@Override
		public List<? extends IConfigurable> getConfigList(String... key) {
			return List.of();
		}
	}
}
