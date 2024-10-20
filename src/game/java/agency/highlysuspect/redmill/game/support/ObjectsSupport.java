package agency.highlysuspect.redmill.game.support;

public class ObjectsSupport {
	public static <T> T firstNonNull(T a, T b) {
		if(a != null) return a;
		else return b;
	}
}
