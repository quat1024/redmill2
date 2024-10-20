//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.net.minecraftforge.common;

import com.google.common.base.Splitter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ConfigCategory implements Map<String, Property> {
	private String name;
	private String comment;
	private ArrayList children;
	private Map properties;
	public final ConfigCategory parent;
	
	public ConfigCategory(String var1) {
		this(var1, (ConfigCategory)null);
	}
	
	public ConfigCategory(String var1, ConfigCategory var2) {
		this.children = new ArrayList();
		this.properties = new TreeMap();
		this.name = var1;
		this.parent = var2;
		if (var2 != null) {
			var2.children.add(this);
		}
		
	}
	
	public boolean equals(Object var1) {
		if (!(var1 instanceof ConfigCategory)) {
			return false;
		} else {
			ConfigCategory var2 = (ConfigCategory)var1;
			return this.get_name().equals(var2.get_name()) && this.get_children().equals(var2.get_children());
		}
	}
	
	public String getQualifiedName() {
		return getQualifiedName(this.get_name(), this.get_parent());
	}
	
	public static String getQualifiedName(String var0, ConfigCategory var1) {
		return var1 == null ? var0 : var1.getQualifiedName() + "." + var0;
	}
	
	public ConfigCategory getFirstParent() {
		return this.get_parent() == null ? this : this.get_parent().getFirstParent();
	}
	
	public boolean isChild() {
		return this.get_parent() != null;
	}
	
	public Map getValues() {
		return this.get_properties();
	}
	
	public void setComment(String var1) {
		this.set_comment(var1);
	}
	
	public boolean containsKey(String var1) {
		return this.get_properties().containsKey(var1);
	}
	
	public Property get(String var1) {
		return (Property)this.get_properties().get(var1);
	}
	
	public void set(String var1, Property var2) {
		this.get_properties().put(var1, var2);
	}
	
	public void write(BufferedWriter var1, int var2) throws IOException {
		String var3 = this.getIndent(var2);
		var1.write(var3 + "####################" + Configuration.get_NEW_LINE());
		var1.write(var3 + "# " + this.get_name() + Configuration.get_NEW_LINE());
		Iterator var5;
		if (this.get_comment() != null) {
			var1.write(var3 + "#===================" + Configuration.get_NEW_LINE());
			Splitter var4 = Splitter.onPattern("\r?\n");
			var5 = var4.split(this.get_comment()).iterator();
			
			while(var5.hasNext()) {
				String var6 = (String)var5.next();
				var1.write(var3 + "# " + var6 + Configuration.get_NEW_LINE());
			}
		}
		
		var1.write(var3 + "####################" + Configuration.get_NEW_LINE() + Configuration.get_NEW_LINE());
		if (!Configuration.get_allowedProperties().matchesAllOf(this.get_name())) {
			this.set_name('"' + this.get_name() + '"');
		}
		
		var1.write(var3 + this.get_name() + " {" + Configuration.get_NEW_LINE());
		var3 = this.getIndent(var2 + 1);
		Property[] var12 = (Property[])this.get_properties().values().toArray(new Property[this.get_properties().size()]);
		
		for(int var13 = 0; var13 < var12.length; ++var13) {
			Property var14 = var12[var13];
			if (var14.get_comment() != null) {
				if (var13 != 0) {
					var1.newLine();
				}
				
				Splitter var7 = Splitter.onPattern("\r?\n");
				Iterator var8 = var7.split(var14.get_comment()).iterator();
				
				while(var8.hasNext()) {
					String var9 = (String)var8.next();
					var1.write(var3 + "# " + var9 + Configuration.get_NEW_LINE());
				}
			}
			
			String var16 = var14.getName();
			if (!Configuration.get_allowedProperties().matchesAllOf(var16)) {
				var16 = '"' + var16 + '"';
			}
			
			if (!var14.isList()) {
				if (var14.getType() == null) {
					var1.write(String.format(var3 + "%s=%s" + Configuration.get_NEW_LINE(), var16, var14.get_value()));
				} else {
					var1.write(String.format(var3 + "%s:%s=%s" + Configuration.get_NEW_LINE(), var14.getType().getID(), var16, var14.get_value()));
				}
			} else {
				var1.write(String.format(var3 + "%s:%s <" + Configuration.get_NEW_LINE(), var14.getType().getID(), var16));
				var3 = this.getIndent(var2 + 2);
				String[] var17 = var14.get_valueList();
				int var18 = var17.length;
				
				for(int var10 = 0; var10 < var18; ++var10) {
					String var11 = var17[var10];
					var1.write(var3 + var11 + Configuration.get_NEW_LINE());
				}
				
				var1.write(this.getIndent(var2 + 1) + " >" + Configuration.get_NEW_LINE());
			}
		}
		
		var5 = this.get_children().iterator();
		
		while(var5.hasNext()) {
			ConfigCategory var15 = (ConfigCategory)var5.next();
			var15.write(var1, var2 + 1);
		}
		
		var1.write(this.getIndent(var2) + "}" + Configuration.get_NEW_LINE() + Configuration.get_NEW_LINE());
	}
	
	private String getIndent(int var1) {
		StringBuilder var2 = new StringBuilder("");
		
		for(int var3 = 0; var3 < var1; ++var3) {
			var2.append("    ");
		}
		
		return var2.toString();
	}
	
	public int size() {
		return this.get_properties().size();
	}
	
	public boolean isEmpty() {
		return this.get_properties().isEmpty();
	}
	
	public boolean containsKey(Object var1) {
		return this.get_properties().containsKey(var1);
	}
	
	public boolean containsValue(Object var1) {
		return this.get_properties().containsValue(var1);
	}
	
	public Property get(Object var1) {
		return (Property)this.get_properties().get(var1);
	}
	
	public Property put(String var1, Property var2) {
		return (Property)this.get_properties().put(var1, var2);
	}
	
	public Property remove(Object var1) {
		return (Property)this.get_properties().remove(var1);
	}
	
	public void putAll(Map var1) {
		this.get_properties().putAll(var1);
	}
	
	public void clear() {
		this.get_properties().clear();
	}
	
	public Set keySet() {
		return this.get_properties().keySet();
	}
	
	public Collection values() {
		return this.get_properties().values();
	}
	
	public Set entrySet() {
		return this.get_properties().entrySet();
	}
	
	public String get_name() {
		return this.name;
	}
	
	public void set_name(String var1) {
		this.name = var1;
	}
	
	public String get_comment() {
		return this.comment;
	}
	
	public void set_comment(String var1) {
		this.comment = var1;
	}
	
	public ArrayList get_children() {
		return this.children;
	}
	
	public void set_children(ArrayList var1) {
		this.children = var1;
	}
	
	public Map get_properties() {
		return this.properties;
	}
	
	public void set_properties(Map var1) {
		this.properties = var1;
	}
	
	public ConfigCategory get_parent() {
		return this.parent;
	}
}
