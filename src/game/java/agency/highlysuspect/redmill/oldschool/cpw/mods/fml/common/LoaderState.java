//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLConstructionEvent;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLInitializationEvent;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLPostInitializationEvent;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLPreInitializationEvent;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLServerStartedEvent;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLServerStartingEvent;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLServerStoppedEvent;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLServerStoppingEvent;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLStateEvent;
import com.google.common.base.Throwables;

public enum LoaderState {
	NOINIT("Uninitialized", (Class)null),
	LOADING("Loading", (Class)null),
	CONSTRUCTING("Constructing mods", FMLConstructionEvent.class),
	PREINITIALIZATION("Pre-initializing mods", FMLPreInitializationEvent.class),
	INITIALIZATION("Initializing mods", FMLInitializationEvent.class),
	POSTINITIALIZATION("Post-initializing mods", FMLPostInitializationEvent.class),
	AVAILABLE("Mod loading complete", FMLLoadCompleteEvent.class),
	SERVER_ABOUT_TO_START("Server about to start", FMLServerAboutToStartEvent.class),
	SERVER_STARTING("Server starting", FMLServerStartingEvent.class),
	SERVER_STARTED("Server started", FMLServerStartedEvent.class),
	SERVER_STOPPING("Server stopping", FMLServerStoppingEvent.class),
	SERVER_STOPPED("Server stopped", FMLServerStoppedEvent.class),
	ERRORED("Mod Loading errored", (Class)null);
	
	private Class eventClass;
	private String name;
	
	private LoaderState(String var3, Class var4) {
		this.name = var3;
		this.eventClass = var4;
	}
	
	public LoaderState transition(boolean var1) {
		if (var1) {
			return ERRORED;
		} else {
			return this == SERVER_STOPPED ? AVAILABLE : values()[this.ordinal() < values().length ? this.ordinal() + 1 : this.ordinal()];
		}
	}
	
	public boolean hasEvent() {
		return this.get_eventClass() != null;
	}
	
	public FMLStateEvent getEvent(Object... var1) {
		try {
			return (FMLStateEvent)this.get_eventClass().getConstructor(Object[].class).newInstance((Object)var1);
		} catch (Exception var3) {
			throw Throwables.propagate(var3);
		}
	}
	
	public LoaderState requiredState() {
		return this == NOINIT ? NOINIT : values()[this.ordinal() - 1];
	}
	
	public static LoaderState get_NOINIT() {
		return NOINIT;
	}
	
	public static LoaderState get_LOADING() {
		return LOADING;
	}
	
	public static LoaderState get_CONSTRUCTING() {
		return CONSTRUCTING;
	}
	
	public static LoaderState get_PREINITIALIZATION() {
		return PREINITIALIZATION;
	}
	
	public static LoaderState get_INITIALIZATION() {
		return INITIALIZATION;
	}
	
	public static LoaderState get_POSTINITIALIZATION() {
		return POSTINITIALIZATION;
	}
	
	public static LoaderState get_AVAILABLE() {
		return AVAILABLE;
	}
	
	public static LoaderState get_SERVER_ABOUT_TO_START() {
		return SERVER_ABOUT_TO_START;
	}
	
	public static LoaderState get_SERVER_STARTING() {
		return SERVER_STARTING;
	}
	
	public static LoaderState get_SERVER_STARTED() {
		return SERVER_STARTED;
	}
	
	public static LoaderState get_SERVER_STOPPING() {
		return SERVER_STOPPING;
	}
	
	public static LoaderState get_SERVER_STOPPED() {
		return SERVER_STOPPED;
	}
	
	public static LoaderState get_ERRORED() {
		return ERRORED;
	}
	
	public Class get_eventClass() {
		return this.eventClass;
	}
	
	public void set_eventClass(Class var1) {
		this.eventClass = var1;
	}
	
	public String get_name() {
		return this.name;
	}
	
	public void set_name(String var1) {
		this.name = var1;
	}
	
	public enum ModState {
		UNLOADED("Unloaded"),
		LOADED("Loaded"),
		CONSTRUCTED("Constructed"),
		PREINITIALIZED("Pre-initialized"),
		INITIALIZED("Initialized"),
		POSTINITIALIZED("Post-initialized"),
		AVAILABLE("Available"),
		DISABLED("Disabled"),
		ERRORED("Errored");
		
		private String label;
		
		private ModState(String var3) {
			this.label = var3;
		}
		
		public String toString() {
			return this.get_label();
		}
		
		public static ModState get_UNLOADED() {
			return UNLOADED;
		}
		
		public static ModState get_LOADED() {
			return LOADED;
		}
		
		public static ModState get_CONSTRUCTED() {
			return CONSTRUCTED;
		}
		
		public static ModState get_PREINITIALIZED() {
			return PREINITIALIZED;
		}
		
		public static ModState get_INITIALIZED() {
			return INITIALIZED;
		}
		
		public static ModState get_POSTINITIALIZED() {
			return POSTINITIALIZED;
		}
		
		public static ModState get_AVAILABLE() {
			return AVAILABLE;
		}
		
		public static ModState get_DISABLED() {
			return DISABLED;
		}
		
		public static ModState get_ERRORED() {
			return ERRORED;
		}
		
		public String get_label() {
			return this.label;
		}
		
		public void set_label(String var1) {
			this.label = var1;
		}
	}
	
}
