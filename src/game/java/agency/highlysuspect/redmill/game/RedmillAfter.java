package agency.highlysuspect.redmill.game;

import agency.highlysuspect.redmill.svc.Consts;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Consts.MODID_AFTER)
public class RedmillAfter {
	public RedmillAfter(IEventBus modBus) {
		System.out.println("Redmill_Late loading");
	}
}
