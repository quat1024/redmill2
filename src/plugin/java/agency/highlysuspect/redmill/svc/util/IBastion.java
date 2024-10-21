package agency.highlysuspect.redmill.svc.util;

import agency.highlysuspect.redmill.svc.Consts;
import net.neoforged.bus.api.IEventBus;

import java.lang.reflect.Field;
import java.util.Objects;

public interface IBastion {
	IEventBus getEventBus();
	
	@SuppressWarnings("unchecked")
	static IBastion getInstance(ModuleLayer gameLayer) {
		Module redmill2Module = gameLayer.findModule(Consts.MODID_BEFORE)
			.orElseThrow(() -> new RuntimeException("couldn't find redmill2 layer"));
		
		try {
			Class<? extends IBastion> clazz = (Class<? extends IBastion>) Objects.requireNonNull(
				Class.forName(redmill2Module, "agency.highlysuspect.redmill.game.Bastion")
			);
			
			Field instanceField = clazz.getDeclaredField("INSTANCE");
			return (IBastion) instanceField.get(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
