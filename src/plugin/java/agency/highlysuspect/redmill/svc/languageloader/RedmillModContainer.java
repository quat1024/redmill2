package agency.highlysuspect.redmill.svc.languageloader;

import agency.highlysuspect.redmill.svc.Globals;
import agency.highlysuspect.redmill.svc.Consts;
import agency.highlysuspect.redmill.svc.ModContainerExt;
import agency.highlysuspect.redmill.svc.ModFileExt;
import agency.highlysuspect.redmill.svc.util.IBastion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Type;

import java.util.Objects;

public class RedmillModContainer extends ModContainer {
	
	public RedmillModContainer(IModInfo info, ModFileScanData modFileScanData, ModuleLayer gameLayer) {
		super(info);
		
		this.bastion = IBastion.getInstance(gameLayer);
		
		// file stuff
		
		this.modFileExt = Globals.getModFileExt(info.getOwningFile().getFile());
		if(modFileExt == null)
			throw new IllegalStateException("No ModFileExt for " + info.getOwningFile().getFile());
		
		this.module = gameLayer.findModule(modFileExt.javaModuleName)
			.orElseThrow(() -> new RuntimeException("Couldn't find a module named " + modFileExt.javaModuleName));
		
		// mod stuff
		
		this.modContainerExt = Globals.getModContainerByNewId(info.getModId());
		if(modContainerExt == null)
			throw new IllegalStateException("No ModContainerExt for modern modid " + info.getModId());
		
		Globals.associateWithModInfo(modContainerExt, info);
		Globals.associateWithModContainer(modContainerExt, this);
		
		String entrypoint = findEntrypoint(modFileScanData, modContainerExt.oldModid);
		if(!Globals.CFG.initMods) {
			Consts.LOG.warn("initMods disabled - not loading entrypoints for {}", modContainerExt.oldModid);
			this.modClassName = null;
		} else {
			this.modClassName = entrypoint;
		}
	}
	
	private final IBastion bastion;
	
	public final ModFileExt modFileExt;
	public final ModContainerExt modContainerExt;
	
	public final Module module;
	
	@Nullable public String modClassName;
	@Nullable public Class<?> modClass;
	@Nullable public Object modInstance;
	
	private static final Type CPW_MOD = Type.getObjectType("cpw/mods/fml/common/Mod");
	
	@Override
	public @Nullable IEventBus getEventBus() {
		return bastion.getEventBus();
	}
	
	@Override
	protected void constructMod() {
		Consts.windowLog("Constructing {}", modContainerExt.oldModid);
		
		if(modClassName != null) {
			Consts.LOG.debug("Loading entrypoint class {}", modClassName);
			try {
				modClass = Objects.requireNonNull(Class.forName(module, modClassName));
			} catch (Exception e) {
				throw Globals.mkRethrow(e, "Failed to load redmill entrypoint class " + modClassName);
			}
			
			Consts.LOG.debug("Constructing mod class {}", modClass);
			try {
				modInstance = modClass.getConstructor().newInstance();
			} catch (Exception e) {
				throw Globals.mkRethrow(e, "Failed to construct redmill entrypoint " + modClass.getName());
			}
		}
	}
	
	private static @Nullable String findEntrypoint(ModFileScanData mfsd, String oldModid) {
		//ModFileScanData contains classes before the mods got remapped,
		//so the @Mod annotation will still be CPW_MOD
		
		for(ModFileScanData.AnnotationData annotationData : mfsd.getAnnotations()) {
			if(annotationData.annotationType().equals(CPW_MOD)) {
				Object oldModidMaybe = annotationData.annotationData().get("modid");
				if(oldModidMaybe instanceof String oldModid2 && oldModid2.equals(oldModid)) {
					Consts.LOG.info("APPLICABLE entrypoint for {} is {}", oldModid, annotationData.clazz().getClassName());
					return annotationData.clazz().getClassName();
				}
			}
		}
		
		return null;
	}
}
