//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.registry;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.IScheduledTickHandler;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.ITickHandler;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.SingleIntervalHandler;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.Side;
import com.google.common.collect.Queues;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicLong;

public class TickRegistry {
	private static PriorityQueue clientTickHandlers = Queues.newPriorityQueue();
	private static PriorityQueue serverTickHandlers = Queues.newPriorityQueue();
	private static AtomicLong clientTickCounter = new AtomicLong();
	private static AtomicLong serverTickCounter = new AtomicLong();
	
	public TickRegistry() {
	}
	
	public static void registerScheduledTickHandler(IScheduledTickHandler var0, Side var1) {
		getQueue(var1).add(new TickQueueElement(var0, getCounter(var1).get()));
	}
	
	private static PriorityQueue getQueue(Side var0) {
		return var0.isClient() ? clientTickHandlers : serverTickHandlers;
	}
	
	private static AtomicLong getCounter(Side var0) {
		return var0.isClient() ? clientTickCounter : serverTickCounter;
	}
	
	public static void registerTickHandler(ITickHandler var0, Side var1) {
		registerScheduledTickHandler(new SingleIntervalHandler(var0), var1);
	}
	
	public static void updateTickQueue(List var0, Side var1) {
		synchronized(var0) {
			var0.clear();
			long var3 = getCounter(var1).incrementAndGet();
			PriorityQueue var5 = getQueue(var1);
			
			while(var5.size() != 0 && ((TickQueueElement)var5.peek()).scheduledNow(var3)) {
				TickQueueElement var6 = (TickQueueElement)var5.poll();
				var6.update(var3);
				var5.offer(var6);
				var0.add(var6.get_ticker());
			}
			
		}
	}
	
	public static PriorityQueue get_clientTickHandlers() {
		return clientTickHandlers;
	}
	
	public static void set_clientTickHandlers(PriorityQueue var0) {
		clientTickHandlers = var0;
	}
	
	public static PriorityQueue get_serverTickHandlers() {
		return serverTickHandlers;
	}
	
	public static void set_serverTickHandlers(PriorityQueue var0) {
		serverTickHandlers = var0;
	}
	
	public static AtomicLong get_clientTickCounter() {
		return clientTickCounter;
	}
	
	public static void set_clientTickCounter(AtomicLong var0) {
		clientTickCounter = var0;
	}
	
	public static AtomicLong get_serverTickCounter() {
		return serverTickCounter;
	}
	
	public static void set_serverTickCounter(AtomicLong var0) {
		serverTickCounter = var0;
	}
	
	public static class TickQueueElement implements Comparable<TickQueueElement> {
		private long next;
		public IScheduledTickHandler ticker;
		
		public TickQueueElement(IScheduledTickHandler var1, long var2) {
			this.ticker = var1;
			this.update(var2);
		}
		
		public int compareTo(TickQueueElement var1) {
			return (int)(this.get_next() - var1.get_next());
		}
		
		public void update(long var1) {
			this.set_next(var1 + (long)Math.max(this.get_ticker().nextTickSpacing(), 1));
		}
		
		public boolean scheduledNow(long var1) {
			return var1 >= this.get_next();
		}
		
		public long get_next() {
			return this.next;
		}
		
		public void set_next(long var1) {
			this.next = var1;
		}
		
		public IScheduledTickHandler get_ticker() {
			return this.ticker;
		}
		
		public void set_ticker(IScheduledTickHandler var1) {
			this.ticker = var1;
		}
	}
	
}
