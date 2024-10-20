//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

import java.util.EnumSet;

public enum TickType {
	WORLD,
	RENDER,
	/** @deprecated */
	@Deprecated
	GUI,
	/** @deprecated */
	@Deprecated
	CLIENTGUI,
	WORLDLOAD,
	CLIENT,
	PLAYER,
	SERVER;
	
	private TickType() {
	}
	
	public EnumSet<TickType> partnerTicks() {
		if (this == CLIENT) {
			return EnumSet.of(RENDER);
		} else if (this == RENDER) {
			return EnumSet.of(CLIENT);
		} else if (this == GUI) {
			return EnumSet.of(CLIENTGUI);
		} else {
			return this == CLIENTGUI ? EnumSet.of(GUI) : EnumSet.noneOf(TickType.class);
		}
	}
	
	public static TickType get_WORLD() {
		return WORLD;
	}
	
	public static TickType get_RENDER() {
		return RENDER;
	}
	
	public static TickType get_GUI() {
		return GUI;
	}
	
	public static TickType get_CLIENTGUI() {
		return CLIENTGUI;
	}
	
	public static TickType get_WORLDLOAD() {
		return WORLDLOAD;
	}
	
	public static TickType get_CLIENT() {
		return CLIENT;
	}
	
	public static TickType get_PLAYER() {
		return PLAYER;
	}
	
	public static TickType get_SERVER() {
		return SERVER;
	}
}
