package agency.highlysuspect.redmill.oldschool.net.minecraftforge.common;

import agency.highlysuspect.redmill.oldschool.net.minecraftforge.event.IEventBus;
import agency.highlysuspect.redmill.oldschool.net.minecraftforge.event.REventBus;

public class RMinecraftForge implements IMinecraftForge {
	public static IEventBus EVENT_BUS = new REventBus();
	
	public static IEventBus get_EVENT_BUS() {
		return EVENT_BUS;
	}
}
