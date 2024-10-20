//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

import java.util.EnumSet;

public class SingleIntervalHandler implements IScheduledTickHandler {
	private ITickHandler wrapped;
	
	public SingleIntervalHandler(ITickHandler var1) {
		this.wrapped = var1;
	}
	
	public void tickStart(EnumSet<TickType> var1, Object... var2) {
		this.get_wrapped().tickStart(var1, var2);
	}
	
	public void tickEnd(EnumSet<TickType> var1, Object... var2) {
		this.get_wrapped().tickEnd(var1, var2);
	}
	
	public EnumSet<TickType> ticks() {
		return this.get_wrapped().ticks();
	}
	
	public String getLabel() {
		return this.get_wrapped().getLabel();
	}
	
	public int nextTickSpacing() {
		return 1;
	}
	
	public ITickHandler get_wrapped() {
		return this.wrapped;
	}
	
	public void set_wrapped(ITickHandler var1) {
		this.wrapped = var1;
	}
}
