//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

final class FMLLogFormatter extends Formatter {
	static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	FMLLogFormatter() {
	}
	
	public String format(LogRecord var1) {
		StringBuilder var2 = new StringBuilder();
		var2.append(this.get_dateFormat().format(var1.getMillis()));
		Level var3 = var1.getLevel();
		String var4 = var3.getLocalizedName();
		if (var4 == null) {
			var4 = var3.getName();
		}
		
		if (var4 != null && var4.length() > 0) {
			var2.append(" [" + var4 + "] ");
		} else {
			var2.append(" ");
		}
		
		if (var1.getLoggerName() != null) {
			var2.append("[" + var1.getLoggerName() + "] ");
		} else {
			var2.append("[] ");
		}
		
		var2.append(var1.getMessage());
		var2.append(get_LINE_SEPARATOR());
		Throwable var5 = var1.getThrown();
		if (var5 != null) {
			StringWriter var6 = new StringWriter();
			var5.printStackTrace(new PrintWriter(var6));
			var2.append(var6.toString());
		}
		
		return var2.toString();
	}
	
	public static String get_LINE_SEPARATOR() {
		return LINE_SEPARATOR;
	}
	
	public SimpleDateFormat get_dateFormat() {
		return this.dateFormat;
	}
	
	public void set_dateFormat(SimpleDateFormat var1) {
		this.dateFormat = var1;
	}
}
