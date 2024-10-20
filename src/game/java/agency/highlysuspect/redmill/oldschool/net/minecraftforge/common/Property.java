//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.net.minecraftforge.common;

import java.util.ArrayList;

public class Property {
	private String name;
	public String value;
	public String comment;
	public String[] valueList;
	private final boolean wasRead;
	private final boolean isList;
	private final Type type;
	
	public Property() {
		this.wasRead = false;
		this.type = null;
		this.isList = false;
	}
	
	public Property(String var1, String var2, Type var3) {
		this(var1, var2, var3, false);
	}
	
	Property(String var1, String var2, Type var3, boolean var4) {
		this.setName(var1);
		this.value = var2;
		this.type = var3;
		this.wasRead = var4;
		this.isList = false;
	}
	
	public Property(String var1, String[] var2, Type var3) {
		this(var1, var2, var3, false);
	}
	
	Property(String var1, String[] var2, Type var3, boolean var4) {
		this.setName(var1);
		this.type = var3;
		this.valueList = var2;
		this.wasRead = var4;
		this.isList = true;
	}
	
	public int getInt() {
		return this.getInt(-1);
	}
	
	public int getInt(int var1) {
		try {
			return Integer.parseInt(this.get_value());
		} catch (NumberFormatException var3) {
			return var1;
		}
	}
	
	public boolean isIntValue() {
		try {
			Integer.parseInt(this.get_value());
			return true;
		} catch (NumberFormatException var2) {
			return false;
		}
	}
	
	public boolean getBoolean(boolean var1) {
		return this.isBooleanValue() ? Boolean.parseBoolean(this.get_value()) : var1;
	}
	
	public boolean isBooleanValue() {
		return "true".equals(this.get_value().toLowerCase()) || "false".equals(this.get_value().toLowerCase());
	}
	
	public boolean isDoubleValue() {
		try {
			Double.parseDouble(this.get_value());
			return true;
		} catch (NumberFormatException var2) {
			return false;
		}
	}
	
	public double getDouble(double var1) {
		try {
			return Double.parseDouble(this.get_value());
		} catch (NumberFormatException var4) {
			return var1;
		}
	}
	
	public int[] getIntList() {
		ArrayList var1 = new ArrayList();
		String[] var2 = this.get_valueList();
		int var3 = var2.length;
		
		for(int var4 = 0; var4 < var3; ++var4) {
			String var5 = var2[var4];
			
			try {
				var1.add(Integer.parseInt(var5));
			} catch (NumberFormatException var7) {
			}
		}
		
		int[] var8 = new int[var1.size()];
		
		for(var3 = 0; var3 < var1.size(); ++var3) {
			var8[var3] = (Integer)var1.get(var3);
		}
		
		return var8;
	}
	
	public boolean isIntList() {
		String[] var1 = this.get_valueList();
		int var2 = var1.length;
		
		for(int var3 = 0; var3 < var2; ++var3) {
			String var4 = var1[var3];
			
			try {
				Integer.parseInt(var4);
			} catch (NumberFormatException var6) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean[] getBooleanList() {
		ArrayList var1 = new ArrayList();
		String[] var2 = this.get_valueList();
		int var3 = var2.length;
		
		for(int var4 = 0; var4 < var3; ++var4) {
			String var5 = var2[var4];
			
			try {
				var1.add(Boolean.parseBoolean(var5));
			} catch (NumberFormatException var7) {
			}
		}
		
		boolean[] var8 = new boolean[var1.size()];
		
		for(var3 = 0; var3 < var1.size(); ++var3) {
			var8[var3] = (Boolean)var1.get(var3);
		}
		
		return var8;
	}
	
	public boolean isBooleanList() {
		String[] var1 = this.get_valueList();
		int var2 = var1.length;
		
		for(int var3 = 0; var3 < var2; ++var3) {
			String var4 = var1[var3];
			if (!"true".equalsIgnoreCase(var4) && !"false".equalsIgnoreCase(var4)) {
				return false;
			}
		}
		
		return true;
	}
	
	public double[] getDoubleList() {
		ArrayList var1 = new ArrayList();
		String[] var2 = this.get_valueList();
		int var3 = var2.length;
		
		for(int var4 = 0; var4 < var3; ++var4) {
			String var5 = var2[var4];
			
			try {
				var1.add(Double.parseDouble(var5));
			} catch (NumberFormatException var7) {
			}
		}
		
		double[] var8 = new double[var1.size()];
		
		for(var3 = 0; var3 < var1.size(); ++var3) {
			var8[var3] = (Double)var1.get(var3);
		}
		
		return var8;
	}
	
	public boolean isDoubleList() {
		String[] var1 = this.get_valueList();
		int var2 = var1.length;
		
		for(int var3 = 0; var3 < var2; ++var3) {
			String var4 = var1[var3];
			
			try {
				Double.parseDouble(var4);
			} catch (NumberFormatException var6) {
				return false;
			}
		}
		
		return true;
	}
	
	public String getName() {
		return this.get_name();
	}
	
	public void setName(String var1) {
		this.set_name(var1);
	}
	
	public boolean wasRead() {
		return this.get_wasRead();
	}
	
	public Type getType() {
		return this.get_type();
	}
	
	public boolean isList() {
		return this.get_isList();
	}
	
	public String get_name() {
		return this.name;
	}
	
	public void set_name(String var1) {
		this.name = var1;
	}
	
	public String get_value() {
		return this.value;
	}
	
	public void set_value(String var1) {
		this.value = var1;
	}
	
	public String get_comment() {
		return this.comment;
	}
	
	public void set_comment(String var1) {
		this.comment = var1;
	}
	
	public String[] get_valueList() {
		return this.valueList;
	}
	
	public void set_valueList(String[] var1) {
		this.valueList = var1;
	}
	
	public boolean get_wasRead() {
		return this.wasRead;
	}
	
	public boolean get_isList() {
		return this.isList;
	}
	
	public Type get_type() {
		return this.type;
	}
	
	public enum Type {
		STRING,
		INTEGER,
		BOOLEAN,
		DOUBLE;
		
		private static Type[] values = values();
		
		private Type() {
		}
		
		public static Type tryParse(char var0) {
			for(int var1 = 0; var1 < values.length; ++var1) {
				if (values[var1].getID() == var0) {
					return values[var1];
				}
			}
			
			return STRING;
		}
		
		public char getID() {
			return this.name().charAt(0);
		}
		
		public static Type get_STRING() {
			return STRING;
		}
		
		public static Type get_INTEGER() {
			return INTEGER;
		}
		
		public static Type get_BOOLEAN() {
			return BOOLEAN;
		}
		
		public static Type get_DOUBLE() {
			return DOUBLE;
		}
		
		public static Type[] get_values() {
			return values;
		}
		
		public static void set_values(Type[] var0) {
			values = var0;
		}
	}
}
