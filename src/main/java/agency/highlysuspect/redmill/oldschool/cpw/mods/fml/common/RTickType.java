package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

public enum RTickType implements ITickType {
	CLIENT,
	RENDER;
	
	public ITickType get_CLIENT() {
		return CLIENT;
	}
	
	public ITickType get_RENDER() {
		return RENDER;
	}
}
