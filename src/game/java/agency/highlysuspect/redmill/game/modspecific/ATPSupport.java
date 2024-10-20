package agency.highlysuspect.redmill.game.modspecific;

import java.util.Map;

/**
 * @see agency.highlysuspect.redmill.svc.transformer.modspecific.AutoThirdPersonProcessor
 */
public class ATPSupport {
	@SuppressWarnings("unchecked")
	public static <K, V> V get(Map<K, V> map, K key) {
		if("fixHandGlitch".equals(key) || "sneakDismount".equals(key)) {
			return (V) Boolean.valueOf(false);
		} else {
			return map.get(key);
		}
	}
}
