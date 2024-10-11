package agency.highlysuspect.redmill.languageloader;

import agency.highlysuspect.redmill.Globals;
import agency.highlysuspect.redmill.Consts;
import agency.highlysuspect.redmill.ModContainerExt;
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
		
		//get the ModContainerExt, which better exist
		ModContainerExt ext = Globals.getModContainerByNewId(info.getModId());
		if(ext == null) {
			throw new IllegalStateException("No ModContainerExt for modern modid " + info.getModId());
		}
		
		Globals.associateWithModInfo(ext, info);
		Globals.associateWithModContainer(ext, this);
		
		//tell RedmillLaunchPluginService about the classes in this jar
		Globals.classesToBeMilled(modFileScanData, ext);
		
		//find the old modid, and find the java module the mod lives inside
		String oldModid = ext.oldModid;
		Module module = gameLayer.findModule(ext.javaModuleName)
			.orElseThrow(() -> new RuntimeException("Couldn't find a module named " + ext.javaModuleName));
		
		//find the applicable entrypoint (@Mod annotation with the correct modid)
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
			Consts.LOG.warn("initMods disabled - not loading that entrypoint");
			entrypoint = null;
		}
		
		//load, but do not yet construct, the mod entrypoint
		if(entrypoint == null) modClass = null;
		else {
			Consts.LOG.debug("Loading entrypoint class {}", entrypoint);
			try {
				modClass = Objects.requireNonNull(Class.forName(module, entrypoint));
			} catch (Exception e) {
				throw Globals.mkRethrow(e, "Failed to load redmill entrypoint class " + entrypoint);
			}
		}
	}
	
	private final @Nullable Class<?> modClass;
	
	@Override
	public @Nullable IEventBus getEventBus() {
		return null;
	}
	
	@Override
	protected void constructMod() {
		if(modClass == null) return;
		
		Consts.LOG.debug("Constructing mod class {}", modClass);
		
		try {
			modClass.getConstructor().newInstance();
		} catch (Exception e) {
			throw Globals.mkRethrow(e, "Failed to construct redmill entrypoint " + modClass);
		}
	}
}
