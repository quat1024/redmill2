//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.client;

import agency.highlysuspect.redmill.game.logging.LegacyLogger;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.client.registry.KeyBindingRegistry;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.*;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.registry.LanguageRegistry;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.relauncher.Side;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.IMinecraft;
import agency.highlysuspect.redmill.oldschool.net.minecraft.crash.RCrashReport;
import agency.highlysuspect.redmill.svc.Globals;
import com.google.common.base.Throwables;

import java.util.ArrayList;
import java.util.List;

public class FMLClientHandler implements IFMLSidedHandler {
	private static final FMLClientHandler INSTANCE = new FMLClientHandler();
	private IMinecraft client;
//	private DummyModContainer optifineContainer;
	private boolean guiLoaded;
	private boolean serverIsRunning;
//	private MissingModsException modsMissing;
	private boolean loading;
//	private WrongMinecraftVersionException wrongMC;
//	private CustomModLoadingErrorDisplayException customError;
//	private DuplicateModsFoundException dupesFound;
	private boolean serverShouldBeKilledQuietly;
	
	public FMLClientHandler() {
	}
	
	public void beginMinecraftLoading(IMinecraft var1) {
//		if (var1.isDemo()) {
//			FMLLog.severe("DEMO MODE DETECTED, FML will not work. Finishing now.", new Object[0]);
//			this.haltGame("FML will not run in demo mode", new RuntimeException());
//		} else {
			this.set_loading(true);
			this.set_client(var1);
//			ObfuscationReflectionHelper.detectObfuscation(RWorld.class); //RM-TODO
//			TextureFXManager.instance().setClient(this.get_client());
			FMLCommonHandler.instance().beginLoading(this);
			//new ModLoaderClientHelper(this.get_client());
			
//			try {
//				Class var2 = Class.forName("Config", false, Loader.instance().getModClassLoader());
//				String var3 = (String)var2.getField("VERSION").get((Object)null);
//				ImmutableMap var4 = ImmutableMap.builder().put("name", "Optifine").put("version", var3).build();
//				ModMetadata var5 = MetadataCollection.from(this.getClass().getResourceAsStream("optifinemod.info"), "optifine").getMetadataForId("optifine", var4);
//				this.set_optifineContainer(new DummyModContainer(var5));
//				FMLLog.info("Forge Mod Loader has detected optifine %s, enabling compatibility features", new Object[]{this.get_optifineContainer().getVersion()});
//			} catch (Exception var11) {
//				this.set_optifineContainer((DummyModContainer)null);
//			}
			
			//RM: Already loaded mods thru RedmillBefore / RedmillAfter
//			try {
//				Loader.instance().loadMods();
//			} catch (cpw.mods.fml.common.WrongMinecraftVersionException var6) {
//				this.set_wrongMC(var6);
//			} catch (cpw.mods.fml.common.DuplicateModsFoundException var7) {
//				this.set_dupesFound(var7);
//			} catch (cpw.mods.fml.common.MissingModsException var8) {
//				this.set_modsMissing(var8);
//			} catch (cpw.mods.fml.client.CustomModLoadingErrorDisplayException var9) {
//				FMLLog.log(Level.SEVERE, var9, "A custom exception was thrown by a mod, the game will now halt", new Object[0]);
//				this.set_customError(var9);
//			} catch (LoaderException var10) {
//				this.haltGame("There was a severe problem during mod loading that has caused the game to fail", var10);
//				return;
//			}
		
//		}
	}
	
	public void haltGame(String var1, Throwable var2) {
		//throw Globals.mkRethrow(var2, "haltGame: " + var1);
		this.get_client().displayCrashReport(new RCrashReport(var1, var2));
		throw Throwables.propagate(var2);
	}
	
	public void finishMinecraftLoading() {
		//if (this.get_modsMissing() == null && this.get_wrongMC() == null && this.get_customError() == null && this.get_dupesFound() == null) {
//			try {
				Loader.instance().initializeMods();
//			} catch (cpw.mods.fml.client.CustomModLoadingErrorDisplayException var2) {
//				FMLLog.log(Level.SEVERE, var2, "A custom exception was thrown by a mod, the game will now halt", new Object[0]);
//				this.set_customError(var2);
//				return;
//			} catch (LoaderException var3) {
//				this.haltGame("There was a severe problem during mod loading that has caused the game to fail", var3);
//				return;
//			}
			
			LanguageRegistry.reloadLanguageTable();
			//RenderingRegistry.instance().loadEntityRenderers(RRenderManager.get_instance().get_entityRenderMap()); //RM-TODO
			this.set_loading(false);
			KeyBindingRegistry.instance().uploadKeyBindingsToGame(this.get_client().get_gameSettings());
		//}
	}
	
