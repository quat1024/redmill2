package agency.highlysuspect.redmill.oldschool.net.minecraft.client;

import agency.highlysuspect.redmill.oldschool.net.minecraft.client.multiplayer.IWorldClient;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.settings.IGameSettings;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.entity.IEntityClientPlayerMP;

public interface IMinecraft {
	IGameSettings get_gameSettings();
	IEntityClientPlayerMP get_thePlayer();
	IWorldClient get_theWorld();
	
	boolean get_isGamePaused();
}
