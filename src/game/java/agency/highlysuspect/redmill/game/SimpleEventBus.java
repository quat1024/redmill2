package agency.highlysuspect.redmill.game;

import agency.highlysuspect.redmill.svc.Consts;
import net.jodah.typetools.TypeResolver;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.IEventBus;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Copypasta of the NeoForge EventBus methods I didn't need to override
 */
public interface SimpleEventBus extends IEventBus {
	<T extends Event> void addListener(final EventPriority priority, @Nullable Predicate<? super T> filter, final Class<T> eventClass, final Consumer<T> consumer);
	
	default <T extends Event> void addListener(final EventPriority priority, @Nullable Predicate<? super T> filter, final Consumer<T> consumer) {
		Class<T> eventClass = getEventClass(consumer);
		if (Objects.equals(eventClass, Event.class))
			Consts.LOG.warn("Attempting to add a Lambda listener with computed generic type of Event. " +
				"Are you sure this is what you meant? NOTE : there are complex lambda forms where " +
				"the generic type information is erased and cannot be recovered at runtime.");
		addListener(priority, filter, eventClass, consumer);
	}
	
	@SuppressWarnings("unchecked")
	default<T extends Event> Class<T> getEventClass(Consumer<T> consumer) {
		final Class<T> eventClass = (Class<T>) TypeResolver.resolveRawArgument(Consumer.class, consumer.getClass());
		if ((Class<?>)eventClass == TypeResolver.Unknown.class) {
			Consts.LOG.error("Failed to resolve handler for \"{}\"", consumer.toString());
			throw new IllegalStateException("Failed to resolve consumer event type: " + consumer.toString());
		}
		return eventClass;
	}
	
	@Nullable
	default <T extends Event> Predicate<T> passNotGenericFilter(boolean receiveCanceled) {
		return receiveCanceled ? null : e -> !((ICancellableEvent) e).isCanceled();
	}
	
	@Override
	default <T extends Event> void addListener(final Consumer<T> consumer) {
		addListener(EventPriority.NORMAL, consumer);
	}
	
	@Override
	default <T extends Event> void addListener(final EventPriority priority, final Consumer<T> consumer) {
		addListener(priority, false, consumer);
	}
	
	@Override
	default <T extends Event> void addListener(final EventPriority priority, final boolean receiveCanceled, final Consumer<T> consumer) {
		addListener(priority, passNotGenericFilter(receiveCanceled), consumer);
	}
	
	@Override
	default <T extends Event> void addListener(EventPriority priority, Class<T> eventType, Consumer<T> consumer) {
		addListener(priority, false, eventType, consumer);
	}
	
	@Override
	default <T extends Event> void addListener(boolean receiveCanceled, Consumer<T> consumer) {
		addListener(EventPriority.NORMAL, receiveCanceled, consumer);
	}
	
	@Override
	default <T extends Event> void addListener(boolean receiveCanceled, Class<T> eventType, Consumer<T> consumer) {
		addListener(EventPriority.NORMAL, receiveCanceled, eventType, consumer);
	}
	
	@Override
	default <T extends Event> void addListener(Class<T> eventType, Consumer<T> consumer) {
		addListener(EventPriority.NORMAL, false, eventType, consumer);
	}
	
	@Override
	default <T extends Event> void addListener(final EventPriority priority, final boolean receiveCanceled, final Class<T> eventType, final Consumer<T> consumer) {
		addListener(priority, passNotGenericFilter(receiveCanceled), eventType, consumer);
	}
}
