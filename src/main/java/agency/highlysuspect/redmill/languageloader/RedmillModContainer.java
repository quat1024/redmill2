package agency.highlysuspect.redmill.languageloader;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.IModInfo;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class RedmillModContainer extends ModContainer {
	public RedmillModContainer(IModInfo info, String entrypoint, Module module) {
		super(info);
		
		//load the entrypoint class
		try {
			modClass = Objects.requireNonNull(Class.forName(module, entrypoint));
		} catch (Exception e) {
			throw new RuntimeException("Failed to load entrypoint class " + entrypoint, e);
		}
	}
	
	private final Class<?> modClass;
	
	@Override
	public @Nullable IEventBus getEventBus() {
		return null;
	}
	
	@Override
	protected void constructMod() {
		try {
			modClass.getConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Failed to construct redmill mod", e);
		}
	}
}
