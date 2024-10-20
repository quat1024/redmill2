//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.LoaderState;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.LoaderState.ModState;

public class FMLServerStartingEvent extends FMLStateEvent {
//	private IMinecraftServer server;
	
	public FMLServerStartingEvent(Object... var1) {
		super(var1);
//		this.server = (IMinecraftServer)var1[0];
	}
	
	public LoaderState.ModState getModState() {
		return ModState.AVAILABLE;
	}
	
//	public IMinecraftServer getServer() {
//		return this.get_server();
//	}
//
//	public void registerServerCommand(IICommand var1) {
//		ICommandHandler var2 = (ICommandHandler)this.getServer().getCommandManager();
//		var2.registerCommand(var1);
//	}
//
//	public IMinecraftServer get_server() {
//		return this.server;
//	}
//
//	public void set_server(IMinecraftServer var1) {
//		this.server = var1;
//	}
}
