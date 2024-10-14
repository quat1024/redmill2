package agency.highlysuspect.redmill.svc.launchplugin;

import agency.highlysuspect.redmill.svc.Globals;
import agency.highlysuspect.redmill.svc.Consts;
import agency.highlysuspect.redmill.svc.ModContainerExt;
import agency.highlysuspect.redmill.svc.ModFileExt;
import agency.highlysuspect.redmill.svc.jarmetadata.RedmillJarMetadata;
import agency.highlysuspect.redmill.svc.languageloader.RedmillModContainer;
import agency.highlysuspect.redmill.svc.transformer.ClassHierarchyBenderProcessor;
import agency.highlysuspect.redmill.svc.transformer.ClassProcessor;
import agency.highlysuspect.redmill.svc.transformer.FieldsToGettersAndSettersProcessor;
import agency.highlysuspect.redmill.svc.transformer.McpRemappingClassProcessor;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import net.neoforged.fml.loading.progress.ProgressMeter;
import net.neoforged.fml.loading.progress.StartupNotificationManager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
	private final Set<String> toBeMilled = new HashSet<>();
	
	public void doneModLoading(Collection<ModFileExt> modFileExts, Collection<ModContainerExt> modContainerExts) {
		List<RedmillModContainer> rmcs = modContainerExts.stream()
			.map(mce -> mce.modernModContainer)
			.toList();
		
		Consts.LOG.info("RedmillLaunchPluginService: got {} mod{} from {} file{} ({})",
			rmcs.size(),
			rmcs.size() == 1 ? "" : "s",
			modFileExts.size(),
			modFileExts.size() == 1 ? "" : "s",
			modContainerExts.stream().map(mce -> mce.oldModid).collect(Collectors.joining(", ")));
		
		//list all the classes that need to be milled
		modFileExts.forEach(mfe -> toBeMilled.addAll(mfe.jarMetadata.getClasses()));
		
		//aggregate all jar metadata
		RedmillJarMetadata compositeMetadata = RedmillJarMetadata.composite(Stream.concat(
			Stream.of(Globals.minecraft147Meta),
			modFileExts.stream().map(mfe -> mfe.jarMetadata)
		));
		
		//make our jar processor
		processor = ClassProcessor.composite(
			new McpRemappingClassProcessor(Globals.minecraft147Srg, compositeMetadata),
			new FieldsToGettersAndSettersProcessor(),
			new ClassHierarchyBenderProcessor()
		);
		
		//early dump time!!!
		if(Globals.CFG.earlyDump) {
			new Thread(() -> {
				ProgressMeter meter = StartupNotificationManager.addProgressBar("(Red Mill) Early dump", toBeMilled.size());
				
				Collection<Path> modPaths = modFileExts.stream().map(mfe -> mfe.modernModFile.getFilePath()).toList();
				
				//TODO
				modPaths = new ArrayList<>(modPaths);
				modPaths.add(Paths.get("./redmill-dump/froge.jar"));
				
				for(Path path : modPaths) {
					try(ZipInputStream zin = new ZipInputStream(Files.newInputStream(path))) {
						ZipEntry entry;
						while((entry = zin.getNextEntry()) != null) {
							if(!entry.isDirectory() && entry.getName().endsWith(".class")) {
								ClassReader reader = new ClassReader(zin);
								ClassNode node = new ClassNode();
								reader.accept(node, 0);
								
								String internalName = node.name;
								meter.label(internalName);
								
								try {
									processor.accept(node);
									dumpClass(node);
								} catch (Exception e) {
									Consts.LOG.warn(e);
								}
								
								meter.increment();
							}
						}
					} catch (Exception e) {
						Consts.LOG.warn(e);
					}
				}
				
				meter.complete();
			}).start();
		}
	}
	
	@Override
	public String name() {
		return Consts.REDMILL_LAUNCH_PLUGIN_SERVICE;
	}
	
	@Override
	public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
		return toBeMilled.contains(classType.getInternalName()) ? BEFORE : NOPE;
	}
	
	@Override
	public boolean processClass(Phase phase, ClassNode classNode, Type classType, String reason) {
		if(phase != Phase.BEFORE) return false;
		
		Consts.LOG.debug("Got class: {} reason: {}", classType.getInternalName(), reason);
		
		processor.accept(classNode);
		
		if(Globals.CFG.dumpClasses) {
			dumpClass(classNode);
		}
		
		//returns whether COMPUTE_FRAMES is needed... yeah just assume it is
		return true;
	}
	
	private void dumpClass(ClassNode node) {
		try {
			Path dumpDir = Paths.get(".").resolve("redmill-dump").resolve("classes");
			
			String name = node.name;
			int slash;
			while((slash = name.indexOf('/')) != -1) {
				String part = name.substring(0, slash);
				if(part.equals("..")) part = "dotdot"; //nice try
				dumpDir = dumpDir.resolve(part);
				name = name.substring(slash + 1);
			}
			
			Files.createDirectories(dumpDir);
			
			ClassWriter wow = new ClassWriter(0);
			node.accept(wow);
			Files.write(dumpDir.resolve(name + ".class"), wow.toByteArray());
		} catch (Exception e) {
			throw Globals.mkRethrow(e, "Failed to dump " + node.name);
		}
	}
}
