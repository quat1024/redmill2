//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.net.minecraftforge.common;

public class ForgeInternalHandler {
	public ForgeInternalHandler() {
	}
	
//	@ForgeSubscribe(
//		priority = EventPriority.HIGHEST
//	)
//	public void onEntityJoinWorld(EntityJoinWorldEvent var1) {
//		if (!var1.get_world().get_isRemote()) {
//			if (var1.get_entity().getPersistentID() == null) {
//				var1.get_entity().generatePersistentID();
//			} else {
//				ForgeChunkManager.loadEntity(var1.get_entity());
//			}
//		}
//
//		IEntity var2 = var1.get_entity();
//		if (var2.getClass().equals(REntityItem.class)) {
//			IItemStack var3 = var2.getDataWatcher().getWatchableObjectItemStack(10);
//			if (var3 == null) {
//				return;
//			}
//
//			IItem var4 = var3.getItem();
//			if (var4 == null) {
//				FMLLog.warning("Attempted to add a EntityItem to the world with a invalid item: ID %d at (%2.2f,  %2.2f, %2.2f), this is most likely a config issue between you and the server. Please double check your configs", new Object[]{var3.get_itemID(), var2.get_posX(), var2.get_posY(), var2.get_posZ()});
//				var2.setDead();
//				var1.setCanceled(true);
//				return;
//			}
//
//			if (var4.hasCustomEntity(var3)) {
//				IEntity var5 = var4.createEntity(var1.get_world(), var2, var3);
//				if (var5 != null) {
//					var2.setDead();
//					var1.setCanceled(true);
//					var1.get_world().spawnEntityInWorld(var5);
//				}
//			}
//		}
//
//	}

//	@ForgeSubscribe(
//		priority = EventPriority.HIGHEST
//	)
//	public void onDimensionLoad(WorldEvent.Load var1) {
//		ForgeChunkManager.loadWorld(var1.get_world());
//	}

//	@ForgeSubscribe(
//		priority = EventPriority.HIGHEST
//	)
//	public void onDimensionSave(WorldEvent.Save var1) {
//		ForgeChunkManager.saveWorld(var1.get_world());
//	}

//	@ForgeSubscribe(
//		priority = EventPriority.HIGHEST
//	)
//	public void onDimensionUnload(WorldEvent.Unload var1) {
//		ForgeChunkManager.unloadWorld(var1.get_world());
//	}
}
