package agency.highlysuspect.redmill.modfilereader;

import agency.highlysuspect.redmill.CheekyGlobalState;
import net.neoforged.neoforgespi.language.IConfigurable;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

//Meant to be deserialized with google gson
public class McmodInfoEntryConfig implements IConfigurable {
	public String modid;
	public String name;
	public String description;
	public String version;
	public String credits;
	public String mcversion;
	public String url;
	public String updateUrl;
	public List<String> authors;
	public String parent;
	public List<String> screenshots;
	public List<String> dependencies;
	
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
			
			modernModid = CheekyGlobalState.CFG.modidPrefix + modernModid;
			
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
			//skipping mcVersion and updateUrl, for hopefully obvious reasons
			case "displayName" -> Optional.of((T) name);
			case "description" -> Optional.of((T) description);
			case "modUrl" -> Optional.of((T) url);
			//modproperties
			
			//from ModListScreen:
			case "authors" -> Optional.of((T) String.join(", ", authors));
			case "credits" -> Optional.of((T) credits);
			
			default -> Optional.empty();
		};
	}
	
	@Override
	public List<? extends IConfigurable> getConfigList(String... key) {
		return List.of();
	}
}
