package agency.highlysuspect.redmill.game;

import agency.highlysuspect.redmill.svc.Consts;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod(Consts.MODID_AFTER)
public class RedmillAfter {
	public RedmillAfter(IEventBus modBus) {
		System.out.println("Redmill_Late loading");
		
		modBus.addListener(FMLConstructModEvent.class, event -> {
			System.out.println("Construct Redmill_Late");
		});
	}
}
