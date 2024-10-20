//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.net.minecraftforge.common;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.FMLLog;
import agency.highlysuspect.redmill.oldschool.net.minecraftforge.event.IEventBus;
import agency.highlysuspect.redmill.oldschool.net.minecraftforge.event.REventBus;

public class MinecraftForge {
	public static final IEventBus EVENT_BUS = new REventBus();
	public static final IEventBus TERRAIN_GEN_BUS = new REventBus();
	public static final IEventBus ORE_GEN_BUS = new REventBus();
	///** @deprecated */
	//@Deprecated
	public static boolean SPAWNER_ALLOW_ON_INVERTED = false;
	private static final ForgeInternalHandler INTERNAL_HANDLER = new ForgeInternalHandler();
	
	public MinecraftForge() {
	}
	
//	public static void addGrassPlant(IBlock var0, int var1, int var2) {
//		ForgeHooks.get_grassList().add(new ForgeHooks.GrassEntry(var0, var1, var2));
//	}

//	public static void addGrassSeed(IItemStack var0, int var1) {
//		ForgeHooks.get_seedList().add(new ForgeHooks.SeedEntry(var0, var1));
//	}

//	public static void setToolClass(IItem var0, String var1, int var2) {
//		ForgeHooks.get_toolClasses().put(var0, Arrays.asList(var1, var2));
//	}

//	public static void setBlockHarvestLevel(IBlock var0, int var1, String var2, int var3) {
//		List var4 = Arrays.asList(var0, var1, var2);
//		ForgeHooks.get_toolHarvestLevels().put(var4, var3);
//		ForgeHooks.get_toolEffectiveness().add(var4);
//	}

//	public static void removeBlockEffectiveness(IBlock var0, int var1, String var2) {
//		List var3 = Arrays.asList(var0, var1, var2);
//		ForgeHooks.get_toolEffectiveness().remove(var3);
//	}

//	public static void setBlockHarvestLevel(IBlock var0, String var1, int var2) {
//		for(int var3 = 0; var3 < 16; ++var3) {
//			List var4 = Arrays.asList(var0, var3, var1);
//			ForgeHooks.get_toolHarvestLevels().put(var4, var2);
//			ForgeHooks.get_toolEffectiveness().add(var4);
//		}
//
//	}

//	public static int getBlockHarvestLevel(IBlock var0, int var1, String var2) {
//		ForgeHooks.initTools();
//		List var3 = Arrays.asList(var0, var1, var2);
//		Integer var4 = (Integer)ForgeHooks.get_toolHarvestLevels().get(var3);
//		return var4 == null ? -1 : var4;
//	}

//	public static void removeBlockEffectiveness(IBlock var0, String var1) {
//		for(int var2 = 0; var2 < 16; ++var2) {
//			List var3 = Arrays.asList(var0, var2, var1);
//			ForgeHooks.get_toolEffectiveness().remove(var3);
//		}
//
//	}
	
	public static void initialize() {
		System.out.printf("MinecraftForge v%s Initialized\n", ForgeVersion.getVersion());
		FMLLog.info("MinecraftForge v%s Initialized", ForgeVersion.getVersion());
//		RBlock var0 = new RBlock(0, RMaterial.get_air());
//		RBlock.get_blocksList()[0] = null;
//		RBlock.get_opaqueCubeLookup()[0] = false;
//		RBlock.get_lightOpacity()[0] = 0;
//
//		for(int var1 = 256; var1 < 4096; ++var1) {
//			if (RItem.get_itemsList()[var1] != null) {
//				RBlock.get_blocksList()[var1] = var0;
//			}
//		}
//
//		boolean[] var2 = new boolean[4096];
//		System.arraycopy(REntityEnderman.get_carriableBlocks(), 0, var2, 0, REntityEnderman.get_carriableBlocks().length);
//		REntityEnderman.set_carriableBlocks(var2);
		EVENT_BUS.register(INTERNAL_HANDLER);
//		OreDictionary.getOreName(0);
	}
	
	public static String getBrandingVersion() {
		return "Minecraft Forge " + ForgeVersion.getVersion();
	}
	
	public static IEventBus get_EVENT_BUS() {
		return EVENT_BUS;
	}
	
	public static IEventBus get_TERRAIN_GEN_BUS() {
		return TERRAIN_GEN_BUS;
	}
	
	public static IEventBus get_ORE_GEN_BUS() {
		return ORE_GEN_BUS;
	}
	
	public static boolean get_SPAWNER_ALLOW_ON_INVERTED() {
		return SPAWNER_ALLOW_ON_INVERTED;
	}
	
	public static void set_SPAWNER_ALLOW_ON_INVERTED(boolean var0) {
		SPAWNER_ALLOW_ON_INVERTED = var0;
	}
	
	public static ForgeInternalHandler get_INTERNAL_HANDLER() {
		return INTERNAL_HANDLER;
	}
}
