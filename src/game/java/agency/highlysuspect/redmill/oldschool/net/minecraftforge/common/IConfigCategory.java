package agency.highlysuspect.redmill.oldschool.net.minecraftforge.common;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IConfigCategory extends Map<String, IProperty> {
	boolean equals(Object var1);
	
	String getQualifiedName();
	
	IConfigCategory getFirstParent();
	
	boolean isChild();
	
	Map<String, IProperty> getValues();
	
	void setComment(String var1);
	
	boolean containsKey(String var1);
	
	IProperty get(String var1);
	
	void set(String var1, IProperty var2);
	
	void write(BufferedWriter var1, int var2) throws IOException;
	
	int size();
	
	boolean isEmpty();
	
	boolean containsKey(Object var1);
	
	boolean containsValue(Object var1);
	
	IProperty get(Object var1);
	
	IProperty put(String var1, IProperty var2);
	
	IProperty remove(Object var1);
	
	void putAll(Map<? extends String, ? extends IProperty> var1);
	
	void clear();
	
	Set<String> keySet();
	
	Collection<IProperty> values();
	
	Set<Map.Entry<String, IProperty>> entrySet();
	
	String get_name();
	
	void set_name(String var1);
	
	String get_comment();
	
	void set_comment(String var1);
	
	ArrayList<IConfigCategory> get_children();
	
	void set_children(ArrayList<IConfigCategory> var1);
	
	Map<String, IProperty> get_properties();
	
	void set_properties(Map<String, IProperty> var1);
	
	IConfigCategory get_parent();
}
