package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

public enum TickType {
	CLIENT,
	RENDER;
	
	public static TickType get_CLIENT() {
		return CLIENT;
	}
	
	public static TickType get_RENDER() {
		return RENDER;
	}
}
