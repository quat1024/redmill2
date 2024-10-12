package agency.highlysuspect.redmill.mixin.client;

import agency.highlysuspect.redmill.oldschool.net.minecraft.client.settings.IGameSettings;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Options.class)
public abstract class OptionsMixin implements IGameSettings {
	@Shadow public abstract CameraType getCameraType();
	@Shadow public abstract void setCameraType(CameraType pPointOfView);
	
	@Override
	public int get_thirdPersonView() {
		return getCameraType().ordinal();
	}
	
	@Override
	public void set_thirdPersonView(int view) {
		switch(view) {
			case 0 -> setCameraType(CameraType.FIRST_PERSON);
			case 1 -> setCameraType(CameraType.THIRD_PERSON_BACK);
			case 2 -> setCameraType(CameraType.THIRD_PERSON_FRONT);
		}
	}
	
	@Override
	public boolean get_showDebugInfo() {
		//Used to be on Options but it's not anymore
		return Minecraft.getInstance().getDebugOverlay().showDebugScreen();
	}
}
