package agency.highlysuspect.redmill.launchplugin;

import agency.highlysuspect.redmill.Globals;
import agency.highlysuspect.redmill.Consts;
import agency.highlysuspect.redmill.jarmetadata.RedmillJarMetadata;
import agency.highlysuspect.redmill.transformer.ClassProcessor;
import agency.highlysuspect.redmill.transformer.McpRemappingClassProcessor;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

public class RedmillLaunchPluginService implements ILaunchPluginService {
	public RedmillLaunchPluginService() {
	}
	
	@Override
	public String name() {
		return Consts.REDMILL_LAUNCH_PLUGIN_SERVICE;
	}
	
	private static final EnumSet<Phase> NOPE = EnumSet.noneOf(Phase.class);
	private static final EnumSet<Phase> BEFORE = EnumSet.of(Phase.BEFORE);
	
	//private ClassProcessor processor;
	
	@Override
	public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
		return Globals.TO_BE_MILLED.contains(classType) ? BEFORE : NOPE;
	}
	
	@Override
	public boolean processClass(Phase phase, ClassNode classNode, Type classType, String reason) {
		if(phase != Phase.BEFORE) return false;
		
		//TODO: cache the ClassProcessor somewhere!!!
		RedmillJarMetadata vanilla = Globals.minecraft147Meta;
		RedmillJarMetadata mod = Globals.getModContainerByClassInternalName(classNode.name).jarMetadata;
		
		ClassProcessor processor = ClassProcessor.composite(
			new McpRemappingClassProcessor(Globals.minecraft147Srg, RedmillJarMetadata.composite(vanilla, mod))
		);
		
		Consts.LOG.info("Got class: {} reason: {}", classType.getInternalName(), reason);
		
		processor.accept(classNode);
		
		//TODO make this an option something
		try {
			Path dumpDir = Paths.get(".").resolve("redmill-dump");
			
			String name = classType.getInternalName();
			int slash;
			while((slash = name.indexOf('/')) != -1) {
				dumpDir = dumpDir.resolve(name.substring(0, slash));
				name = name.substring(slash + 1);
			}
			
			Files.createDirectories(dumpDir);
			
			ClassWriter wow = new ClassWriter(0);
			classNode.accept(wow);
			Files.write(dumpDir.resolve(name + ".class"), wow.toByteArray());
		} catch (Exception e) {
			throw Globals.mkRethrow(e, "Failed to dump " + classType.getInternalName());
		}
		
		return true;
	}
}
