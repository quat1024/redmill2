package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.client.registry;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.ITickType;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.settings.IKeyBinding;

import java.util.EnumSet;

public abstract class KeyHandlerProxy implements IKeyBindingRegistry.IKeyHandler {
	public abstract <T extends Enum<T> & ITickType> void keyDown(EnumSet<T> var1, IKeyBinding var2, boolean var3, boolean var4);
	public abstract <T extends Enum<T> & ITickType> void keyUp(EnumSet<T> var1, IKeyBinding var2, boolean var3);
	public abstract <T extends Enum<T> & ITickType> EnumSet<T> ticks();
	public abstract String getLabel();
}
