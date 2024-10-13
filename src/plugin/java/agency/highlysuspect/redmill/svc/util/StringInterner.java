package agency.highlysuspect.redmill.svc.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringInterner {
	private final Map<String, String> map = new HashMap<>();
	
	public String intern(String s) {
		return map.computeIfAbsent(s, ss -> ss);
	}
	
	public List<String> dedupList(List<String> list) {
		list.replaceAll(this::intern);
		return list;
	}
}
