//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

import agency.highlysuspect.redmill.game.logging.LegacyLogger;
import agency.highlysuspect.redmill.game.support.ObjectsSupport;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.registry.TickRegistry;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.Side;
import agency.highlysuspect.redmill.oldschool.net.minecraftforge.common.MinecraftForge;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.loading.FMLEnvironment;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

public class FMLCommonHandler {
	private static final FMLCommonHandler INSTANCE = new FMLCommonHandler();
	private IFMLSidedHandler sidedDelegate;
	private List scheduledClientTicks = Lists.newArrayList();
	private List scheduledServerTicks = Lists.newArrayList();
	private Class<MinecraftForge> forge;
	private boolean noForge;
	private List brandings;
	//private List crashCallables = Lists.newArrayList(new ICrashCallable[]{Loader.instance().getCallableCrashInformation()});
	private Set handlerSet = Sets.newSetFromMap((new MapMaker()).weakKeys().makeMap());
	
	public FMLCommonHandler() {
	}
	
	public void beginLoading(IFMLSidedHandler var1) {
		this.set_sidedDelegate(var1);
		FMLLog.log("MinecraftForge", Level.INFO, "Attempting early MinecraftForge initialization", new Object[0]);
		this.callForgeMethod("initialize");
		//this.callForgeMethod("registerCrashCallable");
		FMLLog.log("MinecraftForge", Level.INFO, "Completed early MinecraftForge initialization", new Object[0]);
	}
	
	public void rescheduleTicks(Side var1) {
		TickRegistry.updateTickQueue(var1.isClient() ? this.get_scheduledClientTicks() : this.get_scheduledServerTicks(), var1);
	}
	
	public void tickStart(EnumSet<TickType> var1, Side var2, Object... var3) {
		List var4 = var2.isClient() ? this.get_scheduledClientTicks() : this.get_scheduledServerTicks();
		if (var4.size() != 0) {
			Iterator var5 = var4.iterator();
			
			while(var5.hasNext()) {
				IScheduledTickHandler var6 = (IScheduledTickHandler)var5.next();
				EnumSet var7 = EnumSet.copyOf((EnumSet) ObjectsSupport.firstNonNull(var6.ticks(), EnumSet.noneOf(TickType.class)));
				var7.removeAll(EnumSet.complementOf(var1));
				if (!var7.isEmpty()) {
					var6.tickStart(var7, var3);
				}
			}
			
		}
	}
	
	public void tickEnd(EnumSet<TickType> var1, Side var2, Object... var3) {
		List var4 = var2.isClient() ? this.get_scheduledClientTicks() : this.get_scheduledServerTicks();
		if (var4.size() != 0) {
			Iterator var5 = var4.iterator();
			
			while(var5.hasNext()) {
				IScheduledTickHandler var6 = (IScheduledTickHandler)var5.next();
				EnumSet var7 = EnumSet.copyOf((EnumSet)ObjectsSupport.firstNonNull(var6.ticks(), EnumSet.noneOf(TickType.class)));
				var7.removeAll(EnumSet.complementOf(var1));
				if (!var7.isEmpty()) {
					var6.tickEnd(var7, var3);
				}
			}
			
		}
	}
	
	public static FMLCommonHandler instance() {
		return INSTANCE;
	}
	
//	public ModContainer findContainerFor(Object var1) {
//		return (ModContainer)Loader.instance().getReversedModObjectList().get(var1);
//	}
	
	public LegacyLogger getFMLLogger() {
		return FMLLog.getLogger();
	}
	
	public Side getSide() {
		//RM-TODO: call thru sidedDelegate
		//return this.get_sidedDelegate().getSide();
		return Side.redmill$fromModern(FMLEnvironment.dist);
	}
	
//	public Side getEffectiveSide() {
//		Thread var1 = Thread.currentThread();
//		return !(var1 instanceof IThreadMinecraftServer) && !(var1 instanceof IServerListenThread) ? Side.CLIENT : Side.SERVER;
//	}
	
	public void raiseException(Throwable var1, String var2, boolean var3) {
		FMLLog.log(Level.SEVERE, var1, "Something raised an exception. The message was '%s'. 'stopGame' is %b", new Object[]{var2, var3});
		if (var3) {
			this.getSidedDelegate().haltGame(var2, var1);
		}
		
	}
	
	private Class findMinecraftForge() {
		if (this.get_forge() == null && !this.get_noForge()) {
			try {
				//this.set_forge(Class.forName("net.minecraftforge.common.MinecraftForge"));
				this.set_forge(Class.forName("agency.highlysuspect.redmill.oldschool.net.minecraftforge.common.MinecraftForge"));
			} catch (Exception var2) {
				this.set_noForge(true);
			}
		}
		
		return this.get_forge();
	}
	
