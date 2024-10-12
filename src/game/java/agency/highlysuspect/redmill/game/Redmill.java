package agency.highlysuspect.redmill.game;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("redmill2")
public class Redmill {
	public Redmill(IEventBus modBus) {
		for(int i = 0; i < 10; i++)
			System.out.println("HELLO WORLD!!!");
	}
}
