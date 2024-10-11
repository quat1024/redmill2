package agency.highlysuspect.redmill.languageloader;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.IModInfo;
import org.jetbrains.annotations.Nullable;

public class RedmillModContainer extends ModContainer {
	public RedmillModContainer(IModInfo info) {
		super(info);
	}
	
	@Override
	public @Nullable IEventBus getEventBus() {
		return null;
	}
	
	@Override
	protected void constructMod() {
		super.constructMod();
	}
}
