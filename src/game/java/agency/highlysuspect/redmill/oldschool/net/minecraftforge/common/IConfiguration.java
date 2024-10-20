package agency.highlysuspect.redmill.oldschool.net.minecraftforge.common;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.RFMLLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public interface IConfiguration {
	IProperty getBlock(String var1, int var2);
	
	IProperty getBlock(String var1, int var2, String var3);
	
	IProperty getBlock(String var1, String var2, int var3);
	
	IProperty getBlock(String var1, String var2, int var3, String var4);
	
	IProperty getTerrainBlock(String var1, String var2, int var3, String var4);
	
	IProperty getBlockInternal(String var1, String var2, int var3, String var4, int var5, int var6);
	
	IProperty getItem(String var1, int var2);
	
	IProperty getItem(String var1, int var2, String var3);
	
	IProperty getItem(String var1, String var2, int var3);
	
	IProperty getItem(String var1, String var2, int var3, String var4);
	
	IProperty get(String var1, String var2, int var3);
	
	IProperty get(String var1, String var2, int var3, String var4);
	
	IProperty get(String var1, String var2, boolean var3);
	
	IProperty get(String var1, String var2, boolean var3, String var4);
	
	IProperty get(String var1, String var2, double var3);
	
	IProperty get(String var1, String var2, double var3, String var5);
	
	IProperty get(String var1, String var2, String var3);
	
	IProperty get(String var1, String var2, String var3, String var4);
	
	IProperty get(String var1, String var2, String[] var3);
	
	IProperty get(String var1, String var2, String[] var3, String var4);
	
	IProperty get(String var1, String var2, int[] var3);
	
	IProperty get(String var1, String var2, int[] var3, String var4);
	
	IProperty get(String var1, String var2, double[] var3);
	
	IProperty get(String var1, String var2, double[] var3, String var4);
	
	IProperty get(String var1, String var2, boolean[] var3);
	
	IProperty get(String var1, String var2, boolean[] var3, String var4);
	
	IProperty get(String var1, String var2, String var3, String var4, IProperty.IType var5);
	
	IProperty get(String var1, String var2, String[] var3, String var4, IProperty.IType var5);
	
	boolean hasCategory(String var1);
	
	boolean hasKey(String var1, String var2);
	
	void load();
	
	void save();
	
	void save(BufferedWriter var1) throws IOException;
	
	IConfigCategory getCategory(String var1);
	
	void addCustomCategoryComment(String var1, String var2);
	
	void setChild(String var1, IConfiguration var2);
	
	File get_file();
	
	void set_file(File var1);
	
	Map get_categories();
	
	void set_categories(Map var1);
	
	Map get_children();
	
	void set_children(Map var1);
	
	boolean get_caseSensitiveCustomCategories();
	
	void set_caseSensitiveCustomCategories(boolean var1);
	
	String get_defaultEncoding();
	
	void set_defaultEncoding(String var1);
	
	String get_fileName();
	
	void set_fileName(String var1);
	
	boolean get_isChild();
	
	void set_isChild(boolean var1);
}
