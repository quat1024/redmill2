//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.registry;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.FMLLog;
import com.google.common.base.Charsets;
import net.minecraft.client.resources.language.I18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

public class LanguageRegistry {
	private static final LanguageRegistry INSTANCE = new LanguageRegistry();
	private Map modLanguageData = new HashMap();
	
	public LanguageRegistry() {
	}
	
	public static LanguageRegistry instance() {
		return INSTANCE;
	}
	
	public String getStringLocalization(String var1) {
		return I18n.get(var1);
		//return this.getStringLocalization(var1, RStringTranslate.getInstance().getCurrentLanguage());
	}
	
	public String getStringLocalization(String var1, String var2) {
		String var3 = "";
		Properties var4 = (Properties)this.get_modLanguageData().get(var2);
		if (var4 != null && var4.getProperty(var1) != null) {
			var3 = var4.getProperty(var1);
		}
		
		return var3;
	}
	
	public void addStringLocalization(String var1, String var2) {
		this.addStringLocalization(var1, "en_US", var2);
	}
	
	public void addStringLocalization(String var1, String var2, String var3) {
		Properties var4 = (Properties)this.get_modLanguageData().get(var2);
		if (var4 == null) {
			var4 = new Properties();
			this.get_modLanguageData().put(var2, var4);
		}
		
		var4.put(var1, var3);
	}
	
	public void addStringLocalization(Properties var1) {
		this.addStringLocalization(var1, "en_US");
	}
	
	public void addStringLocalization(Properties var1, String var2) {
		Properties var3 = (Properties)this.get_modLanguageData().get(var2);
		if (var3 == null) {
			var3 = new Properties();
			this.get_modLanguageData().put(var2, var3);
		}
		
		if (var1 != null) {
			var3.putAll(var1);
		}
		
	}
	
	public static void reloadLanguageTable() {
		//RM-TODO
//		String var0 = RStringTranslate.getInstance().getCurrentLanguage();
//		RStringTranslate.getInstance().set_currentLanguage((String)null);
//		RStringTranslate.getInstance().setLanguage(var0);
	}
	
	public void addNameForObject(Object var1, String var2, String var3) {
		//RM-TODO
//		String var4;
//		if (var1 instanceof IItem) {
//			var4 = ((IItem)var1).getItemName();
//		} else if (var1 instanceof IBlock) {
//			var4 = ((IBlock)var1).getBlockName();
//		} else {
//			if (!(var1 instanceof IItemStack)) {
//				throw new IllegalArgumentException(String.format("Illegal object for naming %s", var1));
//			}
//
//			var4 = ((IItemStack)var1).getItem().getItemNameIS((IItemStack)var1);
//		}
//
//		var4 = var4 + ".name";
//		this.addStringLocalization(var4, var2, var3);
	}
	
	public static void addName(Object var0, String var1) {
		instance().addNameForObject(var0, "en_US", var1);
	}
	
	public void loadLanguageTable(Properties var1, String var2) {
		Properties var3 = (Properties)this.get_modLanguageData().get("en_US");
		if (var3 != null) {
			var1.putAll(var3);
		}
		
		Properties var4 = (Properties)this.get_modLanguageData().get(var2);
		if (var4 != null) {
			var1.putAll(var4);
		}
	}
	
	public void loadLocalization(String var1, String var2, boolean var3) {
		//RM-TODO
//		URL var4 = this.getClass().getResource(var1);
//		if (var4 != null) {
//			this.loadLocalization(var4, var2, var3);
//		} else {
//			ModContainer var5 = Loader.instance().activeModContainer();
//			if (var5 != null) {
//				FMLLog.log(var5.getModId(), Level.SEVERE, "The language resource %s cannot be located on the classpath. This is a programming error.", new Object[]{var1});
//			} else {
//				FMLLog.log(Level.SEVERE, "The language resource %s cannot be located on the classpath. This is a programming error.", new Object[]{var1});
//			}
//		}
	
	}
	
	public void loadLocalization(URL var1, String var2, boolean var3) {
		InputStream var4 = null;
		Properties var5 = new Properties();
		
		try {
			var4 = var1.openStream();
			if (var3) {
				var5.loadFromXML(var4);
			} else {
				var5.load(new InputStreamReader(var4, Charsets.UTF_8));
			}
			
			this.addStringLocalization(var5, var2);
		} catch (IOException var15) {
			FMLLog.log(Level.SEVERE, var15, "Unable to load localization from file %s", new Object[]{var1});
		} finally {
			try {
				if (var4 != null) {
					var4.close();
				}
			} catch (IOException var14) {
			}
			
		}
		
	}
	
	public static LanguageRegistry get_INSTANCE() {
		return INSTANCE;
	}
	
	public Map get_modLanguageData() {
		return this.modLanguageData;
	}
	
	public void set_modLanguageData(Map var1) {
		this.modLanguageData = var1;
	}
}
