package agency.highlysuspect.redmill.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringDeduplicator {
	private final Map<String, String> map = new HashMap<>();
	
	public String dedup(String s) {
		return map.computeIfAbsent(s, ss -> ss);
	}
	
	public List<String> dedupList(List<String> list) {
		list.replaceAll(this::dedup);
		return list;
	}
}
