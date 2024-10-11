package agency.highlysuspect.redmill;

import com.google.common.collect.Sets;
import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CheekyGlobalState {
	public static final Set<Type> PREMILLED_CLASSES = Sets.newConcurrentHashSet();
	
	public static final Map<String, String> MODERN_TO_OLD_MODIDS = new ConcurrentHashMap<>();
	public static final Map<String, String> OLDID_TO_MODULE_NAME = new ConcurrentHashMap<>();
}
