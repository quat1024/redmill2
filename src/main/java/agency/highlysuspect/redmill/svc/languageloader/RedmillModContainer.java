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
		
		Module redmill2Module = gameLayer.findModule("redmill2")
			.orElseThrow(() -> new RuntimeException("couldn't find redmill2 layer"));
		try {
			this.bastion = (IBastion) Objects.requireNonNull(
					Class.forName(redmill2Module, "agency.highlysuspect.redmill.game.Bastion")
				)
				.getConstructor()
				.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
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
	private final IBastion bastion;
	public ModFileScanData modFileScanData;
	
	@Nullable public Class<?> modClass;
	@Nullable public Object modInstance;
	
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
			
			//its preinniting time (preinits all over the place)
			bastion.preinitMod(this);
		}
	}
}
