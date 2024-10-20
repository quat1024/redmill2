package agency.highlysuspect.redmill.svc.launchplugin;

import agency.highlysuspect.redmill.svc.Globals;
import agency.highlysuspect.redmill.svc.Consts;
import agency.highlysuspect.redmill.svc.ModContainerExt;
import agency.highlysuspect.redmill.svc.ModFileExt;
import agency.highlysuspect.redmill.svc.jarmetadata.RedmillJarMetadata;
import agency.highlysuspect.redmill.svc.languageloader.RedmillModContainer;
import agency.highlysuspect.redmill.svc.transformer.*;
import agency.highlysuspect.redmill.svc.util.StringInterner;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
		
		//make our jar processor
		List<RedmillJarMetadata> meta = new ArrayList<>(modFileExts.size() + 1);
		meta.add(Globals.minecraft147Meta);
		modFileExts.forEach(mfe -> meta.add(mfe.jarMetadata));
		processor = mkProcessor(meta);
		
		//early dump time!!!
		if(Globals.CFG.earlyDump) {
			new Thread(() -> {
				Collection<Path> modPaths = modFileExts.stream().map(mfe -> mfe.modernModFile.getFilePath()).toList();
				ProgressMeter meter = StartupNotificationManager.addProgressBar("(Red Mill) Early mod dump", modPaths.size());
				for(Path mod : modPaths) {
					processAndDumpJar(mod, processor);
					meter.increment();
				}
				meter.complete();
			}).start();
		}
	}
	
	private static ClassProcessor mkProcessor(Collection<RedmillJarMetadata> jarMetas) {
		RedmillJarMetadata meta = RedmillJarMetadata.composite(jarMetas.stream());
		
		return ClassProcessor.composite(
			new McpClassNamingProcessor(Globals.minecraft147Srg),
			new McpClassNamingProcessor(Globals.leftoversSrg),
			new McpFieldMethodClassProcessor(Globals.minecraft147Srg, meta),
		
			new FieldsToGettersAndSettersProcessor(meta),
			new ClassHierarchyBenderProcessor()
		);
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
	
	//TODO: for testing
	public static void processAndDumpJar(Path path, ClassProcessor processor) {
		ProgressMeter meter = null;
		
		try(ZipFile file = new ZipFile(path.toFile())) {
			//this is an overestimate, since it includes non-class files
			int count = file.size();
			
			meter = StartupNotificationManager.addProgressBar(path.getFileName().toString(), count);
			
			for(Iterator<? extends ZipEntry> it = file.entries().asIterator(); it.hasNext(); ) {
				ZipEntry entry = it.next();
				
				if(!entry.isDirectory() && entry.getName().endsWith(".class")) {
					ClassReader reader = new ClassReader(file.getInputStream(entry));
					ClassNode node = new ClassNode();
					reader.accept(node, 0);
					
					meter.label("Dumping " + node.name);
					
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
		} finally {
			if(meter != null)	meter.complete();
		}
	}
	
	private static void dumpClass(ClassNode node) {
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
	
	public static void processAndDumpFroge() {
		try {
			Path froge = Paths.get(".").resolve("redmill-dump").resolve("froge.jar");
			
			RedmillJarMetadata rmj = new RedmillJarMetadata(froge, new StringInterner());
			ClassProcessor processor = mkProcessor(List.of(Globals.minecraft147Meta, rmj));
			
			processAndDumpJar(froge, processor);
		} catch (Throwable e) {
			throw Globals.mkRethrow(e, "Failed to process and dump froge");
		}
	}
	
	static {
		processAndDumpFroge();
		//System.exit(621);
	}
}
