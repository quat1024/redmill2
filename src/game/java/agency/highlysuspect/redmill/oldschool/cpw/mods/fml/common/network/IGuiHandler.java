//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.network;

import agency.highlysuspect.redmill.oldschool.net.minecraft.entity.player.IEntityPlayer;
import agency.highlysuspect.redmill.oldschool.net.minecraft.world.IWorld;

public interface IGuiHandler {
	Object getServerGuiElement(int var1, IEntityPlayer var2, IWorld var3, int var4, int var5, int var6);
	
	Object getClientGuiElement(int var1, IEntityPlayer var2, IWorld var3, int var4, int var5, int var6);
}
