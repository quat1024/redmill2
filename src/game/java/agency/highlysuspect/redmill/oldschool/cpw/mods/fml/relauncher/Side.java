package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher;

import net.neoforged.api.distmarker.Dist;

public enum Side {
	CLIENT,
	SERVER,
	BUKKIT;
	
	public boolean isServer() {
		return this != CLIENT;
	}
	
	public boolean isClient() {
		return this == CLIENT;
	}
	
	public static Side get_CLIENT() {
		return CLIENT;
	}
	
	public static Side get_SERVER() {
		return SERVER;
	}
	
	public static Side get_BUKKIT() {
		return BUKKIT;
	}
	
	public static Side redmill$fromModern(Dist dist) {
		return switch(dist) {
			case Dist.CLIENT -> CLIENT;
			case Dist.DEDICATED_SERVER -> SERVER;
		};
	}
}
