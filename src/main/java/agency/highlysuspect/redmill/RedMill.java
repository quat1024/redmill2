package agency.highlysuspect.redmill;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Consts.REDMILL_MODID)
public class RedMill {
	public RedMill(IEventBus modBus) {
		//TODO I don't think this is getting called
		Consts.LOG.info("hello world ~");
	}
}
