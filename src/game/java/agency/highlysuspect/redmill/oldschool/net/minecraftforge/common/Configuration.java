//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.net.minecraftforge.common;

import agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common.FMLLog;
import agency.highlysuspect.redmill.oldschool.net.minecraftforge.common.Property.Type;
import com.google.common.base.CharMatcher;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Configuration {
	//private static boolean[] configMarkers = new boolean[RItem.get_itemsList().length]; //RM-TODO
	private static final int ITEM_SHIFT = 256;
	private static final int MAX_BLOCKS = 4096;
	public static final String CATEGORY_GENERAL = "general";
	public static final String CATEGORY_BLOCK = "block";
	public static final String CATEGORY_ITEM = "item";
	public static final String ALLOWED_CHARS = "._-";
	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final String CATEGORY_SPLITTER = ".";
	public static final String NEW_LINE;
	private static final Pattern CONFIG_START = Pattern.compile("START: \"([^\\\"]+)\"");
	private static final Pattern CONFIG_END = Pattern.compile("END: \"([^\\\"]+)\"");
	public static final CharMatcher allowedProperties;
	private static Configuration PARENT;
	File file;
	public Map categories;
	private Map children;
	private boolean caseSensitiveCustomCategories;
	public String defaultEncoding;
	private String fileName;
	public boolean isChild;
	
	public Configuration() {
		this.categories = new TreeMap();
		this.children = new TreeMap();
		this.defaultEncoding = "UTF-8";
		this.fileName = null;
		this.isChild = false;
	}
	
	public Configuration(File var1) {
		this.categories = new TreeMap();
		this.children = new TreeMap();
		this.defaultEncoding = "UTF-8";
		this.fileName = null;
		this.isChild = false;
		this.file = var1;
		
		//RM-TODO
//		String var2 = ((File)((File)FMLInjectionData.data()[6])).getAbsolutePath().replace(File.separatorChar, '/').replace("/.", "");
//		String var3 = var1.getAbsolutePath().replace(File.separatorChar, '/').replace("/./", "/").replace(var2, "");
//		if (PARENT != null) {
//			PARENT.setChild(var3, this);
//			this.isChild = true;
//		} else {
//			this.fileName = var3;
//			this.load();
//		}
		
		this.fileName = var1.getName();
		this.load();
	}
	
	public Configuration(File var1, boolean var2) {
		this(var1);
		this.caseSensitiveCustomCategories = var2;
	}
	
	public Property getBlock(String var1, int var2) {
		return this.getBlock("block", var1, var2, (String)null);
	}
	
	public Property getBlock(String var1, int var2, String var3) {
		return this.getBlock("block", var1, var2, var3);
	}
	
	public Property getBlock(String var1, String var2, int var3) {
		throw new UnsupportedOperationException("getBlock not implemented");
//		return this.getBlockInternal(var1, var2, var3, (String)null, 256, RBlock.get_blocksList().length);
	}
	
	public Property getBlock(String var1, String var2, int var3, String var4) {
		throw new UnsupportedOperationException("getBlock not implemented");
//		return this.getBlockInternal(var1, var2, var3, var4, 256, RBlock.get_blocksList().length);
	}
	
	public Property getTerrainBlock(String var1, String var2, int var3, String var4) {
		return this.getBlockInternal(var1, var2, var3, var4, 0, 256);
	}
	
	private Property getBlockInternal(String var1, String var2, int var3, String var4, int var5, int var6) {
		throw new UnsupportedOperationException("getBlockInternal not implemented");
//		Property var7 = this.get(var1, var2, -1, var4);
//		if (var7.getInt() != -1) {
//			get_configMarkers()[var7.getInt()] = true;
//			return var7;
//		} else {
//			if (var3 < var5) {
//				FMLLog.warning("Mod attempted to get a block ID with a default in the Terrain Generation section, mod authors should make sure there defaults are above 256 unless explicitly needed for terrain generation. Most ores do not need to be below 256.", new Object[0]);
//				FMLLog.warning("Config \"%s\" Category: \"%s\" Key: \"%s\" Default: %d", new Object[]{this.get_fileName(), var1, var2, var3});
//				var3 = var6 - 1;
//			}
//
//			if (RBlock.get_blocksList()[var3] == null && !get_configMarkers()[var3]) {
//				var7.set_value(Integer.toString(var3));
//				get_configMarkers()[var3] = true;
//				return var7;
//			} else {
//				for(int var8 = var6 - 1; var8 > 0; --var8) {
//					if (RBlock.get_blocksList()[var8] == null && !get_configMarkers()[var8]) {
//						var7.set_value(Integer.toString(var8));
//						get_configMarkers()[var8] = true;
//						return var7;
//					}
//				}
//
//				throw new RuntimeException("No more block ids available for " + var2);
//			}
//		}
	}
	
	public Property getItem(String var1, int var2) {
		return this.getItem("item", var1, var2, (String)null);
	}
	
	public Property getItem(String var1, int var2, String var3) {
		return this.getItem("item", var1, var2, var3);
	}
	
	public Property getItem(String var1, String var2, int var3) {
		return this.getItem(var1, var2, var3, (String)null);
	}
	
	public Property getItem(String var1, String var2, int var3, String var4) {
		throw new UnsupportedOperationException("getItem not implemented");
//		Property var5 = this.get(var1, var2, -1, var4);
//		int var6 = var3 + 256;
//		if (var5.getInt() != -1) {
//			get_configMarkers()[var5.getInt() + 256] = true;
//			return var5;
//		} else {
//			if (var3 < 3840) {
//				FMLLog.warning("Mod attempted to get a item ID with a default value in the block ID section, mod authors should make sure there defaults are above %d unless explicitly needed so that all block ids are free to store blocks.", new Object[]{3840});
//				FMLLog.warning("Config \"%s\" Category: \"%s\" Key: \"%s\" Default: %d", new Object[]{this.get_fileName(), var1, var2, var3});
//			}
//
//			if (RItem.get_itemsList()[var6] == null && !get_configMarkers()[var6] && var6 >= RBlock.get_blocksList().length) {
//				var5.set_value(Integer.toString(var3));
//				get_configMarkers()[var6] = true;
//				return var5;
//			} else {
//				for(int var7 = RItem.get_itemsList().length - 1; var7 >= 256; --var7) {
//					if (RItem.get_itemsList()[var7] == null && !get_configMarkers()[var7]) {
//						var5.set_value(Integer.toString(var7 - 256));
//						get_configMarkers()[var7] = true;
//						return var5;
//					}
//				}
//
//				throw new RuntimeException("No more item ids available for " + var2);
//			}
//		}
	}
	
	public Property get(String var1, String var2, int var3) {
		return this.get(var1, var2, var3, (String)null);
	}
	
	public Property get(String var1, String var2, int var3, String var4) {
		Property var5 = this.get(var1, var2, Integer.toString(var3), var4, Type.INTEGER);
		if (!var5.isIntValue()) {
			var5.set_value(Integer.toString(var3));
		}
		
		return var5;
	}
	
	public Property get(String var1, String var2, boolean var3) {
		return this.get(var1, var2, var3, (String)null);
	}
	
	public Property get(String var1, String var2, boolean var3, String var4) {
		Property var5 = this.get(var1, var2, Boolean.toString(var3), var4, Type.BOOLEAN);
		if (!var5.isBooleanValue()) {
			var5.set_value(Boolean.toString(var3));
		}
		
		return var5;
	}
	
	public Property get(String var1, String var2, double var3) {
		return this.get(var1, var2, var3, (String)null);
	}
	
	public Property get(String var1, String var2, double var3, String var5) {
		Property var6 = this.get(var1, var2, Double.toString(var3), var5, Type.DOUBLE);
		if (!var6.isDoubleValue()) {
			var6.set_value(Double.toString(var3));
		}
		
		return var6;
	}
	
	public Property get(String var1, String var2, String var3) {
		return this.get(var1, var2, (String)var3, (String)null);
	}
	
	public Property get(String var1, String var2, String var3, String var4) {
		return this.get(var1, var2, var3, var4, Type.STRING);
	}
	
	public Property get(String var1, String var2, String[] var3) {
		return this.get(var1, var2, (String[])var3, (String)null);
	}
	
	public Property get(String var1, String var2, String[] var3, String var4) {
		return this.get(var1, var2, var3, var4, Type.STRING);
	}
	
	public Property get(String var1, String var2, int[] var3) {
		return this.get(var1, var2, (int[])var3, (String)null);
	}
	
	public Property get(String var1, String var2, int[] var3, String var4) {
		String[] var5 = new String[var3.length];
		
		for(int var6 = 0; var6 < var3.length; ++var6) {
			var5[var6] = Integer.toString(var3[var6]);
		}
		
		Property var7 = this.get(var1, var2, var5, var4, Type.INTEGER);
		if (!var7.isIntList()) {
			var7.set_valueList(var5);
		}
		
		return var7;
	}
	
	public Property get(String var1, String var2, double[] var3) {
		return this.get(var1, var2, (double[])var3, (String)null);
	}
	
	public Property get(String var1, String var2, double[] var3, String var4) {
		String[] var5 = new String[var3.length];
		
		for(int var6 = 0; var6 < var3.length; ++var6) {
			var5[var6] = Double.toString(var3[var6]);
		}
		
		Property var7 = this.get(var1, var2, var5, var4, Type.DOUBLE);
		if (!var7.isDoubleList()) {
			var7.set_valueList(var5);
		}
		
		return var7;
	}
	
	public Property get(String var1, String var2, boolean[] var3) {
		return this.get(var1, var2, (boolean[])var3, (String)null);
	}
	
	public Property get(String var1, String var2, boolean[] var3, String var4) {
		String[] var5 = new String[var3.length];
		
		for(int var6 = 0; var6 < var3.length; ++var6) {
			var5[var6] = Boolean.toString(var3[var6]);
		}
		
		Property var7 = this.get(var1, var2, var5, var4, Type.BOOLEAN);
		if (!var7.isBooleanList()) {
			var7.set_valueList(var5);
		}
		
		return var7;
	}
	
	public Property get(String var1, String var2, String var3, String var4, Property.Type var5) {
		if (!this.get_caseSensitiveCustomCategories()) {
			var1 = var1.toLowerCase(Locale.ENGLISH);
		}
		
		ConfigCategory var6 = this.getCategory(var1);
		Property var7;
		if (var6.containsKey(var2)) {
			var7 = var6.get(var2);
			if (var7.getType() == null) {
				var7 = new Property(var7.getName(), var7.get_value(), var5);
				var6.set(var2, var7);
			}
			
			var7.set_comment(var4);
			return var7;
		} else if (var3 != null) {
			var7 = new Property(var2, var3, var5);
			var6.set(var2, var7);
			var7.set_comment(var4);
			return var7;
		} else {
			return null;
		}
	}
	
	public Property get(String var1, String var2, String[] var3, String var4, Property.Type var5) {
		if (!this.get_caseSensitiveCustomCategories()) {
			var1 = var1.toLowerCase(Locale.ENGLISH);
		}
		
		ConfigCategory var6 = this.getCategory(var1);
		Property var7;
		if (var6.containsKey(var2)) {
			var7 = var6.get(var2);
			if (var7.getType() == null) {
				var7 = new Property(var7.getName(), var7.get_value(), var5);
				var6.set(var2, var7);
			}
			
			var7.set_comment(var4);
			return var7;
		} else if (var3 != null) {
			var7 = new Property(var2, var3, var5);
			var7.set_comment(var4);
			var6.set(var2, var7);
			return var7;
		} else {
			return null;
		}
	}
	
	public boolean hasCategory(String var1) {
		return this.get_categories().get(var1) != null;
	}
	
	public boolean hasKey(String var1, String var2) {
		ConfigCategory var3 = (ConfigCategory)this.get_categories().get(var1);
		return var3 != null && var3.containsKey(var2);
	}
	
	public void load() {
		if (get_PARENT() == null || get_PARENT() == this) {
			BufferedReader var1 = null;
			UnicodeInputStreamReader var2 = null;
			
			try {
				if (this.get_file().getParentFile() != null) {
					this.get_file().getParentFile().mkdirs();
				}
				
				if (!this.get_file().exists() && !this.get_file().createNewFile()) {
					return;
				}
				
				if (this.get_file().canRead()) {
					var2 = new UnicodeInputStreamReader(new FileInputStream(this.get_file()), this.get_defaultEncoding());
					this.set_defaultEncoding(var2.getEncoding());
					var1 = new BufferedReader(var2);
					ConfigCategory var4 = null;
					Property.Type var5 = null;
					ArrayList var6 = null;
					int var7 = 0;
					String var8 = null;
					
					while(true) {
						while(true) {
							++var7;
							String var3 = var1.readLine();
							if (var3 == null) {
								return;
							}
							
							Matcher var9 = get_CONFIG_START().matcher(var3);
							Matcher var10 = get_CONFIG_END().matcher(var3);
							if (var9.matches()) {
								this.set_fileName(var9.group(1));
								this.set_categories(new TreeMap());
							} else if (var10.matches()) {
								this.set_fileName(var10.group(1));
								Configuration var35 = new Configuration();
								var35.set_categories(this.get_categories());
								this.get_children().put(this.get_fileName(), var35);
							} else {
								int var11 = -1;
								int var12 = -1;
								boolean var13 = false;
								boolean var14 = false;
								
								for(int var15 = 0; var15 < var3.length() && !var13; ++var15) {
									if (Character.isLetterOrDigit(var3.charAt(var15)) || "._-".indexOf(var3.charAt(var15)) != -1 || var14 && var3.charAt(var15) != '"') {
										if (var11 == -1) {
											var11 = var15;
										}
										
										var12 = var15;
									} else if (!Character.isWhitespace(var3.charAt(var15))) {
										switch (var3.charAt(var15)) {
											case '"':
												if (var14) {
													var14 = false;
												}
												
												if (!var14 && var11 == -1) {
													var14 = true;
												}
												break;
											case '#':
												var13 = true;
												break;
											case ':':
												var5 = Type.tryParse(var3.substring(var11, var12 + 1).charAt(0));
												var12 = -1;
												var11 = -1;
												break;
											case '<':
												if (var6 != null) {
													throw new RuntimeException(String.format("Malformed list property \"%s:%d\"", this.get_fileName(), var7));
												}
												
												var8 = var3.substring(var11, var12 + 1);
												if (var4 == null) {
													throw new RuntimeException(String.format("'%s' has no scope in '%s:%d'", var8, this.get_fileName(), var7));
												}
												
												var6 = new ArrayList();
												var13 = true;
												break;
											case '=':
												var8 = var3.substring(var11, var12 + 1);
												if (var4 == null) {
													throw new RuntimeException(String.format("'%s' has no scope in '%s:%d'", var8, this.get_fileName(), var7));
												}
												
												Property var18 = new Property(var8, var3.substring(var15 + 1), var5, true);
												var15 = var3.length();
												var4.set(var8, var18);
												break;
											case '>':
												if (var6 == null) {
													throw new RuntimeException(String.format("Malformed list property \"%s:%d\"", this.get_fileName(), var7));
												}
												
												var4.set(var8, new Property(var8, (String[])var6.toArray(new String[var6.size()]), var5));
												var8 = null;
												var6 = null;
												var5 = null;
												break;
											case '{':
												var8 = var3.substring(var11, var12 + 1);
												String var16 = ConfigCategory.getQualifiedName(var8, var4);
												ConfigCategory var17 = (ConfigCategory)this.get_categories().get(var16);
												if (var17 == null) {
													var4 = new ConfigCategory(var8, var4);
													this.get_categories().put(var16, var4);
												} else {
													var4 = var17;
												}
												
												var8 = null;
												break;
											case '}':
												if (var4 == null) {
													throw new RuntimeException(String.format("Config file corrupt, attepted to close to many categories '%s:%d'", this.get_fileName(), var7));
												}
												
												var4 = var4.get_parent();
												break;
											default:
												throw new RuntimeException(String.format("Unknown character '%s' in '%s:%d'", var3.charAt(var15), this.get_fileName(), var7));
										}
									}
								}
								
								if (var14) {
									throw new RuntimeException(String.format("Unmatched quote in '%s:%d'", this.get_fileName(), var7));
								}
								
								if (var6 != null && !var13) {
									var6.add(var3.trim());
								}
							}
						}
					}
				}
			} catch (IOException var33) {
				var33.printStackTrace();
			} finally {
				if (var1 != null) {
					try {
						var1.close();
					} catch (IOException var32) {
					}
				}
				
				if (var2 != null) {
					try {
						var2.close();
					} catch (IOException var31) {
					}
				}
				
			}
			
		}
	}
	
	public void save() {
		if (get_PARENT() != null && get_PARENT() != this) {
			get_PARENT().save();
		} else {
			try {
				if (this.get_file().getParentFile() != null) {
					this.get_file().getParentFile().mkdirs();
				}
				
				if (!this.get_file().exists() && !this.get_file().createNewFile()) {
					return;
				}
				
				if (this.get_file().canWrite()) {
					FileOutputStream var1 = new FileOutputStream(this.get_file());
					BufferedWriter var2 = new BufferedWriter(new OutputStreamWriter(var1, this.get_defaultEncoding()));
					var2.write("# Configuration file" + get_NEW_LINE() + get_NEW_LINE());
					if (this.get_children().isEmpty()) {
						this.save(var2);
					} else {
						Iterator var3 = this.get_children().entrySet().iterator();
						
						while(var3.hasNext()) {
							Map.Entry var4 = (Map.Entry)var3.next();
							var2.write("START: \"" + (String)var4.getKey() + "\"" + get_NEW_LINE());
							((Configuration)var4.getValue()).save(var2);
							var2.write("END: \"" + (String)var4.getKey() + "\"" + get_NEW_LINE() + get_NEW_LINE());
						}
					}
					
					var2.close();
					var1.close();
				}
			} catch (IOException var5) {
				var5.printStackTrace();
			}
			
		}
	}
	
	private void save(BufferedWriter var1) throws IOException {
		Object[] var2 = this.get_categories().values().toArray();
		Object[] var3 = var2;
		int var4 = var2.length;
		
		for(int var5 = 0; var5 < var4; ++var5) {
			Object var6 = var3[var5];
			if (var6 instanceof TreeMap) {
				TreeMap var7 = (TreeMap)var6;
				ConfigCategory var8 = new ConfigCategory(this.get_file().getName());
				FMLLog.warning("Forge found a Treemap saved for Configuration file " + this.get_file().getName() + ", this is deprecated behaviour!", new Object[0]);
				Iterator var9 = var7.keySet().iterator();
				
				while(var9.hasNext()) {
					Object var10 = var9.next();
					FMLLog.warning("Converting Treemap to ConfigCategory, key: " + var10 + ", property value: " + ((Property)var7.get(var10)).get_value(), new Object[0]);
					var8.set((String)var10, (Property)var7.get(var10));
				}
				
				this.get_categories().values().remove(var6);
				this.get_categories().put(this.get_file().getName(), var8);
			}
		}
		
		Iterator var11 = this.get_categories().values().iterator();
		
		while(var11.hasNext()) {
			ConfigCategory var12 = (ConfigCategory)var11.next();
			if (!var12.isChild()) {
				var12.write(var1, 0);
				var1.newLine();
			}
		}
		
	}
	
	public ConfigCategory getCategory(String var1) {
		ConfigCategory var2 = (ConfigCategory)this.get_categories().get(var1);
		if (var2 == null) {
			if (var1.contains(".")) {
				String[] var3 = var1.split("\\.");
				ConfigCategory var4 = (ConfigCategory)this.get_categories().get(var3[0]);
				if (var4 == null) {
					var4 = new ConfigCategory(var3[0]);
					this.get_categories().put(var4.getQualifiedName(), var4);
				}
				
				for(int var5 = 1; var5 < var3.length; ++var5) {
					String var6 = ConfigCategory.getQualifiedName(var3[var5], var4);
					ConfigCategory var7 = (ConfigCategory)this.get_categories().get(var6);
					if (var7 == null) {
						var7 = new ConfigCategory(var3[var5], var4);
						this.get_categories().put(var6, var7);
					}
					
					var2 = var7;
					var4 = var7;
				}
			} else {
				var2 = new ConfigCategory(var1);
				this.get_categories().put(var1, var2);
			}
		}
		
		return var2;
	}
	
	public void addCustomCategoryComment(String var1, String var2) {
		if (!this.get_caseSensitiveCustomCategories()) {
			var1 = var1.toLowerCase(Locale.ENGLISH);
		}
		
		this.getCategory(var1).setComment(var2);
	}
	
	private void setChild(String var1, Configuration var2) {
		if (!this.get_children().containsKey(var1)) {
			this.get_children().put(var1, var2);
		} else {
			Configuration var3 = (Configuration)this.get_children().get(var1);
			var2.set_categories(var3.get_categories());
			var2.set_fileName(var3.get_fileName());
		}
		
	}
	
	public static void enableGlobalConfig() {
		//RM-TODO
//		PARENT = new Configuration(new File(Loader.instance().getConfigDir(), "global.cfg"));
//		PARENT.load();
	}
	
	static {
		allowedProperties = CharMatcher.javaLetterOrDigit().or(CharMatcher.anyOf("._-"));
		PARENT = null;
//		Arrays.fill(configMarkers, false); //RM-TODO
		NEW_LINE = System.getProperty("line.separator");
	}
	
	public static boolean[] get_configMarkers() {
		throw new UnsupportedOperationException("configMarkers not implemented");
	//	return configMarkers;
	}
	
	public static void set_configMarkers(boolean[] var0) {
		throw new UnsupportedOperationException("configMarkers not implemented");
//		configMarkers = var0;
	}
	
	public static int get_ITEM_SHIFT() {
		return ITEM_SHIFT;
	}
	
	public static int get_MAX_BLOCKS() {
		return MAX_BLOCKS;
	}
	
	public static String get_CATEGORY_GENERAL() {
		return CATEGORY_GENERAL;
	}
	
	public static String get_CATEGORY_BLOCK() {
		return CATEGORY_BLOCK;
	}
	
	public static String get_CATEGORY_ITEM() {
		return CATEGORY_ITEM;
	}
	
	public static String get_ALLOWED_CHARS() {
		return ALLOWED_CHARS;
	}
	
	public static String get_DEFAULT_ENCODING() {
		return DEFAULT_ENCODING;
	}
	
	public static String get_CATEGORY_SPLITTER() {
		return CATEGORY_SPLITTER;
	}
	
	public static String get_NEW_LINE() {
		return NEW_LINE;
	}
	
	public static Pattern get_CONFIG_START() {
		return CONFIG_START;
	}
	
	public static Pattern get_CONFIG_END() {
		return CONFIG_END;
	}
	
	public static CharMatcher get_allowedProperties() {
		return allowedProperties;
	}
	
	public static Configuration get_PARENT() {
		return PARENT;
	}
	
	public static void set_PARENT(Configuration var0) {
		PARENT = var0;
	}
	
	public File get_file() {
		return this.file;
	}
	
	public void set_file(File var1) {
		this.file = var1;
	}
	
	public Map get_categories() {
		return this.categories;
	}
	
	public void set_categories(Map var1) {
		this.categories = var1;
	}
	
	public Map get_children() {
		return this.children;
	}
	
	public void set_children(Map var1) {
		this.children = var1;
	}
	
	public boolean get_caseSensitiveCustomCategories() {
		return this.caseSensitiveCustomCategories;
	}
	
	public void set_caseSensitiveCustomCategories(boolean var1) {
		this.caseSensitiveCustomCategories = var1;
	}
	
	public String get_defaultEncoding() {
		return this.defaultEncoding;
	}
	
	public void set_defaultEncoding(String var1) {
		this.defaultEncoding = var1;
	}
	
	public String get_fileName() {
		return this.fileName;
	}
	
	public void set_fileName(String var1) {
		this.fileName = var1;
	}
	
	public boolean get_isChild() {
		return this.isChild;
	}
	
	public void set_isChild(boolean var1) {
		this.isChild = var1;
	}
	
	public static class UnicodeInputStreamReader extends Reader {
		private final InputStreamReader input;
		private final String defaultEnc;
		
		public UnicodeInputStreamReader(InputStream var1, String var2) throws IOException {
			this.defaultEnc = var2;
			String var3 = var2;
			byte[] var4 = new byte[4];
			PushbackInputStream var5 = new PushbackInputStream(var1, var4.length);
			int var6 = var5.read(var4, 0, var4.length);
			byte var7 = 0;
			int var8 = (var4[0] & 255) << 8 | var4[1] & 255;
			int var9 = var8 << 8 | var4[2] & 255;
			int var10 = var9 << 8 | var4[3] & 255;
			if (var9 == 15711167) {
				var3 = "UTF-8";
				var7 = 3;
			} else if (var8 == 65279) {
				var3 = "UTF-16BE";
				var7 = 2;
			} else if (var8 == 65534) {
				var3 = "UTF-16LE";
				var7 = 2;
			} else if (var10 == 65279) {
				var3 = "UTF-32BE";
				var7 = 4;
			} else if (var10 == -131072) {
				var3 = "UTF-32LE";
				var7 = 4;
			}
			
			if (var7 < var6) {
				var5.unread(var4, var7, var6 - var7);
			}
			
			this.input = new InputStreamReader(var5, var3);
		}
		
		public String getEncoding() {
			return this.get_input().getEncoding();
		}
		
		public int read(char[] var1, int var2, int var3) throws IOException {
			return this.get_input().read(var1, var2, var3);
		}
		
		public void close() throws IOException {
			this.get_input().close();
		}
		
		public InputStreamReader get_input() {
			return this.input;
		}
		
		public String get_defaultEnc() {
			return this.defaultEnc;
		}
	}
}
