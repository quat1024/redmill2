//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

import com.google.common.eventbus.EventBus;
import java.io.File;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Set;

public interface ModContainer {
	String getModId();
	
	String getName();
	
	String getVersion();
	
	File getSource();
	
//	ModMetadata getMetadata();
//
//	void bindMetadata(MetadataCollection var1);
	
	void setEnabledState(boolean var1);
	
	Set getRequirements();
	
	List getDependencies();
	
	List getDependants();
	
	String getSortingRules();
	
	boolean registerBus(EventBus var1, LoadController var2);
	
	boolean matches(Object var1);
	
	Object getMod();
	
//	ArtifactVersion getProcessedVersion();
	
	boolean isImmutable();
	
	boolean isNetworkMod();
	
	String getDisplayVersion();
	
//	VersionRange acceptableMinecraftVersionRange();
	
	Certificate getSigningCertificate();
}
