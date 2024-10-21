//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.game;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.*;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event.*;
import agency.highlysuspect.redmill.svc.Consts;
import agency.highlysuspect.redmill.svc.ModContainerExt;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Multimap;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.io.File;
import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.cert.Certificate;
import java.util.*;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class OldschoolModContainer implements ModContainer {
	private Mod modDescriptor;
	private Object modInstance;
	private File source;
	private ModMetadata modMetadata;
	private String className;
//	private Map descriptor;
	private boolean enabled = true;
	private String internalVersion;
	private boolean overridesMetadata;
	private EventBus eventBus;
	private LoadController controller;
	private Multimap annotations;
//	private DefaultArtifactVersion processedVersion;
	private boolean isNetworkMod;
	private static final ImmutableBiMap modAnnotationTypes = ImmutableBiMap.builder()
		.put(FMLPreInitializationEvent.class, Mod.PreInit.class)
		.put(FMLInitializationEvent.class, Mod.Init.class)
		.put(FMLPostInitializationEvent.class, Mod.PostInit.class)
		.put(FMLServerAboutToStartEvent.class, Mod.ServerAboutToStart.class)
		.put(FMLServerStartingEvent.class, Mod.ServerStarting.class)
		.put(FMLServerStartedEvent.class, Mod.ServerStarted.class)
		.put(FMLServerStoppingEvent.class, Mod.ServerStopping.class)
		.put(FMLServerStoppedEvent.class, Mod.ServerStopped.class)
		//.put(FMLInterModComms.IMCEvent.class, Mod.IMCCallback.class)
		//.put(FMLFingerprintViolationEvent.class, Mod.FingerprintWarning.class)
		.build();
	private static final BiMap modTypeAnnotations;
	private String annotationDependencies;
//	private VersionRange minecraftAccepted;
	private boolean fingerprintNotPresent;
	private Set sourceFingerprints = new HashSet();
	private Certificate certificate;
	
	public ModContainerExt redmill$ext;
	
	public OldschoolModContainer(ModContainerExt ext) {
		this.className = ext.modernModContainer.modClassName;
		this.source = ext.modernModInfo.getOwningFile().getFile().getFilePath().toFile(); //file file file
		this.modMetadata = new ModMetadata(ext);
		this.internalVersion = this.modMetadata.version;
		
		this.redmill$ext = ext;
	}
	
//	public FMLModContainer2(String var1, File var2, Map var3) {
//		this.className = var1;
//		this.source = var2;
//		this.descriptor = var3;
//	}
	
	public String getModId() {
		//return (String)this.get_descriptor().get("modid");
		return redmill$ext.oldModid;
	}
	
	public String getName() {
		return this.get_modMetadata().get_name();
	}
	
	public String getVersion() {
		return this.get_internalVersion();
	}
	
	public File getSource() {
		return this.get_source();
	}
	
	public ModMetadata getMetadata() {
		return this.get_modMetadata();
	}
	
	public void bindMetadata(MetadataCollection var1) {
		var1.redmill$putMetadata(this.modMetadata);
		
//		this.set_modMetadata(var1.getMetadataForId(this.getModId(), this.get_descriptor()));
//		if (this.get_descriptor().containsKey("useMetadata")) {
//			this.set_overridesMetadata(!(Boolean)this.get_descriptor().get("useMetadata"));
//		}
//
//		if (!this.get_overridesMetadata() && this.get_modMetadata().get_useDependencyInformation()) {
//			FMLLog.log(this.getModId(), Level.FINEST, "Using mcmod dependency info : %s %s %s", new Object[]{this.get_modMetadata().get_requiredMods(), this.get_modMetadata().get_dependencies(), this.get_modMetadata().get_dependants()});
//		} else {
//			HashSet var2 = Sets.newHashSet();
//			ArrayList var3 = Lists.newArrayList();
//			ArrayList var4 = Lists.newArrayList();
//			this.set_annotationDependencies((String)this.get_descriptor().get("dependencies"));
//			Loader.instance().computeDependencies(this.get_annotationDependencies(), var2, var3, var4);
//			this.get_modMetadata().set_requiredMods(var2);
//			this.get_modMetadata().set_dependencies(var3);
//			this.get_modMetadata().set_dependants(var4);
//			FMLLog.log(this.getModId(), Level.FINEST, "Parsed dependency info : %s %s %s", new Object[]{var2, var3, var4});
//		}
//
//		if (Strings.isNullOrEmpty(this.get_modMetadata().get_name())) {
//			FMLLog.log(this.getModId(), Level.INFO, "Mod %s is missing the required element 'name'. Substituting %s", new Object[]{this.getModId(), this.getModId()});
//			this.get_modMetadata().set_name(this.getModId());
//		}
//
//		this.set_internalVersion((String)this.get_descriptor().get("version"));
//		if (Strings.isNullOrEmpty(this.get_internalVersion())) {
//			Properties var5 = this.searchForVersionProperties();
//			if (var5 != null) {
//				this.set_internalVersion(var5.getProperty(this.getModId() + ".version"));
//				FMLLog.log(this.getModId(), Level.FINE, "Found version %s for mod %s in version.properties, using", new Object[]{this.get_internalVersion(), this.getModId()});
//			}
//		}
//
//		if (Strings.isNullOrEmpty(this.get_internalVersion()) && !Strings.isNullOrEmpty(this.get_modMetadata().get_version())) {
//			FMLLog.log(this.getModId(), Level.WARNING, "Mod %s is missing the required element 'version' and a version.properties file could not be found. Falling back to metadata version %s", new Object[]{this.getModId(), this.get_modMetadata().get_version()});
//			this.set_internalVersion(this.get_modMetadata().get_version());
//		}
//
//		if (Strings.isNullOrEmpty(this.get_internalVersion())) {
//			FMLLog.log(this.getModId(), Level.WARNING, "Mod %s is missing the required element 'version' and no fallback can be found. Substituting '1.0'.", new Object[]{this.getModId()});
//			ModMetadata var10000 = this.get_modMetadata();
//			this.set_internalVersion("1.0");
//			var10000.set_version("1.0");
//		}
//
//		String var6 = (String)this.get_descriptor().get("acceptedMinecraftVersions");
//		if (!Strings.isNullOrEmpty(var6)) {
//			this.set_minecraftAccepted(VersionParser.parseRange(var6));
//		} else {
//			this.set_minecraftAccepted(Loader.instance().getMinecraftModContainer().getStaticVersionRange());
//		}
		
	}
	
	public Properties searchForVersionProperties() {
		try {
			FMLLog.log(this.getModId(), Level.FINE, "Attempting to load the file version.properties from %s to locate a version number for %s", new Object[]{this.getSource().getName(), this.getModId()});
			Properties var1 = null;
			if (this.getSource().isFile()) {
				ZipFile var2 = new ZipFile(this.getSource());
				ZipEntry var3 = var2.getEntry("version.properties");
				if (var3 != null) {
					var1 = new Properties();
					var1.load(var2.getInputStream(var3));
				}
				
				var2.close();
			} else if (this.getSource().isDirectory()) {
				File var5 = new File(this.getSource(), "version.properties");
				if (var5.exists() && var5.isFile()) {
					var1 = new Properties();
					FileInputStream var6 = new FileInputStream(var5);
					var1.load(var6);
					var6.close();
				}
			}
			
			return var1;
		} catch (Exception var4) {
			Throwables.propagateIfPossible(var4);
			FMLLog.log(this.getModId(), Level.FINEST, "Failed to find a usable version.properties file", new Object[0]);
			return null;
		}
	}
	
	public void setEnabledState(boolean var1) {
		this.set_enabled(var1);
	}
	
	public Set getRequirements() {
		return this.get_modMetadata().get_requiredMods();
	}
	
	public List getDependencies() {
		return this.get_modMetadata().get_dependencies();
	}
	
	public List getDependants() {
		return this.get_modMetadata().get_dependants();
	}
	
	public String getSortingRules() {
		return !this.get_overridesMetadata() && this.get_modMetadata().get_useDependencyInformation() ? this.get_modMetadata().printableSortingRules() : Strings.nullToEmpty(this.get_annotationDependencies());
	}
	
	public boolean matches(Object var1) {
		return var1 == this.get_modInstance();
	}
	
	public Object getMod() {
		return this.get_modInstance();
	}
	
	public boolean registerBus(EventBus var1, LoadController var2) {
		if (this.get_enabled()) {
			FMLLog.log(this.getModId(), Level.FINE, "Enabling mod %s", new Object[]{this.getModId()});
			this.set_eventBus(var1);
			this.set_controller(var2);
			this.get_eventBus().register(this);
			return true;
		} else {
			return false;
		}
	}
	
	private Multimap gatherAnnotations(Class<?> modClass) throws Exception {
		ArrayListMultimap var2 = ArrayListMultimap.create();
		Method[] var3 = modClass.getDeclaredMethods();
		int var4 = var3.length;
		
		for(int var5 = 0; var5 < var4; ++var5) {
			Method var6 = var3[var5];
			Annotation[] var7 = var6.getAnnotations();
			int var8 = var7.length;
			
			for(int var9 = 0; var9 < var8; ++var9) {
				Annotation var10 = var7[var9];
				if (get_modTypeAnnotations().containsKey(var10.annotationType())) {
					Class[] var11 = new Class[]{(Class)get_modTypeAnnotations().get(var10.annotationType())};
					if (Arrays.equals(var6.getParameterTypes(), var11)) {
						var6.setAccessible(true);
						var2.put(var10.annotationType(), var6);
					} else {
						FMLLog.log(this.getModId(), Level.SEVERE, "The mod %s appears to have an invalid method annotation %s. This annotation can only apply to methods with argument types %s -it will not be called", new Object[]{this.getModId(), var10.annotationType().getSimpleName(), Arrays.toString(var11)});
					}
				}
			}
		}
		
		return var2;
	}
	
//	private void processFieldAnnotations(ASMDataTable var1) throws Exception {
//		SetMultimap var2 = var1.getAnnotationsFor(this);
//		this.parseSimpleFieldAnnotation(var2, Mod.Instance.class.getName(), new FMLModContainer2$1(this));
//		this.parseSimpleFieldAnnotation(var2, Mod.Metadata.class.getName(), new FMLModContainer2$2(this));
//	}
	
//	private void parseSimpleFieldAnnotation(SetMultimap var1, String var2, Function var3) throws IllegalAccessException {
//		String[] var4 = var2.split("\\.");
//		String var5 = var4[var4.length - 1];
//		Iterator var6 = var1.get(var2).iterator();
//
//		while(true) {
//			while(true) {
//				ASMDataTable.ASMData var7;
//				Field var9;
//				Object var10;
//				Object var11;
//				boolean var12;
//				Class var13;
//				do {
//					if (!var6.hasNext()) {
//						return;
//					}
//
//					var7 = (ASMDataTable.ASMData)var6.next();
//					String var8 = (String)var7.getAnnotationInfo().get("value");
//					var9 = null;
//					var10 = null;
//					var11 = this;
//					var12 = false;
//					var13 = this.get_modInstance().getClass();
//					if (!Strings.isNullOrEmpty(var8)) {
//						if (Loader.isModLoaded(var8)) {
//							var11 = (ModContainer)Loader.instance().getIndexedModList().get(var8);
//						} else {
//							var11 = null;
//						}
//					}
//
//					if (var11 != null) {
//						try {
//							var13 = Class.forName(var7.getClassName(), true, Loader.instance().getModClassLoader());
//							var9 = var13.getDeclaredField(var7.getObjectName());
//							var9.setAccessible(true);
//							var12 = Modifier.isStatic(var9.getModifiers());
//							var10 = var3.apply(var11);
//						} catch (Exception var15) {
//							Throwables.propagateIfPossible(var15);
//							FMLLog.log(this.getModId(), Level.WARNING, var15, "Attempting to load @%s in class %s for %s and failing", new Object[]{var5, var7.getClassName(), ((ModContainer)var11).getModId()});
//						}
//					}
//				} while(var9 == null);
//
//				Object var14 = null;
//				if (!var12) {
//					var14 = this.get_modInstance();
//					if (!this.get_modInstance().getClass().equals(var13)) {
//						FMLLog.log(this.getModId(), Level.WARNING, "Unable to inject @%s in non-static field %s.%s for %s as it is NOT the primary mod instance", new Object[]{var5, var7.getClassName(), var7.getObjectName(), ((ModContainer)var11).getModId()});
//						continue;
//					}
//				}
//
//				var9.set(var14, var10);
//			}
//		}
//	}
	
	@Subscribe
	public void constructMod(FMLConstructionEvent var1) {
		if(redmill$ext.modernModContainer.modClassName == null) {
			Consts.LOG.warn("No modClassName for {}, skipping initialization", redmill$ext.oldModid);
			return;
		}
		
		try {
			//Actually the mod was already constructed by the NeoForge loading process.
			Objects.requireNonNull(redmill$ext.modernModContainer.modClassName);
			Objects.requireNonNull(redmill$ext.modernModContainer.modClass);
			
//			ModClassLoader var2 = var1.getModClassLoader();
//			var2.addFile(this.get_source());
//			Class var3 = Class.forName(this.get_className(), true, var2);
//			Certificate[] var4 = var3.getProtectionDomain().getCodeSource().getCertificates();
//			int var5 = 0;
//			if (var4 != null) {
//				var5 = var4.length;
//			}
//
//			ImmutableList.Builder var6 = ImmutableList.builder();
//
//			for(int var7 = 0; var7 < var5; ++var7) {
//				var6.add(CertificateHelper.getFingerprint(var4[var7]));
//			}
//
//			ImmutableList var11 = var6.build();
//			this.set_sourceFingerprints(ImmutableSet.copyOf(var11));
			this.sourceFingerprints = new HashSet<>();
//			String var8 = (String)this.get_descriptor().get("certificateFingerprint");
			this.set_fingerprintNotPresent(true);
//			if (var8 != null && !var8.isEmpty()) {
//				if (!this.get_sourceFingerprints().contains(var8)) {
//					Level var9 = Level.SEVERE;
//					if (this.get_source().isDirectory()) {
//						var9 = Level.FINER;
//					}
//
//					FMLLog.log(this.getModId(), var9, "The mod %s is expecting signature %s for source %s, however there is no signature matching that description", new Object[]{this.getModId(), var8, this.get_source().getName()});
//				} else {
//					this.set_certificate(var4[var11.indexOf(var8)]);
//					this.set_fingerprintNotPresent(false);
//				}
//			}
			
			this.set_annotations(this.gatherAnnotations(redmill$ext.modernModContainer.modClass));
			//this.set_isNetworkMod(FMLNetworkHandler.instance().registerNetworkMod(this, var3, var1.getASMHarvestedData()));
			//this.set_modInstance(var3.newInstance());
			this.modInstance = redmill$ext.modernModContainer.modInstance;
//			if (this.get_fingerprintNotPresent()) {
//				this.get_eventBus().post(new FMLFingerprintViolationEvent(this.get_source().isDirectory(), this.get_source(), ImmutableSet.copyOf(this.get_sourceFingerprints()), var8));
//			}
			
			//ProxyInjector.inject(this, var1.getASMHarvestedData(), FMLCommonHandler.instance().getSide());
			//this.processFieldAnnotations(var1.getASMHarvestedData()); //RM-TODO what does this doooo
		} catch (Throwable var10) {
			this.get_controller().errorOccurred(this, var10);
			Throwables.propagateIfPossible(var10);
		}
		
	}
	
	@Subscribe
	public void handleModStateEvent(FMLEvent var1) {
		FMLLog.info("handleModStateEvent " + var1.getClass() + " " + redmill$ext.modernModContainer.modClassName);
		
		Class var2 = (Class)get_modAnnotationTypes().get(var1.getClass());
		if (var2 != null) {
			try {
				Iterator var3 = this.get_annotations().get(var2).iterator();
				
				while(var3.hasNext()) {
					Object var4 = var3.next();
					Method var5 = (Method)var4;
					var5.invoke(this.get_modInstance(), var1);
				}
			} catch (Throwable var6) {
				this.get_controller().errorOccurred(this, var6);
				Throwables.propagateIfPossible(var6);
			}
			
		}
	}
	
//	public ArtifactVersion getProcessedVersion() {
//		if (this.get_processedVersion() == null) {
//			this.set_processedVersion(new DefaultArtifactVersion(this.getModId(), this.getVersion()));
//		}
//
//		return this.get_processedVersion();
//	}
	
	public boolean isImmutable() {
		return false;
	}
	
	public boolean isNetworkMod() {
		return this.get_isNetworkMod();
	}
	
	public String getDisplayVersion() {
		return this.get_modMetadata().get_version();
	}
	
//	public VersionRange acceptableMinecraftVersionRange() {
//		return this.get_minecraftAccepted();
//	}
	
	public Certificate getSigningCertificate() {
		return this.get_certificate();
	}
	
	static {
		modTypeAnnotations = modAnnotationTypes.inverse();
	}
	
	public Mod get_modDescriptor() {
		return this.modDescriptor;
	}
	
	public void set_modDescriptor(Mod var1) {
		this.modDescriptor = var1;
	}
	
	public Object get_modInstance() {
		return this.modInstance;
	}
	
	public void set_modInstance(Object var1) {
		this.modInstance = var1;
	}
	
	public File get_source() {
		return this.source;
	}
	
	public void set_source(File var1) {
		this.source = var1;
	}
	
	public ModMetadata get_modMetadata() {
		return this.modMetadata;
	}
	
	public void set_modMetadata(ModMetadata var1) {
		this.modMetadata = var1;
	}
	
	public String get_className() {
		return this.className;
	}
	
	public void set_className(String var1) {
		this.className = var1;
	}
	
//	public Map get_descriptor() {
//		return this.descriptor;
//	}
//
//	public void set_descriptor(Map var1) {
//		this.descriptor = var1;
//	}
	
	public boolean get_enabled() {
		return this.enabled;
	}
	
	public void set_enabled(boolean var1) {
		this.enabled = var1;
	}
	
	public String get_internalVersion() {
		return this.internalVersion;
	}
	
	public void set_internalVersion(String var1) {
		this.internalVersion = var1;
	}
	
	public boolean get_overridesMetadata() {
		return this.overridesMetadata;
	}
	
	public void set_overridesMetadata(boolean var1) {
		this.overridesMetadata = var1;
	}
	
	public EventBus get_eventBus() {
		return this.eventBus;
	}
	
	public void set_eventBus(EventBus var1) {
		this.eventBus = var1;
	}
	
	public LoadController get_controller() {
		return this.controller;
	}
	
	public void set_controller(LoadController var1) {
		this.controller = var1;
	}
	
	public Multimap get_annotations() {
		return this.annotations;
	}
	
	public void set_annotations(Multimap var1) {
		this.annotations = var1;
	}
	
//	public DefaultArtifactVersion get_processedVersion() {
//		return this.processedVersion;
//	}
//
//	public void set_processedVersion(DefaultArtifactVersion var1) {
//		this.processedVersion = var1;
//	}
	
	public boolean get_isNetworkMod() {
		return this.isNetworkMod;
	}
	
	public void set_isNetworkMod(boolean var1) {
		this.isNetworkMod = var1;
	}
	
	public static BiMap get_modAnnotationTypes() {
		return modAnnotationTypes;
	}
	
	public static BiMap get_modTypeAnnotations() {
		return modTypeAnnotations;
	}
	
	public String get_annotationDependencies() {
		return this.annotationDependencies;
	}
	
	public void set_annotationDependencies(String var1) {
		this.annotationDependencies = var1;
	}
	
//	public VersionRange get_minecraftAccepted() {
//		return this.minecraftAccepted;
//	}
//
//	public void set_minecraftAccepted(VersionRange var1) {
//		this.minecraftAccepted = var1;
//	}
	
	public boolean get_fingerprintNotPresent() {
		return this.fingerprintNotPresent;
	}
	
	public void set_fingerprintNotPresent(boolean var1) {
		this.fingerprintNotPresent = var1;
	}
	
	public Set get_sourceFingerprints() {
		return this.sourceFingerprints;
	}
	
	public void set_sourceFingerprints(Set var1) {
		this.sourceFingerprints = var1;
	}
	
	public Certificate get_certificate() {
		return this.certificate;
	}
	
	public void set_certificate(Certificate var1) {
		this.certificate = var1;
	}
}
