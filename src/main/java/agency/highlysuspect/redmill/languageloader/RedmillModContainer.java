package agency.highlysuspect.redmill.languageloader;

import agency.highlysuspect.redmill.Globals;
import agency.highlysuspect.redmill.Consts;
import agency.highlysuspect.redmill.ModContainerExt;
import agency.highlysuspect.redmill.ModFileExt;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Type;

import java.util.Objects;

public class RedmillModContainer extends ModContainer {
	
	private static final Type CPW_MOD = Type.getObjectType("cpw/mods/fml/common/Mod");
	
	public RedmillModContainer(IModInfo info, ModFileScanData modFileScanData, ModuleLayer gameLayer) {
		super(info);
		
		//// file specific ////
		
		this.modFileScanData = modFileScanData;
		
		//get the ModFileExt
		this.modFileExt = Globals.getModFileExt(info.getOwningFile().getFile());
		if(modFileExt == null)
			throw new IllegalStateException("No ModFileExt for " + info.getOwningFile().getFile());
		
		//find the java module this mod lives inside
		this.module = gameLayer.findModule(modFileExt.javaModuleName)
			.orElseThrow(() -> new RuntimeException("Couldn't find a module named " + modFileExt.javaModuleName));
		
		//// mod specific ////
		
		//get the ModContainerExt
		this.modContainerExt = Globals.getModContainerByNewId(info.getModId());
		if(modContainerExt == null)
			throw new IllegalStateException("No ModContainerExt for modern modid " + info.getModId());
		
		//associate the ModContainerExt with the ModInfo - TODO: do this earlier
		Globals.associateWithModInfo(modContainerExt, info);
		
		//cant do this one earlier tho
		Globals.associateWithModContainer(modContainerExt, this);
	}
	
	public final ModFileExt modFileExt;
	public final ModContainerExt modContainerExt;
	
	private final Module module;
	public ModFileScanData modFileScanData;
	
	@Nullable Class<?> modClass;
	@Nullable Object modInstance;
	
	@Override
	public @Nullable IEventBus getEventBus() {
		return null;
	}
	
	@Override
	protected void constructMod() {
		//find the @Mod annotation with the correct modid
		String oldModid = modContainerExt.oldModid;
		String entrypoint = null;
		for(ModFileScanData.AnnotationData annotationData : modFileScanData.getAnnotations()) {
			if(annotationData.annotationType().equals(CPW_MOD)) {
				Object oldModidMaybe = annotationData.annotationData().get("modid");
				if(oldModidMaybe instanceof String oldModid2 && oldModid2.equals(oldModid)) {
					Consts.LOG.info("APPLICABLE entrypoint for {} is {}", oldModid, annotationData.clazz().getClassName());
					entrypoint = annotationData.clazz().getClassName();
					break;
				}
			}
		}
		
		if(!Globals.CFG.initMods) {
			Consts.LOG.warn("initMods disabled - not loading entrypoints for " + modContainerExt.oldModid);
			return;
		}
		
		if(entrypoint != null) {
			Consts.LOG.debug("Loading entrypoint class {}", entrypoint);
			try {
				modClass = Objects.requireNonNull(Class.forName(module, entrypoint));
			} catch (Exception e) {
				throw Globals.mkRethrow(e, "Failed to load redmill entrypoint class " + entrypoint);
			}
			
			Consts.LOG.debug("Constructing mod class {}", modClass);
			try {
				modInstance = modClass.getConstructor().newInstance();
			} catch (Exception e) {
				throw Globals.mkRethrow(e, "Failed to construct redmill entrypoint " + modClass);
			}
		}
	}
}
