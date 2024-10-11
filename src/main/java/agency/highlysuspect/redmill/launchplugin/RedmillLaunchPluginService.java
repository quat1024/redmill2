package agency.highlysuspect.redmill.launchplugin;

import agency.highlysuspect.redmill.Globals;
import agency.highlysuspect.redmill.Consts;
import agency.highlysuspect.redmill.jarmetadata.RedmillJarMetadata;
import agency.highlysuspect.redmill.languageloader.RedmillModContainer;
import agency.highlysuspect.redmill.transformer.ClassProcessor;
import agency.highlysuspect.redmill.transformer.McpRemappingClassProcessor;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RedmillLaunchPluginService implements ILaunchPluginService {
	public RedmillLaunchPluginService() {
		if(INSTANCE != null) {
			throw new IllegalStateException("RedmillLaunchPluginService already exists");
		}
		
		INSTANCE = this;
	}
	
	public static RedmillLaunchPluginService INSTANCE;
	
	private static final EnumSet<Phase> NOPE = EnumSet.noneOf(Phase.class);
	private static final EnumSet<Phase> BEFORE = EnumSet.of(Phase.BEFORE);
	
	private ClassProcessor processor;
	private final Set<Type> toBeMilled = new HashSet<>();
	
	public void doneModLoading(Collection<RedmillModContainer> rmcs) {
		Consts.LOG.info("RedmillLaunchPluginService: got {} mods ({})",
			rmcs.size(),
			rmcs.stream().map(rmc -> rmc.modContainerExt.oldModid).collect(Collectors.joining(", ")));
		
		//list all the classes that need to be milled
		for(RedmillModContainer rmc : rmcs) {
			rmc.modFileScanData.getClasses().forEach(cd -> toBeMilled.add(cd.clazz()));
		}
		
		//aggregate all jar metadata
		RedmillJarMetadata compositeMetadata = RedmillJarMetadata.composite(Stream.concat(
			Stream.of(Globals.minecraft147Meta),
			rmcs.stream().map(rmc -> rmc.modFileExt).distinct().map(mfe -> mfe.jarMetadata)
		));
		
		//make our jar processor
		processor = ClassProcessor.composite(
			new McpRemappingClassProcessor(Globals.minecraft147Srg, compositeMetadata)
		);
	}
	
	@Override
	public String name() {
		return Consts.REDMILL_LAUNCH_PLUGIN_SERVICE;
	}
	
	@Override
	public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
		return toBeMilled.contains(classType) ? BEFORE : NOPE;
	}
	
	@Override
	public boolean processClass(Phase phase, ClassNode classNode, Type classType, String reason) {
		if(phase != Phase.BEFORE) return false;
		
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
