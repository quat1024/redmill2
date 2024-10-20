package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.client.registry;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.ITickHandler;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.TickType;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.Side;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.settings.IGameSettings;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.settings.IKeyBinding;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.settings.RKeyBinding;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.EnumSet;
import java.util.Set;

public class KeyBindingRegistry {
	private static final KeyBindingRegistry INSTANCE = new KeyBindingRegistry();
	private Set<KeyHandler> keyHandlers = Sets.newLinkedHashSet();
	
	public KeyBindingRegistry() {
	}
	
	public static void registerKeyBinding(KeyHandler var0) {
		instance().keyHandlers.add(var0);
//		if (!KeyHandler.access$000(var0)) { //RM-TODO what is this
//			TickRegistry.registerTickHandler(var0, Side.CLIENT);
//		}
	
	}
	
	/** @deprecated */
	@Deprecated
	public static KeyBindingRegistry instance() {
		return INSTANCE;
	}
	
	public void uploadKeyBindingsToGame(IGameSettings var1) {
		//RM-TODO; also a missed remapping on the arrays
//		ArrayList var2 = Lists.newArrayList();
//		Iterator var3 = this.get_keyHandlers().iterator();
//
//		while(var3.hasNext()) {
//			KeyBindingRegistry$KeyHandler var4 = (KeyBindingRegistry$KeyHandler)var3.next();
//			IKeyBinding[] var5 = var4.get_keyBindings();
//			int var6 = var5.length;
//
//			for(int var7 = 0; var7 < var6; ++var7) {
//				IKeyBinding var8 = var5[var7];
//				var2.add(var8);
//			}
//		}
//
//		KeyBinding[] var9 = (KeyBinding[])var2.toArray(new RKeyBinding[var2.size()]);
//		RKeyBinding[] var10 = new RKeyBinding[var1.get_keyBindings().length + var9.length];
//		System.arraycopy(var1.get_keyBindings(), 0, var10, 0, var1.get_keyBindings().length);
//		System.arraycopy(var9, 0, var10, var1.get_keyBindings().length, var9.length);
//		var1.set_keyBindings(var10);
//		var1.loadOptions();
	}
	
	public static KeyBindingRegistry get_INSTANCE() {
		return INSTANCE;
	}
	
	public Set<KeyHandler> get_keyHandlers() {
		return this.keyHandlers;
	}
	
	public void set_keyHandlers(Set<KeyHandler> var1) {
		this.keyHandlers = var1;
	}
	
	public abstract static class KeyHandler implements ITickHandler {
		protected IKeyBinding[] keyBindings;
		protected boolean[] keyDown;
		protected boolean[] repeatings;
		private boolean isDummy;
		
		public KeyHandler(IKeyBinding[] var1, boolean[] var2) {
			assert var1.length == var2.length : "You need to pass two arrays of identical length";
			
			this.keyBindings = var1;
			this.repeatings = var2;
			this.keyDown = new boolean[var1.length];
		}
		
		public KeyHandler(IKeyBinding[] var1) {
			this.keyBindings = var1;
			this.isDummy = true;
		}
		
		public IKeyBinding[] getKeyBindings() {
			return this.get_keyBindings();
		}
		
		public final void tickStart(EnumSet<TickType> var1, Object... var2) {
			this.keyTick(var1, false);
		}
		
		public final void tickEnd(EnumSet<TickType> var1, Object... var2) {
			this.keyTick(var1, true);
		}
		
		private void keyTick(EnumSet<TickType> var1, boolean var2) {
			for(int var3 = 0; var3 < this.get_keyBindings().length; ++var3) {
				IKeyBinding var4 = this.get_keyBindings()[var3];
//				int var5 = var4.get_keyCode();
//				boolean var6 = var5 < 0 ? Mouse.isButtonDown(var5 + 100) : Keyboard.isKeyDown(var5);
				boolean var6 = false; //RM-TODO mouse buttons
				if (var6 != this.get_keyDown()[var3] || var6 && this.get_repeatings()[var3]) {
					if (var6) {
						this.keyDown(var1, var4, var2, var6 != this.get_keyDown()[var3]);
					} else {
						this.keyUp(var1, var4, var2);
					}
					
					if (var2) {
						this.get_keyDown()[var3] = var6;
					}
				}
			}
			
		}
		
		public abstract void keyDown(EnumSet<TickType> var1, IKeyBinding var2, boolean var3, boolean var4);
		
		public abstract void keyUp(EnumSet<TickType> var1, IKeyBinding var2, boolean var3);
		
		public abstract EnumSet<TickType> ticks();
		
		public IKeyBinding[] get_keyBindings() {
			return this.keyBindings;
		}
		
		public void set_keyBindings(IKeyBinding[] var1) {
			this.keyBindings = var1;
		}
		
		public boolean[] get_keyDown() {
			return this.keyDown;
		}
		
		public void set_keyDown(boolean[] var1) {
			this.keyDown = var1;
		}
		
		public boolean[] get_repeatings() {
			return this.repeatings;
		}
		
		public void set_repeatings(boolean[] var1) {
			this.repeatings = var1;
		}
		
		public boolean get_isDummy() {
			return this.isDummy;
		}
		
		public void set_isDummy(boolean var1) {
			this.isDummy = var1;
		}
	}
}
