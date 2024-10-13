package agency.highlysuspect.redmill.svc;

import net.neoforged.fml.loading.progress.StartupNotificationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;

public class Consts {
	//note that this is not the modid of 'plugin'. plugin isn't a mod
	public static final String MODID = "redmill2";
	
	public static final String REDMILL_TRANSFORMATION_SERVICE = "redmill_launch_plugin_service_springboard";
	public static final String REDMILL_LAUNCH_PLUGIN_SERVICE = "redmill_launch_plugin_service";
	public static final String REDMILL_LANGUAGE_LOADER = "redmill_language_loader";
	
	public static final Logger LOG = LogManager.getLogger("Red Mill");
	
	public static void windowLog(String message, Object... args)  {
		Message msg = LOG.getMessageFactory().newMessage(message, args);
		StartupNotificationManager.addModMessage("(Red Mill) " + msg.getFormattedMessage());
		LOG.info(msg);
	}
}
