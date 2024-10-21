package agency.highlysuspect.redmill.game;

import agency.highlysuspect.redmill.svc.Globals;
import net.neoforged.bus.BusBuilderImpl;
import net.neoforged.bus.EventBus;
import net.neoforged.bus.api.BusBuilder;
import net.neoforged.bus.api.EventListener;
import net.neoforged.bus.api.SubscribeEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RedmillNeoforgeEventBus extends EventBus {
	public RedmillNeoforgeEventBus() {
		super((BusBuilderImpl) BusBuilder.builder().allowPerPhasePost());
		
		try {
			Field f = EventBus.class.getDeclaredField("listeners");
			f.setAccessible(true);
			listeners2 = (ConcurrentHashMap<Object, List<EventListener>>) f.get(this);
			
			actualRegisterMethod = EventBus.class.getDeclaredMethod("register", Class.class, Object.class, Method.class);
			actualRegisterMethod.setAccessible(true);
		} catch (Exception e) {
			throw Globals.mkRethrow(e, "couldn't reflect eventbus");
		}
	}
	
	protected final ConcurrentHashMap<Object, List<EventListener>> listeners2;
	protected final Method actualRegisterMethod;
	
	@Override
	public void register(Object target) {
		if(listeners2.containsKey(target)) {
			return;
		}
		
		boolean wantStatic = target.getClass() == Class.class;
		Class<?> clazz = wantStatic ? (Class<?>) target : target.getClass();
		
		for(Method method : clazz.getDeclaredMethods())  {
			if(!method.isAnnotationPresent(SubscribeEvent.class)) continue;
			
			if(Modifier.isStatic(method.getModifiers()) == wantStatic){
				try {
					//eventType, target, real
					Object eventType = method.getParameterTypes()[0];
					actualRegisterMethod.invoke(this, eventType, target, method);
				} catch (Exception e) {
					throw Globals.mkRethrow(e, "couldn't register listener");
				}
			}
		}
	}
}
