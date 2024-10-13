package agency.highlysuspect.redmill.game;

import agency.highlysuspect.redmill.svc.Consts;
import agency.highlysuspect.redmill.svc.Globals;
import agency.highlysuspect.redmill.svc.languageloader.RedmillModContainer;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.RMod;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.RFMLPreInitializationEvent;
import agency.highlysuspect.redmill.svc.util.IBastion;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;

@SuppressWarnings("unused") //reflectively accessed
public class Bastion implements IBastion {
	static {
		Consts.LOG.info("(Red Mill) Hello from the Bastion!");
		Consts.LOG.info("My module is: {}", Bastion.class.getModule());
		Consts.LOG.info("My module layer is: {}", Bastion.class.getModule().getLayer());
	}
	
	@SuppressWarnings("unused") //reflectively accessed
	public static final IBastion INSTANCE = new Bastion();
	
	@Override
	public @Nullable Class<?> loadModClass(RedmillModContainer rmc) {
		if(rmc.modClassName == null) return null;
		
		Consts.LOG.debug("Loading entrypoint class {}", rmc.modClassName);
		try {
			return Objects.requireNonNull(Class.forName(rmc.module, rmc.modClassName));
		} catch (Exception e) {
			throw Globals.mkRethrow(e, "Failed to load redmill entrypoint class " + rmc.modClassName);
		}
	}
	
	@Override
	public Object constructModClass(RedmillModContainer rmc) {
		if(rmc.modClass == null) return null;
		
		Consts.LOG.debug("Constructing mod class {}", rmc.modClass);
		try {
			return rmc.modClass.getConstructor().newInstance();
		} catch (Exception e) {
			throw Globals.mkRethrow(e, "Failed to construct redmill entrypoint " + rmc.modClass.getName());
		}
	}
	
	public void preinitMod(RedmillModContainer rmc) {
		if(rmc.modInstance == null) return;
		
		RFMLPreInitializationEvent preinit = new RFMLPreInitializationEvent();
		
		try {
			for(Method method : rmc.modClass.getMethods()) {
				Object preInit = method.getAnnotation(RMod.RPreInit.class);
				if(preInit != null) {
					Consts.LOG.debug("Found @RPreInit method {}", method);
					if(Modifier.isStatic(method.getModifiers())) {
						method.invoke(preinit);
					} else {
						method.invoke(rmc.modInstance, preinit);
					}
				}
			}
		} catch (Exception e) {
			throw Globals.mkRethrow(e, "failed to do preinit stuff");
		}
	}
}
