package agency.highlysuspect.redmill.launchplugin;

import agency.highlysuspect.redmill.CheekyGlobalState;
import agency.highlysuspect.redmill.Consts;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

import java.util.EnumSet;

public class RedmillLaunchPluginService implements ILaunchPluginService {
	@Override
	public String name() {
		return Consts.REDMILL_LAUNCH_PLUGIN_SERVICE;
	}
	
	private static final EnumSet<Phase> NOPE = EnumSet.noneOf(Phase.class);
	private static final EnumSet<Phase> BEFORE = EnumSet.of(Phase.BEFORE);
	
	@Override
	public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
		return CheekyGlobalState.TO_BE_MILLED.contains(classType) ? BEFORE : NOPE;
	}
	
	@Override
	public boolean processClass(Phase phase, ClassNode classNode, Type classType, String reason) {
		if(phase != Phase.BEFORE) return false;
		
		Consts.LOG.info("Got class: {} reason: {}", classType.getInternalName(), reason);
		Consts.LOG.warn("The game will now probably crash");
		
		return true;
	}
}
