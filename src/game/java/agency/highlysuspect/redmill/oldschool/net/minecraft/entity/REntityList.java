//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.net.minecraft.entity;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.FMLLog;
import agency.highlysuspect.redmill.oldschool.net.minecraft.world.IWorld;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

public class REntityList {
	public static Map stringToClassMapping = new HashMap();
	public static Map classToStringMapping = new HashMap();
	public static Map IDtoClassMapping = new HashMap();
	private static Map classToIDMapping = new HashMap();
	private static Map stringToIDMapping = new HashMap();
	public static HashMap entityEggs = new LinkedHashMap();
	
	public REntityList() {
	}
	
	public static void addMapping(Class var0, String var1, int var2) {
		stringToClassMapping.put(var1, var0);
		classToStringMapping.put(var0, var1);
		IDtoClassMapping.put(var2, var0);
		classToIDMapping.put(var0, var2);
		stringToIDMapping.put(var1, var2);
	}
	
//	public static void addMapping(Class var0, String var1, int var2, int var3, int var4) {
//		addMapping(var0, var1, var2);
//		entityEggs.put(var2, new REntityEggInfo(var2, var3, var4));
//	}

//	public static IEntity createEntityByName(String var0, IWorld var1) {
//		IEntity var2 = null;
//
//		try {
//			Class var3 = (Class)stringToClassMapping.get(var0);
//			if (var3 != null) {
//				var2 = (IEntity)var3.getConstructor(RWorld.class).newInstance(var1);
//			}
//		} catch (Exception var4) {
//			var4.printStackTrace();
//		}
//
//		return var2;
//	}

//	public static IEntity createEntityFromNBT(INBTTagCompound var0, IWorld var1) {
//		IEntity var2 = null;
//		Class var3 = null;
//
//		try {
//			var3 = (Class)stringToClassMapping.get(var0.getString("id"));
//			if (var3 != null) {
//				var2 = (IEntity)var3.getConstructor(RWorld.class).newInstance(var1);
//			}
//		} catch (Exception var6) {
//			var6.printStackTrace();
//		}
//
//		if (var2 != null) {
//			try {
//				var2.readFromNBT(var0);
//			} catch (Exception var5) {
//				FMLLog.log(Level.SEVERE, var5, "An Entity %s(%s) has thrown an exception during loading, its state cannot be restored. Report this to the mod author", new Object[]{var0.getString("id"), var3.getName()});
//				var2 = null;
//			}
//		} else {
//			System.out.println("Skipping Entity with id " + var0.getString("id"));
//		}
//
//		return var2;
//	}

//	public static IEntity createEntityByID(int var0, IWorld var1) {
//		IEntity var2 = null;
//
//		try {
//			Class var3 = (Class)IDtoClassMapping.get(var0);
//			if (var3 != null) {
//				var2 = (IEntity)var3.getConstructor(RWorld.class).newInstance(var1);
//			}
//		} catch (Exception var4) {
//			var4.printStackTrace();
//		}
//
//		if (var2 == null) {
//			System.out.println("Skipping Entity with id " + var0);
//		}
//
//		return var2;
//	}
	
	public static int getEntityID(IEntity var0) {
//		Class var1 = var0.getClass();
//		return classToIDMapping.containsKey(var1) ? (Integer)classToIDMapping.get(var1) : 0;
		if(var0 instanceof Entity real) {
			return BuiltInRegistries.ENTITY_TYPE.getId(real.getType());
		} else {
			return -1;
		}
	}
	
//	public static Class getClassFromID(int var0) {
//		return (Class)IDtoClassMapping.get(var0);
//	}
	
	public static String getEntityString(IEntity var0) {
		//return (String)classToStringMapping.get(var0.getClass());
		if(var0 instanceof Entity real) {
			return BuiltInRegistries.ENTITY_TYPE.getKey(real.getType()).toString();
		} else {
			return "UNKNOWN!!!";
		}
	}
	
//	public static String getStringFromID(int var0) {
//		Class var1 = (Class)IDtoClassMapping.get(var0);
//		return var1 != null ? (String)classToStringMapping.get(var1) : null;
//	}
	
