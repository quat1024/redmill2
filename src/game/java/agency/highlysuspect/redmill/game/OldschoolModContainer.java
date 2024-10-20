package agency.highlysuspect.redmill.game;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.LoadController;
import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.ModContainer;
import agency.highlysuspect.redmill.svc.ModContainerExt;
import com.google.common.eventbus.EventBus;
import net.neoforged.neoforgespi.language.IModInfo;

import java.io.File;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Set;

public class OldschoolModContainer implements ModContainer {
	public OldschoolModContainer(ModContainerExt ext) {
		this.ext = ext;
		this.modInfo = ext.modernModContainer.getModInfo();
	}
	
	public final ModContainerExt ext;
	public final IModInfo modInfo;
	
	@Override
	public String getModId() {
		return ext.oldModid;
	}
	
	@Override
	public String getName() {
		return modInfo.getDisplayName();
	}
	
	@Override
	public String getVersion() {
		return modInfo.getVersion().toString();
	}
	
	@Override
	public File getSource() {
		return modInfo.getOwningFile().getFile().getFilePath().toFile(); // file file file
	}
	
	@Override
	public void setEnabledState(boolean var1) {
	
	}
	
	@Override
	public Set getRequirements() {
		return Set.of();
	}
	
	@Override
	public List getDependencies() {
		return List.of();
	}
	
	@Override
	public List getDependants() {
		return List.of();
	}
	
	@Override
	public String getSortingRules() {
		return "";
	}
	
	@Override
	public boolean registerBus(EventBus var1, LoadController var2) {
		//TODO
		return false;
	}
	
	@Override
	public boolean matches(Object var1) {
		return false;
	}
	
	@Override
	public Object getMod() {
		return ext.modernModContainer.modInstance;
	}
	
	@Override
	public boolean isImmutable() {
		return false;
	}
	
	@Override
	public boolean isNetworkMod() {
		return false;
	}
	
	@Override
	public String getDisplayVersion() {
		return getVersion();
	}
	
	@Override
	public Certificate getSigningCertificate() {
		return null;
	}
}
