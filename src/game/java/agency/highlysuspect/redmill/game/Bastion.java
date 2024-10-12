package agency.highlysuspect.redmill.game;

import agency.highlysuspect.redmill.svc.Consts;
import agency.highlysuspect.redmill.svc.Globals;
import agency.highlysuspect.redmill.svc.languageloader.RedmillModContainer;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.RMod;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.RFMLPreInitializationEvent;
import agency.highlysuspect.redmill.svc.util.IBastion;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Bastion implements IBastion {
	static {
		System.out.println("Hello from Bastion!");
		System.out.println(Bastion.class.getClassLoader());
		System.out.println(Bastion.class.getModule());
	}
	
	public void preinitMod(RedmillModContainer rmc) {
		assert rmc.modClass != null;
		
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
