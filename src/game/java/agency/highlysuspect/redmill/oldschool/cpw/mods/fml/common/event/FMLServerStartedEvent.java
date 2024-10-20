//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.LoaderState;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.LoaderState.ModState;

public class FMLServerStartedEvent extends FMLStateEvent {
	public FMLServerStartedEvent(Object... var1) {
		super(var1);
	}
	
	public LoaderState.ModState getModState() {
		return ModState.AVAILABLE;
	}
}