	public void onInitializationComplete() {
//		if (this.get_wrongMC() != null) {
//			this.get_client().displayGuiScreen(new GuiWrongMinecraft(this.get_wrongMC()));
//		} else if (this.get_modsMissing() != null) {
//			this.get_client().displayGuiScreen(new GuiModsMissing(this.get_modsMissing()));
//		} else if (this.get_dupesFound() != null) {
//			this.get_client().displayGuiScreen(new GuiDupesFound(this.get_dupesFound()));
//		} else if (this.get_customError() != null) {
//			this.get_client().displayGuiScreen(new GuiCustomModLoadingErrorScreen(this.get_customError()));
//		} else {
		
		//RM-TODO
		
			//TextureFXManager.instance().loadTextures(this.get_client().get_texturePackList().getSelectedTexturePack());
//		}
		
	}
	
	public IMinecraft getClient() {
		return this.get_client();
	}
	
	public LegacyLogger getMinecraftLogger() {
		return null;
	}
	
	public static FMLClientHandler instance() {
		return INSTANCE;
	}
	
//	public void displayGuiScreen(IEntityPlayer var1, IGuiScreen var2) {
//		if (this.get_client().get_thePlayer() == var1 && var2 != null) {
//			this.get_client().displayGuiScreen(var2);
//		}
//
//	}
	
	public void addSpecialModEntries(ArrayList<ModContainer> var1) {
//		if (this.get_optifineContainer() != null) {
//			var1.add(this.get_optifineContainer());
//		}
		
	}
	
	public List<String> getAdditionalBrandingInformation() {
//		return (List)(this.get_optifineContainer() != null ? Arrays.asList(String.format("Optifine %s", this.get_optifineContainer().getVersion())) : ImmutableList.of());
		return List.of("Red Mill");
	}
	
	public Side getSide() {
		return Side.CLIENT;
	}
	
	public boolean hasOptifine() {
		//return this.get_optifineContainer() != null;
		return false;
	}
	
	public void showGuiScreen(Object var1) {
//		IGuiScreen var2 = (IGuiScreen)var1;
//		this.get_client().displayGuiScreen(var2);
	}
	
//	public IEntity spawnEntityIntoClientWorld(EntityRegistry.EntityRegistration var1, EntitySpawnPacket var2) {
//		IWorldClient var3 = this.get_client().get_theWorld();
//		Class var4 = var1.getEntityClass();
//
//		try {
//			IEntity var5;
//			if (var1.hasCustomSpawning()) {
//				var5 = var1.doCustomSpawning(var2);
//			} else {
//				var5 = (IEntity)var4.getConstructor(RWorld.class).newInstance(var3);
//				var5.set_entityId(var2.get_entityId());
//				var5.setLocationAndAngles(var2.get_scaledX(), var2.get_scaledY(), var2.get_scaledZ(), var2.get_scaledYaw(), var2.get_scaledPitch());
//				if (var5 instanceof IEntityLiving) {
//					((IEntityLiving)var5).set_rotationYawHead(var2.get_scaledHeadYaw());
//				}
//			}
//
//			var5.set_serverPosX(var2.get_rawX());
//			var5.set_serverPosY(var2.get_rawY());
//			var5.set_serverPosZ(var2.get_rawZ());
//			if (var5 instanceof IThrowableEntity) {
//				Object var6 = this.get_client().get_thePlayer().get_entityId() == var2.get_throwerId() ? this.get_client().get_thePlayer() : var3.getEntityByID(var2.get_throwerId());
//				((IThrowableEntity)var5).setThrower((IEntity)var6);
//			}
//
//			IEntity[] var10 = var5.getParts();
//			if (var10 != null) {
//				int var7 = var2.get_entityId() - var5.get_entityId();
//
//				for(int var8 = 0; var8 < var10.length; ++var8) {
//					var10[var8].set_entityId(var10[var8].get_entityId() + var7);
//				}
//			}
//
//			if (var2.get_metadata() != null) {
//				var5.getDataWatcher().updateWatchedObjectsFromList(var2.get_metadata());
//			}
//
//			if (var2.get_throwerId() > 0) {
//				var5.setVelocity(var2.get_speedScaledX(), var2.get_speedScaledY(), var2.get_speedScaledZ());
//			}
//
//			if (var5 instanceof IEntityAdditionalSpawnData) {
//				((IEntityAdditionalSpawnData)var5).readSpawnData(var2.get_dataStream());
//			}
//
//			var3.addEntityToWorld(var2.get_entityId(), var5);
//			return var5;
//		} catch (Exception var9) {
//			FMLLog.log(Level.SEVERE, var9, "A severe problem occurred during the spawning of an entity", new Object[0]);
//			throw Throwables.propagate(var9);
//		}
//	}
//
//	public void adjustEntityLocationOnClient(EntitySpawnAdjustmentPacket var1) {
//		IEntity var2 = this.get_client().get_theWorld().getEntityByID(var1.get_entityId());
//		if (var2 != null) {
//			var2.set_serverPosX(var1.get_serverX());
//			var2.set_serverPosY(var1.get_serverY());
//			var2.set_serverPosZ(var1.get_serverZ());
//		} else {
//			FMLLog.fine("Attempted to adjust the position of entity %d which is not present on the client", new Object[]{var1.get_entityId()});
//		}
//
//	}
//
//	public void beginServerLoading(IMinecraftServer var1) {
//		this.set_serverShouldBeKilledQuietly(false);
//	}
	
