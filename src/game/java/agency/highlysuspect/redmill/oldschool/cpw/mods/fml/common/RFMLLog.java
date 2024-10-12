package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

import java.util.logging.Logger;

public class RFMLLog implements IFMLLog {
	public static Logger getLogger() {
		return Logger.getGlobal();
	}
}