	private Object callForgeMethod(String var1) {
		if (this.get_noForge()) {
			return null;
		} else {
			try {
				return this.findMinecraftForge().getMethod(var1).invoke((Object)null);
			} catch (Exception var3) {
				return null;
			}
		}
	}
	
	public void computeBranding() {
		if (this.get_brandings() == null) {
			ImmutableList.Builder var1 = ImmutableList.builder();
			var1.add(Loader.instance().getMCVersionString());
			var1.add(Loader.instance().getMCPVersionString());
			var1.add("FML v" + Loader.instance().getFMLVersionString());
			String var2 = (String)this.callForgeMethod("getBrandingVersion");
			if (!Strings.isNullOrEmpty(var2)) {
				var1.add(var2);
			}
			
			if (this.get_sidedDelegate() != null) {
				var1.addAll(this.get_sidedDelegate().getAdditionalBrandingInformation());
			}
			
			try {
				Properties var3 = new Properties();
				var3.load(this.getClass().getClassLoader().getResourceAsStream("fmlbranding.properties"));
				var1.add(var3.getProperty("fmlbranding"));
			} catch (Exception var5) {
			}
			
			int var6 = Loader.instance().getModList().size();
			int var4 = Loader.instance().getActiveModList().size();
			var1.add(String.format("%d mod%s loaded, %d mod%s active", var6, var6 != 1 ? "s" : "", var4, var4 != 1 ? "s" : ""));
			this.set_brandings(var1.build());
		}
		
	}
	
	public List getBrandings() {
		if (this.get_brandings() == null) {
			this.computeBranding();
		}
		
		return ImmutableList.copyOf(this.get_brandings());
	}
	
	public IFMLSidedHandler getSidedDelegate() {
		return this.get_sidedDelegate();
	}
	
	public void onPostServerTick() {
		this.tickEnd(EnumSet.of(TickType.SERVER), Side.SERVER);
	}
	
	public void onPostWorldTick(Object var1) {
		this.tickEnd(EnumSet.of(TickType.WORLD), Side.SERVER, var1);
	}
	
	public void onPreServerTick() {
		this.tickStart(EnumSet.of(TickType.SERVER), Side.SERVER);
	}
	
	public void onPreWorldTick(Object var1) {
		this.tickStart(EnumSet.of(TickType.WORLD), Side.SERVER, var1);
	}
	
//	public void onWorldLoadTick(IWorld[] var1) {
//		this.rescheduleTicks(Side.SERVER);
//		IWorld[] var2 = var1;
//		int var3 = var1.length;
//
//		for(int var4 = 0; var4 < var3; ++var4) {
//			IWorld var5 = var2[var4];
//			this.tickStart(EnumSet.of(TickType.WORLDLOAD), Side.SERVER, var5);
//		}
//
//	}

//	public boolean handleServerAboutToStart(IMinecraftServer var1) {
//		return Loader.instance().serverAboutToStart(var1);
//	}
//
//	public boolean handleServerStarting(IMinecraftServer var1) {
//		return Loader.instance().serverStarting(var1);
//	}
	
	public void handleServerStarted() {
		Loader.instance().serverStarted();
	}
	
	public void handleServerStopping() {
		Loader.instance().serverStopping();
	}
	
//	public IMinecraftServer getMinecraftServerInstance() {
//		return this.get_sidedDelegate().getServer();
//	}
	
	public void showGuiScreen(Object var1) {
		this.get_sidedDelegate().showGuiScreen(var1);
	}
	
//	public IEntity spawnEntityIntoClientWorld(EntityRegistry.EntityRegistration var1, EntitySpawnPacket var2) {
//		return this.get_sidedDelegate().spawnEntityIntoClientWorld(var1, var2);
//	}
//
//	public void adjustEntityLocationOnClient(EntitySpawnAdjustmentPacket var1) {
//		this.get_sidedDelegate().adjustEntityLocationOnClient(var1);
//	}
//
//	public void onServerStart(IDedicatedServer var1) {
//		FMLServerHandler.instance();
//		this.get_sidedDelegate().beginServerLoading(var1);
//	}
	
	public void onServerStarted() {
		this.get_sidedDelegate().finishServerLoading();
	}
	
	public void onPreClientTick() {
		this.tickStart(EnumSet.of(TickType.CLIENT), Side.CLIENT);
	}
	
	public void onPostClientTick() {
		this.tickEnd(EnumSet.of(TickType.CLIENT), Side.CLIENT);
	}
	
	public void onRenderTickStart(float var1) {
		this.tickStart(EnumSet.of(TickType.RENDER), Side.CLIENT, var1);
	}
	
