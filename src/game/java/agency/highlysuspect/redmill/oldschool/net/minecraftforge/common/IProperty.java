package agency.highlysuspect.redmill.oldschool.net.minecraftforge.common;

public interface IProperty {
	int getInt();
	
	int getInt(int var1);
	
	boolean isIntValue();
	
	boolean getBoolean(boolean var1);
	
	boolean isBooleanValue();
	
	boolean isDoubleValue();
	
	double getDouble(double var1);
	
	int[] getIntList();
	
	boolean isIntList();
	
	boolean[] getBooleanList();
	
	boolean isBooleanList();
	
	double[] getDoubleList();
	
	boolean isDoubleList();
	
	String getName();
	
	void setName(String var1);
	
	boolean wasRead();
	
	IProperty.IType getType();
	
	boolean isList();
	
	String get_name();
	
	void set_name(String var1);
	
	String get_value();
	
	void set_value(String var1);
	
	String get_comment();
	
	void set_comment(String var1);
	
	String[] get_valueList();
	
	void set_valueList(String[] var1);
	
	boolean get_wasRead();
	
	boolean get_isList();
	
	IProperty.IType get_type();
	
	interface IType {
		String name();
		char getID();
	}
}
