//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.net.minecraftforge.event.world;

import agency.highlysuspect.redmill.oldschool.net.minecraft.world.IWorld;
import agency.highlysuspect.redmill.oldschool.net.minecraftforge.event.Event;

public abstract class WorldEvent extends Event {
	public final IWorld world;
	
	public WorldEvent(IWorld var1) {
		this.world = var1;
	}
	
	public IWorld get_world() {
		return this.world;
	}
	
	public static class Load extends WorldEvent {
		public Load(IWorld var1) {
			super(var1);
		}
	}
	
	public static class Save extends WorldEvent {
		public Save(IWorld var1) {
			super(var1);
		}
	}
	
	public static class Unload extends WorldEvent {
		public Unload(IWorld var1) {
			super(var1);
		}
	}
	
}
