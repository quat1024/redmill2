package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.client.registry;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.ITickType;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.settings.IKeyBinding;

import java.util.EnumSet;

public interface IKeyBindingRegistry {
	interface IKeyHandler {
		<T extends Enum<T> & ITickType> void keyDown(EnumSet<T> var1, IKeyBinding var2, boolean var3, boolean var4);
		<T extends Enum<T> & ITickType> void keyUp(EnumSet<T> var1, IKeyBinding var2, boolean var3);
		<T extends Enum<T> & ITickType> EnumSet<T> ticks();
		String getLabel();
	}
}
