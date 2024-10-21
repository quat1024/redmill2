package agency.highlysuspect.redmill.game;

import agency.highlysuspect.redmill.svc.Consts;
import agency.highlysuspect.redmill.svc.util.IBastion;
import net.neoforged.bus.api.IEventBus;

/**
 * TODO I don't actually think we need this
 */
@SuppressWarnings("unused") //reflectively accessed
public class Bastion implements IBastion {
	static {
		Consts.LOG.info("(Red Mill) Hello from the Bastion!");
		Consts.LOG.info("My module is: {}", Bastion.class.getModule());
		Consts.LOG.info("My module layer is: {}", Bastion.class.getModule().getLayer());
	}
	
	@SuppressWarnings("unused") //reflectively accessed
	public static final IBastion INSTANCE = new Bastion();
	
	public final IEventBus EVENT_BUS = new RedmillNeoforgeEventBus();
	
	@Override
	public IEventBus getEventBus() {
		return EVENT_BUS;
	}
}
