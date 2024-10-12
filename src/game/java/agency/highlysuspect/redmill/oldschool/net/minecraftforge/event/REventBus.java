package agency.highlysuspect.redmill.oldschool.net.minecraftforge.event;

import agency.highlysuspect.redmill.svc.Consts;

public class REventBus implements IEventBus {
	@Override
	public void register(Object object) {
		Consts.LOG.warn("REventBus.register called with object: {}", object);
	}
}
