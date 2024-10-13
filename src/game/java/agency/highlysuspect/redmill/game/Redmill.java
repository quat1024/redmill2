package agency.highlysuspect.redmill.game;

import agency.highlysuspect.redmill.svc.Consts;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Consts.MODID)
public class Redmill {
	public Redmill(IEventBus modBus) {
		Consts.LOG.info("RedMill mod loading");
	}
}
