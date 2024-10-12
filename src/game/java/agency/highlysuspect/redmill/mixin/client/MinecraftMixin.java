package agency.highlysuspect.redmill.mixin.client;

import agency.highlysuspect.redmill.oldschool.net.minecraft.client.IMinecraft;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.entity.IEntityClientPlayerMP;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.multiplayer.IWorldClient;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.settings.IGameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public class MinecraftMixin implements IMinecraft {
	@Shadow static Minecraft instance;
	
	@Shadow ClientLevel level;
	
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
		return (IWorldClient) level;
	}
	
	@Override
	public boolean get_isGamePaused() {
		return instance.isPaused();
	}
}
