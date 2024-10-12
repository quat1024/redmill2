package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

import java.util.EnumSet;

public interface IITickHandler {
	<T extends Enum<T> & ITickType> void tickStart(EnumSet<T> types, Object... hmm);
	<T extends Enum<T> & ITickType> void tickEnd(EnumSet<T> types, Object... hmm);
	<T extends Enum<T> & ITickType> EnumSet<T> ticks();
	String getLabel();
}
