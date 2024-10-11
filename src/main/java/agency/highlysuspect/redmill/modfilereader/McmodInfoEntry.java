package agency.highlysuspect.redmill.modfilereader;

import agency.highlysuspect.redmill.Consts;
import net.neoforged.neoforgespi.language.IConfigurable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

//Meant to be deserialized with google gson
public class McmodInfoEntry implements IConfigurable {
	String modid;
	String name;
	String description;
	String version;
	String credits;
	String mcversion;
	String url;
	String updateUrl;
	List<String> authors;
	String parent;
	List<String> screenshots;
	List<String> dependencies;
	
	private transient String modernModid;
	private transient String modernVersion;
	
	public String getModernModid() {
		if(modernModid == null) {
			//see ModInfo.VALID_MODID.
			
			modernModid = modid;
			
			//surely this won't happen
			if(modernModid == null || modernModid.isEmpty()) {
				modernModid = "unnamed";
			}
			
			//lowercase only
			modernModid = modernModid
				.toLowerCase(Locale.ROOT)
				.replaceAll("[^a-z0-9_]", "_");
			
			//has to start with a letter
			char firstie = modernModid.charAt(0);
			if(firstie < 'a' || firstie > 'z') {
				modernModid = "z" + modernModid;
			}
			
			//between 1 and 63 characters
			if(modernModid.length() > 63) {
				modernModid = modernModid.substring(0, 63);
			}
		}
		return modernModid;
	}
	
	public String getModernVersion() {
		if(modernVersion == null) {
			//see ModInfo.VALID_VERSION
			
			modernVersion = version;
			
			//sanity
			if(modernVersion == null || modernVersion.isEmpty()) {
				modernVersion = "0";
			}
			
			//needs to start with at least one digit
			char firstie = modernVersion.charAt(0);
			if(firstie < '0' || firstie > '9') {
				modernVersion = "0" + modernVersion;
			}
		}
		return modernVersion;
	}
	
	//see net.neoforged.fml.loading.moddiscovery.ModInfo.ModInfo
	@Override
	public <T> Optional<T> getConfigElement(String... key) {
		return switch(key[0]) {
			case "modId" -> Optional.of((T) getModernModid());
			case "version" -> Optional.of((T) getModernVersion());
			case "displayName" -> Optional.of((T) name);
			case "description" -> Optional.of((T) description);
			case "modUrl" -> Optional.of((T) url);
			//modproperties
			default -> Optional.empty();
		};
	}
	
	@Override
	public List<? extends IConfigurable> getConfigList(String... key) {
		if(key[0].equals("dependencies")) {
			List<FakeDependency> deps = new ArrayList<>();
			deps.add(new FakeDependency(Consts.REDMILL_MODID, "BEFORE"));
			
			//Old forge has this concept of parent/child mods.
			//I don't think new forge has this, so convert "parent: xyz" into an AFTER dependency on xyz.
			if(parent != null) deps.add(new FakeDependency(parent, "AFTER"));
			
			return deps;
		}
		
		else return List.of();
	}
	
	//see net.neoforged.fml.loading.moddiscovery.ModInfo.ModVersion.ModVersion
	private record FakeDependency(String dependsOn, String ordering) implements IConfigurable {
		@Override
		public <T> Optional<T> getConfigElement(String... key) {
			return switch(key[0]) {
				case "modid" -> Optional.of((T) dependsOn);
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
