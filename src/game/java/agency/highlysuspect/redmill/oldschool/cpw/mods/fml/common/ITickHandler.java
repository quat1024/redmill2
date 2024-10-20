package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

import java.util.EnumSet;

public interface ITickHandler {
	void tickStart(EnumSet<TickType> types, Object... hmm);
	void tickEnd(EnumSet<TickType> types, Object... hmm);
	EnumSet<TickType> ticks();
	String getLabel();
}