	static {
//		addMapping(REntityItem.class, "Item", 1);
//		addMapping(REntityXPOrb.class, "XPOrb", 2);
//		addMapping(REntityPainting.class, "Painting", 9);
//		addMapping(REntityArrow.class, "Arrow", 10);
//		addMapping(REntitySnowball.class, "Snowball", 11);
//		addMapping(REntityLargeFireball.class, "Fireball", 12);
//		addMapping(REntitySmallFireball.class, "SmallFireball", 13);
//		addMapping(REntityEnderPearl.class, "ThrownEnderpearl", 14);
//		addMapping(REntityEnderEye.class, "EyeOfEnderSignal", 15);
//		addMapping(REntityPotion.class, "ThrownPotion", 16);
//		addMapping(REntityExpBottle.class, "ThrownExpBottle", 17);
//		addMapping(REntityItemFrame.class, "ItemFrame", 18);
//		addMapping(REntityWitherSkull.class, "WitherSkull", 19);
//		addMapping(REntityTNTPrimed.class, "PrimedTnt", 20);
//		addMapping(REntityFallingSand.class, "FallingSand", 21);
//		addMapping(REntityFireworkRocket.class, "FireworksRocketEntity", 22);
//		addMapping(REntityMinecart.class, "Minecart", 40);
//		addMapping(REntityBoat.class, "Boat", 41);
//		addMapping(REntityLiving.class, "Mob", 48);
//		addMapping(REntityMob.class, "Monster", 49);
//		addMapping(REntityCreeper.class, "Creeper", 50, 894731, 0);
//		addMapping(REntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
//		addMapping(REntitySpider.class, "Spider", 52, 3419431, 11013646);
//		addMapping(REntityGiantZombie.class, "Giant", 53);
//		addMapping(REntityZombie.class, "Zombie", 54, 44975, 7969893);
//		addMapping(REntitySlime.class, "Slime", 55, 5349438, 8306542);
//		addMapping(REntityGhast.class, "Ghast", 56, 16382457, 12369084);
//		addMapping(REntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
//		addMapping(REntityEnderman.class, "Enderman", 58, 1447446, 0);
//		addMapping(REntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
//		addMapping(REntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
//		addMapping(REntityBlaze.class, "Blaze", 61, 16167425, 16775294);
//		addMapping(REntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
//		addMapping(REntityDragon.class, "EnderDragon", 63);
//		addMapping(REntityWither.class, "WitherBoss", 64);
//		addMapping(REntityBat.class, "Bat", 65, 4996656, 986895);
//		addMapping(REntityWitch.class, "Witch", 66, 3407872, 5349438);
//		addMapping(REntityPig.class, "Pig", 90, 15771042, 14377823);
//		addMapping(REntitySheep.class, "Sheep", 91, 15198183, 16758197);
//		addMapping(REntityCow.class, "Cow", 92, 4470310, 10592673);
//		addMapping(REntityChicken.class, "Chicken", 93, 10592673, 16711680);
//		addMapping(REntitySquid.class, "Squid", 94, 2243405, 7375001);
//		addMapping(REntityWolf.class, "Wolf", 95, 14144467, 13545366);
//		addMapping(REntityMooshroom.class, "MushroomCow", 96, 10489616, 12040119);
//		addMapping(REntitySnowman.class, "SnowMan", 97);
//		addMapping(REntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
//		addMapping(REntityIronGolem.class, "VillagerGolem", 99);
//		addMapping(REntityVillager.class, "Villager", 120, 5651507, 12422002);
//		addMapping(REntityEnderCrystal.class, "EnderCrystal", 200);
	}
	
	public static Map get_stringToClassMapping() {
		return stringToClassMapping;
	}
	
	public static void set_stringToClassMapping(Map var0) {
		stringToClassMapping = var0;
	}
	
	public static Map get_classToStringMapping() {
		return classToStringMapping;
	}
	
	public static void set_classToStringMapping(Map var0) {
		classToStringMapping = var0;
	}
	
	public static Map get_IDtoClassMapping() {
		return IDtoClassMapping;
	}
	
	public static void set_IDtoClassMapping(Map var0) {
		IDtoClassMapping = var0;
	}
	
	public static Map get_classToIDMapping() {
		return classToIDMapping;
	}
	
	public static void set_classToIDMapping(Map var0) {
		classToIDMapping = var0;
	}
	
	public static Map get_stringToIDMapping() {
		return stringToIDMapping;
	}
	
	public static void set_stringToIDMapping(Map var0) {
		stringToIDMapping = var0;
	}
	
	public static HashMap get_entityEggs() {
		return entityEggs;
	}
	
	public static void set_entityEggs(HashMap var0) {
		entityEggs = var0;
	}
}
