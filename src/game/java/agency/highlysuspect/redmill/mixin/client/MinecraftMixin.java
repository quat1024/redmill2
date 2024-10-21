package agency.highlysuspect.redmill.mixin.client;

import agency.highlysuspect.redmill.oldschool.net.minecraft.client.IMinecraft;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.entity.IEntityClientPlayerMP;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.multiplayer.IWorldClient;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.settings.IGameSettings;
import agency.highlysuspect.redmill.oldschool.net.minecraft.crash.ICrashReport;
import agency.highlysuspect.redmill.oldschool.net.minecraft.crash.RCrashReport;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public class MinecraftMixin implements IMinecraft {
	@Shadow static Minecraft instance;
	
	@Shadow ClientLevel level;
	
	@Override
	public void displayCrashReport(ICrashReport crash) {
		instance.emergencySaveAndCrash(crash.redmill$getDelegate());
	}
	
	@Override
	public IGameSettings get_gameSettings() {
		return (IGameSettings) instance.options;
	}
	
	@Override
	public IEntityClientPlayerMP get_thePlayer() {
		return (IEntityClientPlayerMP) instance.player;
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
