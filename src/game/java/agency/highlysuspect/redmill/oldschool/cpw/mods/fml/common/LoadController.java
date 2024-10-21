//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

import agency.highlysuspect.redmill.game.logging.LegacyLogger;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.LoaderState.ModState;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLEvent;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLLoadEvent;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLPreInitializationEvent;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLStateEvent;
import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import net.neoforged.fml.loading.progress.ProgressMeter;
import net.neoforged.fml.loading.progress.StartupNotificationManager;
import net.neoforged.fml.util.LoaderException;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class LoadController {
	private Loader loader;
	private EventBus masterChannel;
	private ImmutableMap eventChannels;
	private LoaderState state;
	private Multimap modStates = ArrayListMultimap.create();
	private Multimap errors = ArrayListMultimap.create();
	private Map modList;
	private List activeModList = Lists.newArrayList();
	private ModContainer activeContainer;
	private BiMap modObjectList;
	
	private ProgressMeter redmill$progressMeter;
	
	public LoadController(Loader var1) {
		this.loader = var1;
		this.masterChannel = new EventBus("FMLMainChannel");
		this.masterChannel.register(this);
		this.state = LoaderState.NOINIT;
		
		redmill$progressMeter = StartupNotificationManager.addProgressBar("Red Mill LoadController",
			LoaderState.AVAILABLE.ordinal());
	}
	
	@Subscribe
	public void buildModList(FMLLoadEvent var1) {
		this.set_modList(this.get_loader().getIndexedModList());
		ImmutableMap.Builder var2 = ImmutableMap.builder();
		Iterator var3 = this.get_loader().getModList().iterator();
		
		while(var3.hasNext()) {
			ModContainer var4 = (ModContainer)var3.next();
			EventBus var5 = new EventBus(var4.getModId());
			boolean var6 = var4.registerBus(var5, this);
			if (var6) {
				Level var7 = LegacyLogger.getLogger(var4.getModId()).getLevel();
				FMLLog.log(var4.getModId(), Level.FINE, "Mod Logging channel %s configured at %s level.", new Object[]{var4.getModId(), var7 == null ? "default" : var7});
				FMLLog.log(var4.getModId(), Level.INFO, "Activating mod %s", new Object[]{var4.getModId()});
				this.get_activeModList().add(var4);
				this.get_modStates().put(var4.getModId(), ModState.UNLOADED);
				var2.put(var4.getModId(), var5);
			} else {
				FMLLog.log(var4.getModId(), Level.WARNING, "Mod %s has been disabled through configuration", new Object[]{var4.getModId()});
				this.get_modStates().put(var4.getModId(), ModState.UNLOADED);
				this.get_modStates().put(var4.getModId(), ModState.DISABLED);
			}
		}
		
		this.set_eventChannels(var2.build());
	}
	
	public void distributeStateMessage(LoaderState var1, Object... var2) {
		if (var1.hasEvent()) {
			this.get_masterChannel().post(var1.getEvent(var2));
		}
		
	}
	
	public void transition(LoaderState var1) {
		LoaderState var2 = this.get_state();
		this.set_state(this.get_state().transition(!this.get_errors().isEmpty()));
		if (this.get_state() != var1) {
			Throwable var3 = null;
			FMLLog.severe("Fatal errors were detected during the transition from %s to %s. Loading cannot continue", new Object[]{var2, var1});
			StringBuilder var4 = new StringBuilder();
			this.printModStates(var4);
			FMLLog.getLogger().severe(var4.toString());
			if (this.get_errors().size() > 0) {
				FMLLog.severe("The following problems were captured during this phase", new Object[0]);
				Iterator var5 = this.get_errors().entries().iterator();
				
				while(var5.hasNext()) {
					Map.Entry var6 = (Map.Entry)var5.next();
					FMLLog.log(Level.SEVERE, (Throwable)var6.getValue(), "Caught exception from %s", new Object[]{var6.getKey()});
					if (var6.getValue() instanceof IFMLHandledException) {
						var3 = (Throwable)var6.getValue();
					} else if (var3 == null) {
						var3 = (Throwable)var6.getValue();
					}
				}
				
				if (var3 != null && var3 instanceof RuntimeException) {
					throw (RuntimeException)var3;
				} else {
					throw new LoaderException(var3);
				}
			} else {
				FMLLog.severe("The ForgeModLoader state engine has become corrupted. Probably, a state was missed by and invalid modification to a base classForgeModLoader depends on. This is a critical error and not recoverable. Investigate any modifications to base classes outside ofForgeModLoader, especially Optifine, to see if there are fixes available.", new Object[0]);
				throw new RuntimeException("The ForgeModLoader state engine is invalid");
			}
		}
		
		if(redmill$progressMeter != null) {
			redmill$progressMeter.setAbsolute(this.state.ordinal());
			redmill$progressMeter.label("Red Mill LoadController: " + this.state.get_name());
			if(this.state == LoaderState.AVAILABLE) {
				redmill$progressMeter.complete();
				redmill$progressMeter = null;
			}
		}
	}
	
	public ModContainer activeContainer() {
		return this.get_activeContainer();
	}
	
	@Subscribe
	public void propogateStateMessage(FMLEvent var1) {
		if (var1 instanceof FMLPreInitializationEvent) {
			this.set_modObjectList(this.buildModObjectList());
		}
		
		Iterator var2 = this.get_activeModList().iterator();
		
		while(var2.hasNext()) {
			ModContainer var3 = (ModContainer)var2.next();
			this.set_activeContainer(var3);
			String var4 = var3.getModId();
			var1.applyModContainer(this.activeContainer());
			FMLLog.log(var4, Level.FINEST, "Sending event %s to mod %s", new Object[]{var1.getEventType(), var4});
			((EventBus)this.get_eventChannels().get(var4)).post(var1);
			FMLLog.log(var4, Level.FINEST, "Sent event %s to mod %s", new Object[]{var1.getEventType(), var4});
			this.set_activeContainer((ModContainer)null);
			if (var1 instanceof FMLStateEvent) {
				if (!this.get_errors().containsKey(var4)) {
					this.get_modStates().put(var4, ((FMLStateEvent)var1).getModState());
				} else {
					this.get_modStates().put(var4, ModState.ERRORED);
				}
			}
		}
		
	}
	
	public ImmutableBiMap buildModObjectList() {
		ImmutableBiMap.Builder var1 = ImmutableBiMap.builder();
		Iterator var2 = this.get_activeModList().iterator();
		
		while(var2.hasNext()) {
			ModContainer var3 = (ModContainer)var2.next();
			if (!var3.isImmutable() && var3.getMod() != null) {
				var1.put(var3, var3.getMod());
			}
			
			if (var3.getMod() == null && !var3.isImmutable() && this.get_state() != LoaderState.CONSTRUCTING) {
				FMLLog.severe("There is a severe problem with %s - it appears not to have constructed correctly", new Object[]{var3.getModId()});
				if (this.get_state() != LoaderState.CONSTRUCTING) {
					this.errorOccurred(var3, new RuntimeException());
				}
			}
		}
		
		return var1.build();
	}
	
	public void errorOccurred(ModContainer var1, Throwable var2) {
		if (var2 instanceof InvocationTargetException) {
			this.get_errors().put(var1.getModId(), ((InvocationTargetException)var2).getCause());
		} else {
			this.get_errors().put(var1.getModId(), var2);
		}
		
	}
	
	public void printModStates(StringBuilder var1) {
		Iterator var2 = this.get_loader().getModList().iterator();
		
		while(var2.hasNext()) {
			ModContainer var3 = (ModContainer)var2.next();
			var1.append("\n\t").append(var3.getModId()).append(" [").append(var3.getName()).append("] (").append(var3.getSource().getName()).append(") ");
			Joiner.on("->").appendTo(var1, this.get_modStates().get(var3.getModId()));
		}
		
	}
	
	public List getActiveModList() {
		return this.get_activeModList();
	}
	
	public LoaderState.ModState getModState(ModContainer var1) {
		return (LoaderState.ModState)Iterables.getLast(this.get_modStates().get(var1.getModId()), ModState.AVAILABLE);
	}
	
	public void distributeStateMessage(Class var1) {
		try {
			this.get_masterChannel().post(var1.newInstance());
		} catch (Exception var3) {
			FMLLog.log(Level.SEVERE, var3, "An unexpected exception", new Object[0]);
			throw new LoaderException(var3);
		}
	}
	
	public BiMap getModObjectList() {
		if (this.get_modObjectList() == null) {
			FMLLog.severe("Detected an attempt by a mod %s to perform game activity during mod construction. This is a serious programming error.", new Object[]{this.get_activeContainer()});
			return this.buildModObjectList();
		} else {
			return ImmutableBiMap.copyOf(this.get_modObjectList());
		}
	}
	
	public boolean isInState(LoaderState var1) {
		return this.get_state() == var1;
	}
	
	boolean hasReachedState(LoaderState var1) {
		return this.get_state().ordinal() >= var1.ordinal() && this.get_state() != LoaderState.ERRORED;
	}
	
	public Loader get_loader() {
		return this.loader;
	}
	
	public void set_loader(Loader var1) {
		this.loader = var1;
	}
	
	public EventBus get_masterChannel() {
		return this.masterChannel;
	}
	
	public void set_masterChannel(EventBus var1) {
		this.masterChannel = var1;
	}
	
	public ImmutableMap get_eventChannels() {
		return this.eventChannels;
	}
	
	public void set_eventChannels(ImmutableMap var1) {
		this.eventChannels = var1;
	}
	
	public LoaderState get_state() {
		return this.state;
	}
	
	public void set_state(LoaderState var1) {
		this.state = var1;
	}
	
	public Multimap get_modStates() {
		return this.modStates;
	}
	
	public void set_modStates(Multimap var1) {
		this.modStates = var1;
	}
	
	public Multimap get_errors() {
		return this.errors;
	}
	
	public void set_errors(Multimap var1) {
		this.errors = var1;
	}
	
	public Map get_modList() {
		return this.modList;
	}
	
	public void set_modList(Map var1) {
		this.modList = var1;
	}
	
	public List get_activeModList() {
		return this.activeModList;
	}
	
	public void set_activeModList(List var1) {
		this.activeModList = var1;
	}
	
	public ModContainer get_activeContainer() {
		return this.activeContainer;
	}
	
	public void set_activeContainer(ModContainer var1) {
		this.activeContainer = var1;
	}
	
	public BiMap get_modObjectList() {
		return this.modObjectList;
	}
	
	public void set_modObjectList(BiMap var1) {
		this.modObjectList = var1;
	}
}
