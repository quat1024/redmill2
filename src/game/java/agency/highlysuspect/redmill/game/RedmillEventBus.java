package agency.highlysuspect.redmill.game;

import agency.highlysuspect.redmill.svc.Consts;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class RedmillEventBus implements SimpleEventBus {
	@Override
	public <T extends Event> void addListener(EventPriority priority, @Nullable Predicate<? super T> filter, Class<T> eventClass, Consumer<T> consumer) {
		Consts.LOG.info("addListener {} {} {} {}", priority, filter, eventClass, consumer);
	}
	
	@Override
	public void register(Object target) {
		Consts.LOG.info("register {}", target);
	}
	
	@Override
	public void unregister(Object object) {
		Consts.LOG.info("unregister {}", object);
	}
	
	@Override
	public <T extends Event> T post(T event) {
		Consts.LOG.info("post {}", event);
		return event;
	}
	
	@Override
	public <T extends Event> T post(EventPriority phase, T event) {
		//Consts.LOG.info("post/phase {} {}", phase, event); //spammy
		return event;
	}
	
	@Override
	public void start() {
		Consts.LOG.info("start");
	}
}
