//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.Loader;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.LoaderState;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.LoaderState.ModState;
import com.google.common.base.Throwables;

public class FMLPostInitializationEvent extends FMLStateEvent {
	public FMLPostInitializationEvent(Object... var1) {
		super(var1);
	}
	
	public LoaderState.ModState getModState() {
		return ModState.POSTINITIALIZED;
	}
	
	public Object buildSoftDependProxy(String var1, String var2) {
		if (Loader.isModLoaded(var1)) {
			try {
				Class var3 = Class.forName(var2, true, Loader.instance().getModClassLoader());
				return var3.newInstance();
			} catch (Exception var4) {
				Throwables.propagateIfPossible(var4);
				return null;
			}
		} else {
			return null;
		}
	}
}
