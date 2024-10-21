package agency.highlysuspect.redmill.game;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.client.FMLClientHandler;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.FMLCommonHandler;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.Loader;
import agency.highlysuspect.redmill.svc.Consts;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Consts.MODID_AFTER)
public class RedmillAfter {
	public RedmillAfter(IEventBus modBus) {
		System.out.println("Redmill_Late loading");
		
		//RedmillBefore left it in the CONSTRUCTING state
		//Then, Neoforge classloaded and constructed all the mods (thru Bastion)
		//It's time to start the init process
		Loader loady = Loader.instance();
		loady.redmill$loadMods_afterConstruction();
		
		//This leaves the loader in INITIALIZATION state before distributing the event
		//Process continues from FMLClientHandler.finishMinecraftLoading -> Loader.initializeMods
		
		//TODO...?
		if(FMLCommonHandler.instance().get_sidedDelegate() instanceof FMLClientHandler client) {
			client.finishMinecraftLoading();
		}
	}
}
