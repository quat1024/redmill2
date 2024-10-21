//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.net.minecraftforge.event;

public class Event {
	private boolean isCanceled = false;
	private final boolean isCancelable;
	private Result result;
	private final boolean hasResult;
	//private static ListenerList listeners = new ListenerList();
	
	public Event() {
		this.result = Result.DEFAULT;
		this.setup();
//		this.isCancelable = this.hasAnnotation(Cancelable.class);
//		this.hasResult = this.hasAnnotation(HasResult.class);
		this.isCancelable = false; //TODO
		this.hasResult = false;
	}
	
	private boolean hasAnnotation(Class var1) {
//		for(Class var2 = this.getClass(); var2 != Event.class; var2 = var2.getSuperclass()) {
//			if (var2.isAnnotationPresent(Cancelable.class)) {
//				return true;
//			}
//		}
		
		return false;
	}
	
	public boolean isCancelable() {
		return this.get_isCancelable();
	}
	
	public boolean isCanceled() {
		return this.get_isCanceled();
	}
	
	public void setCanceled(boolean var1) {
		if (!this.isCancelable()) {
			throw new IllegalArgumentException("Attempted to cancel a uncancelable event");
		} else {
			this.set_isCanceled(var1);
		}
	}
	
	public boolean hasResult() {
		return this.get_hasResult();
	}
	
	public Result getResult() {
		return this.get_result();
	}
	
	public void setResult(Result var1) {
		this.set_result(var1);
	}
	
	protected void setup() {
	}
	
//	public ListenerList getListenerList() {
//		return get_listeners();
//	}
	
	public boolean get_isCanceled() {
		return this.isCanceled;
	}
	
	public void set_isCanceled(boolean var1) {
		this.isCanceled = var1;
	}
	
	public boolean get_isCancelable() {
		return this.isCancelable;
	}
	
	public Result get_result() {
		return this.result;
	}
	
	public void set_result(Result var1) {
		this.result = var1;
	}
	
	public boolean get_hasResult() {
		return this.hasResult;
	}
	
//	public static ListenerList get_listeners() {
//		return listeners;
//	}
//
//	public static void set_listeners(ListenerList var0) {
//		listeners = var0;
//	}
	
	public enum Result {
		DENY,
		DEFAULT,
		ALLOW;
		
		public static Result get_DENY() {
			return DENY;
		}
		
		public static Result get_DEFAULT() {
			return DEFAULT;
		}
		
		public static Result get_ALLOW() {
			return ALLOW;
		}
	}
	
}
