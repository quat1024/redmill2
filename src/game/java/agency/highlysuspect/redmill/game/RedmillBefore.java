package agency.highlysuspect.redmill.game;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.client.FMLClientHandler;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.FMLCommonHandler;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.Loader;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.TickType;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.FMLInjectionData;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.Side;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.RMinecraft;
import agency.highlysuspect.redmill.oldschool.net.minecraftforge.common.ForgeVersion;
import agency.highlysuspect.redmill.oldschool.net.minecraftforge.common.MinecraftForge;
import agency.highlysuspect.redmill.oldschool.net.minecraftforge.event.world.WorldEvent;
import agency.highlysuspect.redmill.svc.Consts;
import agency.highlysuspect.redmill.svc.Globals;
import agency.highlysuspect.redmill.svc.ModContainerExt;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent;

import java.util.EnumSet;

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
		
		NeoForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void levelLoad(LevelEvent.Load e) {
		MinecraftForge.EVENT_BUS.post(new WorldEvent.Load(null)); //TODO proxy level
	}
	
	@SubscribeEvent
	public void levelUnload(LevelEvent.Unload e) {
		MinecraftForge.EVENT_BUS.post(new WorldEvent.Unload(null)); //TODO proxy level
	}
	
	@SubscribeEvent
	public void levelSave(LevelEvent.Save e) {
		MinecraftForge.EVENT_BUS.post(new WorldEvent.Save(null)); //TODO proxy level
	}
	
	@Mod(value = Consts.MODID_BEFORE, dist = Dist.CLIENT)
	public static class Client {
		public Client() {
			NeoForge.EVENT_BUS.register(this);
		}
		
		private static final String FORGE_F3 = "(Red Mill) Forge "
			+ ChatFormatting.LIGHT_PURPLE
			+ ForgeVersion.getVersion()
			+ ChatFormatting.RESET
			+ "/1.4.7"; //lol hardcoded
		
		@SubscribeEvent
		public void f3(CustomizeGuiOverlayEvent.DebugText e) {
			e.getRight().add(FORGE_F3);
		}
		
		@SubscribeEvent
		public void clientTickPre(ClientTickEvent.Pre e) {
			FMLCommonHandler.instance().onPreClientTick();
		}
		
		@SubscribeEvent
		public void clientTickPost(ClientTickEvent.Post e) {
			FMLCommonHandler.instance().onPostClientTick();
		}
		
		@SubscribeEvent
		public void renderTickPre(ClientTickEvent.Pre e) {
			FMLCommonHandler.instance().rescheduleTicks(Side.CLIENT); //TODO where does this go
			FMLCommonHandler.instance().onRenderTickStart(Minecraft.getInstance().getTimer().getGameTimeDeltaTicks());
		}
		
		@SubscribeEvent
		public void renderTickPost(ClientTickEvent.Post e) {
			FMLCommonHandler.instance().onRenderTickEnd(Minecraft.getInstance().getTimer().getGameTimeDeltaTicks());
		}
	}
}