	public void onRenderTickEnd(float var1) {
		this.tickEnd(EnumSet.of(TickType.RENDER), Side.CLIENT, var1);
	}
	
//	public void onPlayerPreTick(IEntityPlayer var1) {
//		Side var2 = var1 instanceof IEntityPlayerMP ? Side.SERVER : Side.CLIENT;
//		this.tickStart(EnumSet.of(TickType.PLAYER), var2, var1);
//	}
//
//	public void onPlayerPostTick(IEntityPlayer var1) {
//		Side var2 = var1 instanceof IEntityPlayerMP ? Side.SERVER : Side.CLIENT;
//		this.tickEnd(EnumSet.of(TickType.PLAYER), var2, var1);
//	}

//	public void registerCrashCallable(ICrashCallable var1) {
//		this.get_crashCallables().add(var1);
//	}
//
//	public void enhanceCrashReport(ICrashReport var1, ICrashReportCategory var2) {
//		Iterator var3 = this.get_crashCallables().iterator();
//
//		while(var3.hasNext()) {
//			ICrashCallable var4 = (ICrashCallable)var3.next();
//			var2.addCrashSectionCallable(var4.getLabel(), var4);
//		}
//
//	}

//	public void handleTinyPacket(INetHandler var1, IPacket131MapData var2) {
//		this.get_sidedDelegate().handleTinyPacket(var1, var2);
//	}

//	public void handleWorldDataSave(ISaveHandler var1, IWorldInfo var2, INBTTagCompound var3) {
//		Iterator var4 = Loader.instance().getModList().iterator();
//
//		while(var4.hasNext()) {
//			ModContainer var5 = (ModContainer)var4.next();
//			if (var5 instanceof InjectedModContainer) {
//				WorldAccessContainer var6 = ((InjectedModContainer)var5).getWrappedWorldAccessContainer();
//				if (var6 != null) {
//					INBTTagCompound var7 = var6.getDataForWriting(var1, var2);
//					var3.setCompoundTag(var5.getModId(), var7);
//				}
//			}
//		}
//
//	}

//	public void handleWorldDataLoad(ISaveHandler var1, IWorldInfo var2, INBTTagCompound var3) {
//		if (this.getEffectiveSide() == Side.SERVER) {
//			if (!this.get_handlerSet().contains(var1)) {
//				this.get_handlerSet().add(var1);
//				HashMap var4 = Maps.newHashMap();
//				var2.setAdditionalProperties(var4);
//				Iterator var5 = Loader.instance().getModList().iterator();
//
//				while(var5.hasNext()) {
//					ModContainer var6 = (ModContainer)var5.next();
//					if (var6 instanceof InjectedModContainer) {
//						WorldAccessContainer var7 = ((InjectedModContainer)var6).getWrappedWorldAccessContainer();
//						if (var7 != null) {
//							var7.readData(var1, var2, var4, var3.getCompoundTag(var6.getModId()));
//						}
//					}
//				}
//
//			}
//		}
//	}
	
	public boolean shouldServerBeKilledQuietly() {
		return this.get_sidedDelegate() == null ? false : this.get_sidedDelegate().shouldServerShouldBeKilledQuietly();
	}
	
//	public void disconnectIDMismatch(MapDifference var1, INetHandler var2, IINetworkManager var3) {
//		this.get_sidedDelegate().disconnectIDMismatch(var1, var2, var3);
//	}
	
	public void handleServerStopped() {
		Loader.instance().serverStopped();
	}
	
	public static FMLCommonHandler get_INSTANCE() {
		return INSTANCE;
	}
	
	public IFMLSidedHandler get_sidedDelegate() {
		return this.sidedDelegate;
	}
	
	public void set_sidedDelegate(IFMLSidedHandler var1) {
		this.sidedDelegate = var1;
	}
	
	public List get_scheduledClientTicks() {
		return this.scheduledClientTicks;
	}
	
	public void set_scheduledClientTicks(List var1) {
		this.scheduledClientTicks = var1;
	}
	
	public List get_scheduledServerTicks() {
		return this.scheduledServerTicks;
	}
	
	public void set_scheduledServerTicks(List var1) {
		this.scheduledServerTicks = var1;
	}
	
	public Class get_forge() {
		return this.forge;
	}
	
	public void set_forge(Class var1) {
		this.forge = var1;
	}
	
	public boolean get_noForge() {
		return this.noForge;
	}
	
	public void set_noForge(boolean var1) {
		this.noForge = var1;
	}
	
	public List get_brandings() {
		return this.brandings;
	}
	
	public void set_brandings(List var1) {
		this.brandings = var1;
	}
	
//	public List get_crashCallables() {
//		return this.crashCallables;
//	}
//
//	public void set_crashCallables(List var1) {
//		this.crashCallables = var1;
//	}
	
	public Set get_handlerSet() {
		return this.handlerSet;
	}
	
	public void set_handlerSet(Set var1) {
		this.handlerSet = var1;
	}
}
