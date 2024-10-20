//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.LoaderState;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.LoaderState.ModState;

public class FMLConstructionEvent extends FMLStateEvent {
//	private ModClassLoader modClassLoader;
//	private ASMDataTable asmData;
	
	public FMLConstructionEvent(Object... var1) {
		super(new Object[0]);
//		this.modClassLoader = (ModClassLoader)var1[0];
//		this.asmData = (ASMDataTable)var1[1];
	}
	
//	public ModClassLoader getModClassLoader() {
//		return this.get_modClassLoader();
//	}
	
	public LoaderState.ModState getModState() {
		return ModState.CONSTRUCTED;
	}
	
//	public ASMDataTable getASMHarvestedData() {
//		return this.get_asmData();
//	}

//	public ModClassLoader get_modClassLoader() {
//		return this.modClassLoader;
//	}
//
//	public void set_modClassLoader(ModClassLoader var1) {
//		this.modClassLoader = var1;
//	}
//
//	public ASMDataTable get_asmData() {
//		return this.asmData;
//	}
//
//	public void set_asmData(ASMDataTable var1) {
//		this.asmData = var1;
//	}
}
