//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.Side;
import java.util.List;

public interface IFMLSidedHandler {
	List getAdditionalBrandingInformation();
	
	Side getSide();
	
	void haltGame(String var1, Throwable var2);
	
	void showGuiScreen(Object var1);
	
//	IEntity spawnEntityIntoClientWorld(EntityRegistry.EntityRegistration var1, EntitySpawnPacket var2);
//
//	void adjustEntityLocationOnClient(EntitySpawnAdjustmentPacket var1);
//
//	void beginServerLoading(IMinecraftServer var1);
	
	void finishServerLoading();
	
//	IMinecraftServer getServer();
//
//	void sendPacket(IPacket var1);
//
//	void displayMissingMods(ModMissingPacket var1);
//
//	void handleTinyPacket(INetHandler var1, IPacket131MapData var2);
	
	void setClientCompatibilityLevel(byte var1);
	
	byte getClientCompatibilityLevel();
	
	boolean shouldServerShouldBeKilledQuietly();
	
//	void disconnectIDMismatch(MapDifference var1, INetHandler var2, IINetworkManager var3);
}
