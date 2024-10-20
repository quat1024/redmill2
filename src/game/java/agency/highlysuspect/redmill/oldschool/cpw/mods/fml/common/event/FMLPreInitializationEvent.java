//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.event;

import agency.highlysuspect.redmill.game.logging.LegacyLogger;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.FMLLog;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.LoaderState;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.ModContainer;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.LoaderState.ModState;
import java.io.File;

public class FMLPreInitializationEvent extends FMLStateEvent {
//	private ModMetadata modMetadata;
	private File sourceFile;
	private File configurationDir;
	private File suggestedConfigFile;
//	private ASMDataTable asmData;
	private ModContainer modContainer;
	
	public FMLPreInitializationEvent(Object... var1) {
		super(var1);
//		this.asmData = (ASMDataTable)var1[0];
		this.configurationDir = (File)var1[1];
	}
	
	public LoaderState.ModState getModState() {
		return ModState.PREINITIALIZED;
	}
	
	public void applyModContainer(ModContainer var1) {
		this.set_modContainer(var1);
//		this.set_modMetadata(var1.getMetadata());
		this.set_sourceFile(var1.getSource());
		this.set_suggestedConfigFile(new File(this.get_configurationDir(), var1.getModId() + ".cfg"));
	}
	
	public File getSourceFile() {
		return this.get_sourceFile();
	}
	
//	public ModMetadata getModMetadata() {
//		return this.get_modMetadata();
//	}
	
	public File getModConfigurationDirectory() {
		return this.get_configurationDir();
	}
	
	public File getSuggestedConfigurationFile() {
		return this.get_suggestedConfigFile();
	}
	
//	public ASMDataTable getAsmData() {
//		return this.get_asmData();
//	}
//
//	public Properties getVersionProperties() {
//		return this.get_modContainer() instanceof FMLModContainer ? ((FMLModContainer)this.get_modContainer()).searchForVersionProperties() : null;
//	}
	
	public LegacyLogger getModLog() {
		LegacyLogger var1 = LegacyLogger.getLogger(this.get_modContainer().getModId());
		var1.setParent(FMLLog.getLogger());
		return var1;
	}
	
//	/** @deprecated */
//	@Deprecated
//	public Certificate[] getFMLSigningCertificates() {
//		CodeSource var1 = this.getClass().getClassLoader().getParent().getClass().getProtectionDomain().getCodeSource();
//		Certificate[] var2 = var1.getCertificates();
//		return var2 == null ? new Certificate[0] : var2;
//	}

//	public ModMetadata get_modMetadata() {
//		return this.modMetadata;
//	}
//
//	public void set_modMetadata(ModMetadata var1) {
//		this.modMetadata = var1;
//	}
	
	public File get_sourceFile() {
		return this.sourceFile;
	}
	
	public void set_sourceFile(File var1) {
		this.sourceFile = var1;
	}
	
	public File get_configurationDir() {
		return this.configurationDir;
	}
	
	public void set_configurationDir(File var1) {
		this.configurationDir = var1;
	}
	
	public File get_suggestedConfigFile() {
		return this.suggestedConfigFile;
	}
	
	public void set_suggestedConfigFile(File var1) {
		this.suggestedConfigFile = var1;
	}
	
//	public ASMDataTable get_asmData() {
//		return this.asmData;
//	}
//
//	public void set_asmData(ASMDataTable var1) {
//		this.asmData = var1;
//	}
	
	public ModContainer get_modContainer() {
		return this.modContainer;
	}
	
	public void set_modContainer(ModContainer var1) {
		this.modContainer = var1;
	}
}
