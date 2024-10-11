package agency.highlysuspect.redmill.languageloader;

import agency.highlysuspect.redmill.CheekyGlobalState;
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
		ModContainerExt ext = CheekyGlobalState.getModContainerByNewId(info.getModId());
		if(ext == null) {
			throw new IllegalStateException("No ModContainerExt for new modid " + info.getModId());
		}
		
		CheekyGlobalState.associateWithModInfo(ext, info);
		CheekyGlobalState.associateWithModContainer(ext, this);
		
		//tell RedmillLaunchPluginService about the classes in this jar
		CheekyGlobalState.classesToBeMilled(modFileScanData);
		
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
		
		if(!CheekyGlobalState.CFG.initMods) {
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
				throw new RuntimeException("Failed to load redmill entrypoint class " + entrypoint, e);
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
			throw new RuntimeException("Failed to construct redmill entrypoint " + modClass, e);
		}
	}
}
