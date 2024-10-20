//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.FMLCommonHandler;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.LoaderState;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.Side;

public abstract class FMLStateEvent extends FMLEvent {
	public FMLStateEvent(Object... var1) {
	}
	
	public abstract LoaderState.ModState getModState();
	
	public Side getSide() {
		return FMLCommonHandler.instance().getSide();
	}
}
