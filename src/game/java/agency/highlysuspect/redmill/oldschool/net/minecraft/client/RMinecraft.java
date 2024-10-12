package agency.highlysuspect.redmill.oldschool.net.minecraft.client;

import agency.highlysuspect.redmill.oldschool.net.minecraft.client.multiplayer.IWorldClient;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.settings.IGameSettings;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.entity.IEntityClientPlayerMP;

public class RMinecraft implements IMinecraft {
	public static final IMinecraft INSTANCE = new RMinecraft();
	
	public static IMinecraft getMinecraft() {
		return INSTANCE;
	}
	
	@Override
	public IGameSettings get_gameSettings() {
		return null;
	}
	
	@Override
	public IEntityClientPlayerMP get_thePlayer() {
		return null;
	}
	
	@Override
	public IWorldClient get_theWorld() {
		return null;
	}
	
	@Override
	public boolean get_isGamePaused() {
		return false;
	}
}
