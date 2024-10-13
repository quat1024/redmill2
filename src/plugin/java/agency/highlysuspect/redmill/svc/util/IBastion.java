package agency.highlysuspect.redmill.svc.util;

import agency.highlysuspect.redmill.svc.Consts;
import agency.highlysuspect.redmill.svc.languageloader.RedmillModContainer;

import java.lang.reflect.Field;
import java.util.Objects;

public interface IBastion {
	Class<?> loadModClass(RedmillModContainer rmc);
	Object constructModClass(RedmillModContainer rmc);
	void preinitMod(RedmillModContainer rmc);
	
	@SuppressWarnings("unchecked")
	static IBastion getInstance(ModuleLayer gameLayer) {
		Module redmill2Module = gameLayer.findModule(Consts.MODID)
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
