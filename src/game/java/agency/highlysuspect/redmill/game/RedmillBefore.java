package agency.highlysuspect.redmill.game;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.client.FMLClientHandler;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.Loader;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.FMLInjectionData;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.RMinecraft;
import agency.highlysuspect.redmill.oldschool.net.minecraftforge.common.MinecraftForge;
import agency.highlysuspect.redmill.svc.Consts;
import agency.highlysuspect.redmill.svc.Globals;
import agency.highlysuspect.redmill.svc.ModContainerExt;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Consts.MODID_BEFORE)
public class RedmillBefore {
	public RedmillBefore(IEventBus modBus) {
		Consts.LOG.info("RedMill mod loading");
		
		//TODO: WILL CRASH ON SERVERS
		FMLClientHandler clientHandler = new FMLClientHandler();
		clientHandler.beginMinecraftLoading(RMinecraft.getMinecraft()); //calls MinecraftForge.initialize
		
		//injection data
		FMLInjectionData.redmill$build();
		Loader.injectData(FMLInjectionData.data());
		
		Loader loady = Loader.instance();
		//inject the modern mod containers into the loader, since i ripped out the mod discovery process
		for(ModContainerExt ext : Globals.getAllModContainers()) {
			loady.redmill$injectContainer(ext);
		}
		
		//Prepare for mod loading.
		//Leaves the LoadController in the CONSTRUCTING state
		loady.loadMods();
	}
}
