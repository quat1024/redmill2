package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher;

import net.neoforged.api.distmarker.Dist;

public enum RSide implements ISide {
	CLIENT,
	SERVER,
	BUKKIT;
	
	@Override
	public boolean isServer() {
		return this != CLIENT;
	}
	
	@Override
	public boolean isClient() {
		return this == CLIENT;
	}
	
	public static ISide get_CLIENT() {
		return CLIENT;
	}
	
	public static ISide get_SERVER() {
		return SERVER;
	}
	
	public static ISide get_BUKKIT() {
		return BUKKIT;
	}
	
	public static RSide redmill$fromModern(Dist dist) {
		return switch(dist) {
			case Dist.CLIENT -> CLIENT;
			case Dist.DEDICATED_SERVER -> SERVER;
		};
	}
}