	public void finishServerLoading() {
	}
	
//	public IMinecraftServer getServer() {
//		return this.get_client().getIntegratedServer();
//	}
//
//	public void sendPacket(IPacket var1) {
//		if (this.get_client().get_thePlayer() != null) {
//			this.get_client().get_thePlayer().get_sendQueue().addToSendQueue(var1);
//		}
//
//	}
	
//	public void displayMissingMods(ModMissingPacket var1) {
//		this.get_client().displayGuiScreen(new GuiModsMissingForServer(var1));
//	}
	
	public boolean isLoading() {
		return this.get_loading();
	}
	
//	public void handleTinyPacket(INetHandler var1, IPacket131MapData var2) {
//		((INetClientHandler)var1).fmlPacket131Callback(var2);
//	}
//
	public void setClientCompatibilityLevel(byte var1) {
		//RNetClientHandler.setConnectionCompatibilityLevel(var1);
	}

	public byte getClientCompatibilityLevel() {
		throw Globals.nyi("getClientCompatibilityLevel");
		//return RNetClientHandler.getConnectionCompatibilityLevel();
	}
//
//	public void warnIDMismatch(MapDifference var1, boolean var2) {
//		GuiIdMismatchScreen var3 = new GuiIdMismatchScreen(var1, var2);
//		this.get_client().displayGuiScreen(var3);
//	}
//
//	public void callbackIdDifferenceResponse(boolean var1) {
//		if (var1) {
//			this.set_serverShouldBeKilledQuietly(false);
//			GameData.releaseGate(true);
//			this.get_client().continueWorldLoading();
//		} else {
//			this.set_serverShouldBeKilledQuietly(true);
//			GameData.releaseGate(false);
//			this.get_client().loadWorld((IWorldClient)null);
//			this.get_client().displayGuiScreen((IGuiScreen)null);
//		}
//
//	}
	
	public boolean shouldServerShouldBeKilledQuietly() {
		return this.get_serverShouldBeKilledQuietly();
	}
	
//	public void disconnectIDMismatch(MapDifference var1, INetHandler var2, IINetworkManager var3) {
//		boolean var4 = !var1.entriesOnlyOnLeft().isEmpty();
//		Iterator var5 = var1.entriesDiffering().entrySet().iterator();
//
//		while(var5.hasNext()) {
//			Map.Entry var6 = (Map.Entry)var5.next();
//			MapDifference.ValueDifference var7 = (MapDifference.ValueDifference)var6.getValue();
//			if (!((ItemData)var7.leftValue()).mayDifferByOrdinal((ItemData)var7.rightValue())) {
//				var4 = true;
//			}
//		}
//
//		if (var4) {
//			((INetClientHandler)var2).disconnect();
//			RGuiConnecting.forceTermination((IGuiConnecting)this.get_client().get_currentScreen());
//			var3.processReadPackets();
//			this.get_client().loadWorld((IWorldClient)null);
//			this.warnIDMismatch(var1, false);
//		}
//	}
	
	public static FMLClientHandler get_INSTANCE() {
		return INSTANCE;
	}
	
	public IMinecraft get_client() {
		return this.client;
	}
	
	public void set_client(IMinecraft var1) {
		this.client = var1;
	}
	
//	public DummyModContainer get_optifineContainer() {
//		return this.optifineContainer;
//	}
//
//	public void set_optifineContainer(DummyModContainer var1) {
//		this.optifineContainer = var1;
//	}
	
	public boolean get_guiLoaded() {
		return this.guiLoaded;
	}
	
	public void set_guiLoaded(boolean var1) {
		this.guiLoaded = var1;
	}
	
	public boolean get_serverIsRunning() {
		return this.serverIsRunning;
	}
	
	public void set_serverIsRunning(boolean var1) {
		this.serverIsRunning = var1;
	}
	
//	public MissingModsException get_modsMissing() {
//		return this.modsMissing;
//	}
//
//	public void set_modsMissing(MissingModsException var1) {
//		this.modsMissing = var1;
//	}
	
	public boolean get_loading() {
		return this.loading;
	}
	
	public void set_loading(boolean var1) {
		this.loading = var1;
	}
	
//	public WrongMinecraftVersionException get_wrongMC() {
//		return this.wrongMC;
//	}
//
//	public void set_wrongMC(WrongMinecraftVersionException var1) {
//		this.wrongMC = var1;
//	}
//
//	public CustomModLoadingErrorDisplayException get_customError() {
//		return this.customError;
//	}
//
//	public void set_customError(CustomModLoadingErrorDisplayException var1) {
//		this.customError = var1;
//	}
//
//	public DuplicateModsFoundException get_dupesFound() {
//		return this.dupesFound;
//	}
//
//	public void set_dupesFound(DuplicateModsFoundException var1) {
//		this.dupesFound = var1;
//	}
	
	public boolean get_serverShouldBeKilledQuietly() {
		return this.serverShouldBeKilledQuietly;
	}
	
	public void set_serverShouldBeKilledQuietly(boolean var1) {
		this.serverShouldBeKilledQuietly = var1;
	}
}
