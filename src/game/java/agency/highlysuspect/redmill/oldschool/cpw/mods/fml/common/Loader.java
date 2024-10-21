//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

import agency.highlysuspect.redmill.game.OldschoolModContainer;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.LoaderState.ModState;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.FMLLoadEvent;
import agency.highlysuspect.redmill.svc.ModContainerExt;
import com.google.common.base.Splitter;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class Loader {
	private static final Splitter DEPENDENCYPARTSPLITTER = Splitter.on(":").omitEmptyStrings().trimResults();
	private static final Splitter DEPENDENCYSPLITTER = Splitter.on(";").omitEmptyStrings().trimResults();
	private static Loader instance;
	private static String major;
	private static String minor;
	private static String rev;
	private static String build;
	private static String mccversion;
	private static String mcpversion;
	//private ModClassLoader modClassLoader = new ModClassLoader(this.getClass().getClassLoader());
	private List<ModContainer> mods = new ArrayList<>();
	private Map<String, ModContainer> namedMods = new HashMap<>();
	private File canonicalConfigDir;
	private File canonicalMinecraftDir;
	private Exception capturedError;
	private File canonicalModsDir;
	private LoadController modController;
//	private MinecraftDummyContainer minecraft;
//	private MCPDummyContainer mcp;
	private static File minecraftDir;
	private static List injectedContainers;
	private File loggingProperties;
	
	public final Map<String, OldschoolModContainer> omcsByModernModid = new HashMap<>();
	
	public void redmill$injectContainer(ModContainerExt ext)  {
		OldschoolModContainer omc = new OldschoolModContainer(ext);
		mods.add(omc);
		namedMods.put(omc.getModId(), omc);
		omcsByModernModid.put(ext.modernModid, omc);
	}
	
	public static Loader instance() {
		if (instance == null) {
			instance = new Loader();
		}
		
		return instance;
	}
	
	public static void injectData(Object... var0) {
		major = (String)var0[0];
		minor = (String)var0[1];
		rev = (String)var0[2];
		build = (String)var0[3];
		mccversion = (String)var0[4];
		mcpversion = (String)var0[5];
		minecraftDir = (File)var0[6];
		injectedContainers = (List)var0[7];
	}
	
	private Loader() {
//		String var1 = (new RCallableMinecraftVersion((ICrashReport)null)).minecraftVersion();
//		if (!mccversion.equals(var1)) {
//			FMLLog.severe("This version of FML is built for Minecraft %s, we have detected Minecraft %s in your minecraft jar file", new Object[]{mccversion, var1});
//			throw new LoaderException();
//		} else {
//			this.minecraft = new MinecraftDummyContainer(var1);
//			this.mcp = new MCPDummyContainer(MetadataCollection.from(this.getClass().getResourceAsStream("/mcpmod.info"), "MCP").getMetadataForId("mcp", (Map)null));
//		}
	}
	
	private void sortModList() {
//		FMLLog.finer("Verifying mod requirements are satisfied", new Object[0]);
//		boolean var16 = false;
//
//		Iterator var2;
//		ModContainer var3;
//		try {
//			var16 = true;
//			HashBiMap var1 = HashBiMap.create();
//			var2 = this.getActiveModList().iterator();
//
//			while(var2.hasNext()) {
//				var3 = (ModContainer)var2.next();
//				var1.put(var3.getModId(), var3.getProcessedVersion());
//			}
//
//			var2 = this.getActiveModList().iterator();
//
//			while(var2.hasNext()) {
//				var3 = (ModContainer)var2.next();
//				if (!var3.acceptableMinecraftVersionRange().containsVersion(this.get_minecraft().getProcessedVersion())) {
//					FMLLog.severe("The mod %s does not wish to run in Minecraft version %s. You will have to remove it to play.", new Object[]{var3.getModId(), this.getMCVersionString()});
//					throw new WrongMinecraftVersionException(var3);
//				}
//
//				ImmutableMap var4 = Maps.uniqueIndex(var3.getRequirements(), new Loader$1(this));
//				HashSet var5 = Sets.newHashSet();
//				Sets.SetView var6 = Sets.difference(var4.keySet(), var1.keySet());
//				if (!var6.isEmpty()) {
//					FMLLog.severe("The mod %s (%s) requires mods %s to be available", new Object[]{var3.getModId(), var3.getName(), var6});
//					Iterator var22 = var6.iterator();
//
//					while(var22.hasNext()) {
//						String var23 = (String)var22.next();
//						var5.add(var4.get(var23));
//					}
//
//					throw new MissingModsException(var5);
//				}
//
//				ImmutableList var7 = ImmutableList.builder().addAll(var3.getDependants()).addAll(var3.getDependencies()).build();
//				Iterator var8 = var7.iterator();
//
//				while(var8.hasNext()) {
//					ArtifactVersion var9 = (ArtifactVersion)var8.next();
//					if (var1.containsKey(var9.getLabel()) && !var9.containsVersion((ArtifactVersion)var1.get(var9.getLabel()))) {
//						var5.add(var9);
//					}
//				}
//
//				if (!var5.isEmpty()) {
//					FMLLog.severe("The mod %s (%s) requires mod versions %s to be available", new Object[]{var3.getModId(), var3.getName(), var5});
//					throw new MissingModsException(var5);
//				}
//			}
//
//			FMLLog.finer("All mod requirements are satisfied", new Object[0]);
//			ModSorter var20 = new ModSorter(this.getActiveModList(), this.get_namedMods());
//
//			try {
//				FMLLog.finer("Sorting mods into an ordered list", new Object[0]);
//				List var21 = var20.sort();
//				this.get_modController().getActiveModList().clear();
//				this.get_modController().getActiveModList().addAll(var21);
//				this.get_mods().removeAll(var21);
//				var21.addAll(this.get_mods());
//				this.set_mods(var21);
//				FMLLog.finer("Mod sorting completed successfully", new Object[0]);
//				var16 = false;
//			} catch (ModSortingException var17) {
//				FMLLog.severe("A dependency cycle was detected in the input mod set so an ordering cannot be determined", new Object[0]);
//				FMLLog.severe("The visited mod list is %s", new Object[]{var17.getExceptionData().getVisitedNodes()});
//				FMLLog.severe("The first mod in the cycle is %s", new Object[]{var17.getExceptionData().getFirstBadNode()});
//				FMLLog.log(Level.SEVERE, var17, "The full error", new Object[0]);
//				throw new LoaderException(var17);
//			}
//		} finally {
//			if (var16) {
//				FMLLog.fine("Mod sorting data", new Object[0]);
//				int var11 = this.get_mods().size();
//				Iterator var12 = this.getActiveModList().iterator();
//
//				while(var12.hasNext()) {
//					ModContainer var13 = (ModContainer)var12.next();
//					if (!var13.isImmutable()) {
//						FMLLog.fine("\t%s(%s:%s): %s (%s)", new Object[]{var13.getModId(), var13.getName(), var13.getVersion(), var13.getSource().getName(), var13.getSortingRules()});
//						--var11;
//					}
//				}
//
//				if (var11 == this.get_mods().size()) {
//					FMLLog.fine("No user mods found to sort", new Object[0]);
//				}
//
//			}
//		}
//
//		FMLLog.fine("Mod sorting data", new Object[0]);
//		int var19 = this.get_mods().size();
//		var2 = this.getActiveModList().iterator();
//
//		while(var2.hasNext()) {
//			var3 = (ModContainer)var2.next();
//			if (!var3.isImmutable()) {
//				FMLLog.fine("\t%s(%s:%s): %s (%s)", new Object[]{var3.getModId(), var3.getName(), var3.getVersion(), var3.getSource().getName(), var3.getSortingRules()});
//				--var19;
//			}
//		}
//
//		if (var19 == this.get_mods().size()) {
//			FMLLog.fine("No user mods found to sort", new Object[0]);
//		}
//
	}
	
//	private ModDiscoverer identifyMods() {
//		FMLLog.fine("Building injected Mod Containers %s", new Object[]{get_injectedContainers()});
//		this.get_mods().add(new InjectedModContainer(this.get_mcp(), new File("minecraft.jar")));
//		File var1 = new File(get_minecraftDir(), "coremods");
//
//		ModContainer var4;
//		for(Iterator var2 = get_injectedContainers().iterator(); var2.hasNext(); this.get_mods().add(new InjectedModContainer(var4, var1))) {
//			String var3 = (String)var2.next();
//
//			try {
//				var4 = (ModContainer)Class.forName(var3, true, this.get_modClassLoader()).newInstance();
//			} catch (Exception var6) {
//				FMLLog.log(Level.SEVERE, var6, "A problem occured instantiating the injected mod container %s", new Object[]{var3});
//				throw new LoaderException(var6);
//			}
//		}
//
//		ModDiscoverer var7 = new ModDiscoverer();
//		FMLLog.fine("Attempting to load mods contained in the minecraft jar file and associated classes", new Object[0]);
//		var7.findClasspathMods(this.get_modClassLoader());
//		FMLLog.fine("Minecraft jar mods loaded successfully", new Object[0]);
//		FMLLog.info("Searching %s for mods", new Object[]{this.get_canonicalModsDir().getAbsolutePath()});
//		var7.findModDirMods(this.get_canonicalModsDir());
//		this.get_mods().addAll(var7.identifyMods());
//		this.identifyDuplicates(this.get_mods());
//		this.set_namedMods(Maps.uniqueIndex(this.get_mods(), new ModIdFunction()));
//		FMLLog.info("Forge Mod Loader has identified %d mod%s to load", new Object[]{this.get_mods().size(), this.get_mods().size() != 1 ? "s" : ""});
//		Iterator var8 = this.get_namedMods().keySet().iterator();
//
//		while(var8.hasNext()) {
//			String var9 = (String)var8.next();
//			FMLLog.makeLog(var9);
//		}
//
//		return var7;
//	}
	
	private void identifyDuplicates(List var1) {
//		TreeMultimap var2 = TreeMultimap.create(new Loader$ModIdComparator(this, (Loader$1)null), Ordering.arbitrary());
//		Iterator var3 = var1.iterator();
//
//		while(var3.hasNext()) {
//			ModContainer var4 = (ModContainer)var3.next();
//			if (var4.getSource() != null) {
//				var2.put(var4, var4.getSource());
//			}
//		}
//
//		ImmutableMultiset var7 = Multisets.copyHighestCountFirst(var2.keys());
//		LinkedHashMultimap var8 = LinkedHashMultimap.create();
//		Iterator var5 = var7.entrySet().iterator();
//
//		while(var5.hasNext()) {
//			Multiset.Entry var6 = (Multiset.Entry)var5.next();
//			if (var6.getCount() > 1) {
//				FMLLog.severe("Found a duplicate mod %s at %s", new Object[]{((ModContainer)var6.getElement()).getModId(), var2.get(var6.getElement())});
//				var8.putAll(var6.getElement(), var2.get(var6.getElement()));
//			}
//		}
//
//		if (!var8.isEmpty()) {
//			throw new DuplicateModsFoundException(var8);
//		}
	}
	
	private void initializeLoader() {
		File modsDir = new File(get_minecraftDir(), "mods");
		File configDir = new File(get_minecraftDir(), "config");
		
		String var3;
		String var4;
		try {
			this.set_canonicalMinecraftDir(get_minecraftDir().getCanonicalFile());
			var3 = modsDir.getCanonicalPath();
			var4 = configDir.getCanonicalPath();
			this.set_canonicalConfigDir(configDir.getCanonicalFile());
			this.set_canonicalModsDir(modsDir.getCanonicalFile());
		} catch (IOException var6) {
			FMLLog.log(Level.SEVERE, var6, "Failed to resolve loader directories: mods : %s ; config %s", new Object[]{this.get_canonicalModsDir().getAbsolutePath(), configDir.getAbsolutePath()});
			throw new LoaderException(var6);
		}
		
//		boolean var5;
//		if (!this.get_canonicalModsDir().exists()) {
//			FMLLog.info("No mod directory found, creating one: %s", new Object[]{var3});
//			var5 = this.get_canonicalModsDir().mkdir();
//			if (!var5) {
//				FMLLog.severe("Unable to create the mod directory %s", new Object[]{var3});
//				throw new LoaderException();
//			}
//
//			FMLLog.info("Mod directory created successfully", new Object[0]);
//		}
		
//		if (!this.get_canonicalConfigDir().exists()) {
//			FMLLog.fine("No config directory found, creating one: %s", new Object[]{var4});
//			var5 = this.get_canonicalConfigDir().mkdir();
//			if (!var5) {
//				FMLLog.severe("Unable to create the config directory %s", new Object[]{var4});
//				throw new LoaderException();
//			}
//
//			FMLLog.info("Config directory created successfully", new Object[0]);
//		}
//
//		if (!this.get_canonicalModsDir().isDirectory()) {
//			FMLLog.severe("Attempting to load mods from %s, which is not a directory", new Object[]{var3});
//			throw new LoaderException();
//		} else if (!configDir.isDirectory()) {
//			FMLLog.severe("Attempting to load configuration from %s, which is not a directory", new Object[]{var4});
//			throw new LoaderException();
//		} else {
//			this.set_loggingProperties(new File(this.get_canonicalConfigDir(), "logging.properties"));
//			FMLLog.info("Reading custom logging properties from %s", new Object[]{this.get_loggingProperties().getPath()});
//			FMLRelaunchLog.loadLogConfiguration(this.get_loggingProperties());
//			FMLLog.log(Level.OFF, "Logging level for ForgeModLoader logging is set to %s", new Object[]{FMLRelaunchLog.get_log().getLogger().getLevel()});
//		}
	}
	
	public List getModList() {
		return instance().get_mods() != null ? ImmutableList.copyOf(instance().get_mods()) : ImmutableList.of();
	}
	
	public void loadMods() {
		this.initializeLoader();
		//this.set_mods(Lists.newArrayList());
		//this.set_namedMods(Maps.newHashMap());
		this.set_modController(new LoadController(this));
		this.get_modController().transition(LoaderState.LOADING);
		//ModDiscoverer var1 = this.identifyMods();
		//this.disableRequestedMods();
		//FMLLog.fine("Reloading logging properties from %s", new Object[]{this.get_loggingProperties().getPath()});
		//FMLRelaunchLog.loadLogConfiguration(this.get_loggingProperties());
		//FMLLog.fine("Reloaded logging properties", new Object[0]);
		this.get_modController().distributeStateMessage(FMLLoadEvent.class);
		//this.sortModList();
		//this.set_mods(ImmutableList.copyOf(this.get_mods()));
//		Iterator var2 = var1.getNonModLibs().iterator();
//
//		while(var2.hasNext()) {
//			File var3 = (File)var2.next();
//			if (var3.isFile()) {
//				FMLLog.info("FML has found a non-mod file %s in your mods directory. It will now be injected into your classpath. This could severe stability issues, it should be removed if possible.", new Object[]{var3.getName()});
//
//				try {
//					this.get_modClassLoader().addFile(var3);
//				} catch (MalformedURLException var5) {
//					FMLLog.log(Level.SEVERE, var5, "Encountered a weird problem with non-mod file injection : %s", new Object[]{var3.getName()});
//				}
//			}
//		}
//
		this.get_modController().transition(LoaderState.CONSTRUCTING);
	}
	
	public void redmill$loadMods_afterConstruction() {
		this.get_modController().distributeStateMessage(LoaderState.CONSTRUCTING,
			null, //this.get_modClassLoader(),
			null //var1.getASMTable()
		);
//		FMLLog.fine("Mod signature data", new Object[0]);
//		var2 = this.getActiveModList().iterator();
//
//		while(var2.hasNext()) {
//			ModContainer var6 = (ModContainer)var2.next();
//			FMLLog.fine("\t%s(%s:%s): %s (%s)", new Object[]{var6.getModId(), var6.getName(), var6.getVersion(), var6.getSource().getName(), CertificateHelper.getFingerprint(var6.getSigningCertificate())});
//		}
//
//		if (this.getActiveModList().isEmpty()) {
//			FMLLog.fine("No user mod signature data found", new Object[0]);
//		}
//
		this.get_modController().transition(LoaderState.PREINITIALIZATION);
		this.get_modController().distributeStateMessage(LoaderState.PREINITIALIZATION,
			null, //var1.getASMTable(),
			this.get_canonicalConfigDir()
		);
		this.get_modController().transition(LoaderState.INITIALIZATION);
	}
	
	private void disableRequestedMods() {
//		String var1 = System.getProperty("fml.modStates", "");
//		FMLLog.finer("Received a system property request '%s'", new Object[]{var1});
//		Map var2 = Splitter.on(CharMatcher.anyOf(";:")).omitEmptyStrings().trimResults().withKeyValueSeparator("=").split(var1);
//		FMLLog.finer("System property request managing the state of %d mods", new Object[]{var2.size()});
//		HashMap var3 = Maps.newHashMap();
//		File var4 = new File(this.get_canonicalConfigDir(), "fmlModState.properties");
//		Properties var5 = new Properties();
//		if (var4.exists() && var4.isFile()) {
//			FMLLog.finer("Found a mod state file %s", new Object[]{var4.getName()});
//
//			try {
//				var5.load(new FileReader(var4));
//				FMLLog.finer("Loaded states for %d mods from file", new Object[]{var5.size()});
//			} catch (Exception var9) {
//				FMLLog.log(Level.INFO, var9, "An error occurred reading the fmlModState.properties file", new Object[0]);
//			}
//		}
//
//		var3.putAll(Maps.fromProperties(var5));
//		var3.putAll(var2);
//		FMLLog.fine("After merging, found state information for %d mods", new Object[]{var3.size()});
//		Map var6 = Maps.transformValues(var3, new Loader$2(this));
//		Iterator var7 = var6.entrySet().iterator();
//
//		while(var7.hasNext()) {
//			Map.Entry var8 = (Map.Entry)var7.next();
//			if (this.get_namedMods().containsKey(var8.getKey())) {
//				FMLLog.info("Setting mod %s to enabled state %b", new Object[]{var8.getKey(), var8.getValue()});
//				((ModContainer)this.get_namedMods().get(var8.getKey())).setEnabledState((Boolean)var8.getValue());
//			}
//		}
	
	}
	
	public static boolean isModLoaded(String var0) {
		return instance().namedMods.containsKey(var0) && instance().modController.getModState((ModContainer)instance.namedMods.get(var0)) != ModState.DISABLED;
	}
	
	public File getConfigDir() {
		return this.get_canonicalConfigDir();
	}
	
	public String getCrashInformation() {
		throw new UnsupportedOperationException("RedMill: Loader.getCrashInformation not implemented");
//		StringBuilder var1 = new StringBuilder();
//		List var2 = FMLCommonHandler.instance().getBrandings();
//		Joiner.on(' ').skipNulls().appendTo(var1, var2.subList(1, var2.size()));
//		if (this.get_modController() != null) {
//			this.get_modController().printModStates(var1);
//		}
//
//		return var1.toString();
	}
	
	public String getFMLVersionString() {
		return String.format("%s.%s.%s.%s", get_major(), get_minor(), get_rev(), get_build());
	}
	
	public ClassLoader getModClassLoader() {
		throw new UnsupportedOperationException("RedMill: Loader.getModClassLoader not implemented");
		//return this.get_modClassLoader();
	}
	
	public void computeDependencies(String var1, Set var2, List var3, List var4) {
//		if (var1 != null && var1.length() != 0) {
//			boolean var5 = false;
//			Iterator var6 = get_DEPENDENCYSPLITTER().split(var1).iterator();
//
//			while(true) {
//				while(var6.hasNext()) {
//					String var7 = (String)var6.next();
//					ArrayList var8 = Lists.newArrayList(get_DEPENDENCYPARTSPLITTER().split(var7));
//					if (var8.size() != 2) {
//						var5 = true;
//					} else {
//						String var9 = (String)var8.get(0);
//						String var10 = (String)var8.get(1);
//						boolean var11 = var10.startsWith("*");
//						if (var11 && var10.length() > 1) {
//							var5 = true;
//						} else {
//							if ("required-before".equals(var9) || "required-after".equals(var9)) {
//								if (var11) {
//									var5 = true;
//									continue;
//								}
//
//								var2.add(VersionParser.parseVersionReference(var10));
//							}
//
//							if (var11 && var10.indexOf(64) > -1) {
//								var5 = true;
//							} else if (!"required-before".equals(var9) && !"before".equals(var9)) {
//								if (!"required-after".equals(var9) && !"after".equals(var9)) {
//									var5 = true;
//								} else {
//									var3.add(VersionParser.parseVersionReference(var10));
//								}
//							} else {
//								var4.add(VersionParser.parseVersionReference(var10));
//							}
//						}
//					}
//				}
//
//				if (var5) {
//					FMLLog.log(Level.WARNING, "Unable to parse dependency string %s", new Object[]{var1});
//					throw new LoaderException();
//				}
//
//				return;
//			}
//		}
	}
	
	public Map getIndexedModList() {
		return ImmutableMap.copyOf(this.get_namedMods());
	}
	
	public void initializeMods() {
		this.get_modController().distributeStateMessage(LoaderState.INITIALIZATION);
		this.get_modController().transition(LoaderState.POSTINITIALIZATION);
		//this.get_modController().distributeStateMessage(FMLInterModComms.IMCEvent.class);
		this.get_modController().distributeStateMessage(LoaderState.POSTINITIALIZATION);
		this.get_modController().transition(LoaderState.AVAILABLE);
		this.get_modController().distributeStateMessage(LoaderState.AVAILABLE);
		FMLLog.info("Forge Mod Loader has successfully loaded %d mod%s", this.get_mods().size(), this.get_mods().size() == 1 ? "" : "s");
	}
	
//	public ICrashCallable getCallableCrashInformation() {
//		return new Loader$3(this);
//	}
	
	public List getActiveModList() {
		return (List)(this.get_modController() != null ? this.get_modController().getActiveModList() : ImmutableList.of());
	}
	
	public LoaderState.ModState getModState(ModContainer var1) {
		return this.get_modController().getModState(var1);
	}
	
	public String getMCVersionString() {
		return "Minecraft " + get_mccversion();
	}
	
	public boolean serverStarting(Object var1) {
		try {
			this.get_modController().distributeStateMessage(LoaderState.SERVER_STARTING, new Object[]{var1});
			this.get_modController().transition(LoaderState.SERVER_STARTING);
			return true;
		} catch (Throwable var3) {
			FMLLog.log(Level.SEVERE, var3, "A fatal exception occurred during the server starting event", new Object[0]);
			return false;
		}
	}
	
	public void serverStarted() {
		this.get_modController().distributeStateMessage(LoaderState.SERVER_STARTED, new Object[0]);
		this.get_modController().transition(LoaderState.SERVER_STARTED);
	}
	
	public void serverStopping() {
		this.get_modController().distributeStateMessage(LoaderState.SERVER_STOPPING, new Object[0]);
		this.get_modController().transition(LoaderState.SERVER_STOPPING);
	}
	
	public BiMap getModObjectList() {
		return this.get_modController().getModObjectList();
	}
	
	public BiMap getReversedModObjectList() {
		return this.getModObjectList().inverse();
	}
	
	public ModContainer activeModContainer() {
		return this.get_modController() != null ? this.get_modController().activeContainer() : null;
	}
	
	public boolean isInState(LoaderState var1) {
		return this.get_modController().isInState(var1);
	}
	
//	public MinecraftDummyContainer getMinecraftModContainer() {
//		return this.get_minecraft();
//	}
	
	public boolean hasReachedState(LoaderState var1) {
		return this.get_modController() != null ? this.get_modController().hasReachedState(var1) : false;
	}
	
	public String getMCPVersionString() {
		return String.format("MCP v%s", get_mcpversion());
	}
	
	public void serverStopped() {
		this.get_modController().distributeStateMessage(LoaderState.SERVER_STOPPED, new Object[0]);
		this.get_modController().transition(LoaderState.SERVER_STOPPED);
		this.get_modController().transition(LoaderState.AVAILABLE);
	}
	
	public boolean serverAboutToStart(Object var1) {
		try {
			this.get_modController().distributeStateMessage(LoaderState.SERVER_ABOUT_TO_START, new Object[]{var1});
			this.get_modController().transition(LoaderState.SERVER_ABOUT_TO_START);
			return true;
		} catch (Throwable var3) {
			FMLLog.log(Level.SEVERE, var3, "A fatal exception occurred during the server about to start event", new Object[0]);
			return false;
		}
	}
	
	public static Splitter get_DEPENDENCYPARTSPLITTER() {
		return DEPENDENCYPARTSPLITTER;
	}
	
	public static Splitter get_DEPENDENCYSPLITTER() {
		return DEPENDENCYSPLITTER;
	}
	
	public static Loader get_instance() {
		return instance;
	}
	
	public static void set_instance(Loader var0) {
		instance = var0;
	}
	
	public static String get_major() {
		return major;
	}
	
	public static void set_major(String var0) {
		major = var0;
	}
	
	public static String get_minor() {
		return minor;
	}
	
	public static void set_minor(String var0) {
		minor = var0;
	}
	
	public static String get_rev() {
		return rev;
	}
	
	public static void set_rev(String var0) {
		rev = var0;
	}
	
	public static String get_build() {
		return build;
	}
	
	public static void set_build(String var0) {
		build = var0;
	}
	
	public static String get_mccversion() {
		return mccversion;
	}
	
	public static void set_mccversion(String var0) {
		mccversion = var0;
	}
	
	public static String get_mcpversion() {
		return mcpversion;
	}
	
	public static void set_mcpversion(String var0) {
		mcpversion = var0;
	}
	
//	public ModClassLoader get_modClassLoader() {
//		return this.modClassLoader;
//	}
//
//	public void set_modClassLoader(ModClassLoader var1) {
//		this.modClassLoader = var1;
//	}
	
	public List get_mods() {
		return this.mods;
	}
	
	public void set_mods(List var1) {
		this.mods = var1;
	}
	
	public Map get_namedMods() {
		return this.namedMods;
	}
	
	public void set_namedMods(Map var1) {
		this.namedMods = var1;
	}
	
	public File get_canonicalConfigDir() {
		return this.canonicalConfigDir;
	}
	
	public void set_canonicalConfigDir(File var1) {
		this.canonicalConfigDir = var1;
	}
	
	public File get_canonicalMinecraftDir() {
		return this.canonicalMinecraftDir;
	}
	
	public void set_canonicalMinecraftDir(File var1) {
		this.canonicalMinecraftDir = var1;
	}
	
	public Exception get_capturedError() {
		return this.capturedError;
	}
	
	public void set_capturedError(Exception var1) {
		this.capturedError = var1;
	}
	
	public File get_canonicalModsDir() {
		return this.canonicalModsDir;
	}
	
	public void set_canonicalModsDir(File var1) {
		this.canonicalModsDir = var1;
	}
	
	public LoadController get_modController() {
		return this.modController;
	}
	
	public void set_modController(LoadController var1) {
		this.modController = var1;
	}
	
//	public MinecraftDummyContainer get_minecraft() {
//		return this.minecraft;
//	}
//
//	public void set_minecraft(MinecraftDummyContainer var1) {
//		this.minecraft = var1;
//	}
//
//	public MCPDummyContainer get_mcp() {
//		return this.mcp;
//	}
//
//	public void set_mcp(MCPDummyContainer var1) {
//		this.mcp = var1;
//	}
	
	public static File get_minecraftDir() {
		return minecraftDir;
	}
	
	public static void set_minecraftDir(File var0) {
		minecraftDir = var0;
	}
	
	public static List get_injectedContainers() {
		return injectedContainers;
	}
	
	public static void set_injectedContainers(List var0) {
		injectedContainers = var0;
	}
	
	public File get_loggingProperties() {
		return this.loggingProperties;
	}
	
	public void set_loggingProperties(File var1) {
		this.loggingProperties = var1;
	}
}
