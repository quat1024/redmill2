package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public @interface RMod {
	String modid();
	boolean useMetadata();
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface RPreInit {}
}
