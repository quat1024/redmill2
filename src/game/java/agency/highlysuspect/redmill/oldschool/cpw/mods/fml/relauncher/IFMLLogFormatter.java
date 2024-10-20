package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher;

import java.text.SimpleDateFormat;
import java.util.logging.LogRecord;

public interface IFMLLogFormatter {
	static String get_LINE_SEPARATOR() {
		return RFMLLogFormatter.LINE_SEPARATOR;
	}
	
	String format(LogRecord var1);
	
	SimpleDateFormat get_dateFormat();
	
	void set_dateFormat(SimpleDateFormat var1);
}
