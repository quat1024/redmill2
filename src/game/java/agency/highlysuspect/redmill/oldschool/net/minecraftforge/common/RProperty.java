//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.net.minecraftforge.common;

import java.util.ArrayList;

public class RProperty implements IProperty {
	private String name;
	public String value;
	public String comment;
	public String[] valueList;
	private final boolean wasRead;
	private final boolean isList;
	private final IProperty.IType type;
	
	public RProperty() {
		this.wasRead = false;
		this.type = null;
		this.isList = false;
	}
	
	public RProperty(String var1, String var2, IProperty.IType var3) {
		this(var1, var2, var3, false);
	}
	
	RProperty(String var1, String var2, IProperty.IType var3, boolean var4) {
		this.setName(var1);
		this.value = var2;
		this.type = var3;
		this.wasRead = var4;
		this.isList = false;
	}
	
	public RProperty(String var1, String[] var2, IProperty.IType var3) {
		this(var1, var2, var3, false);
	}
	
	RProperty(String var1, String[] var2, IProperty.IType var3, boolean var4) {
		this.setName(var1);
		this.type = var3;
		this.valueList = var2;
		this.wasRead = var4;
		this.isList = true;
	}
	
	@Override
	public int getInt() {
		return this.getInt(-1);
	}
	
	@Override
	public int getInt(int var1) {
		try {
			return Integer.parseInt(this.get_value());
		} catch (NumberFormatException var3) {
			return var1;
		}
	}
	
	@Override
	public boolean isIntValue() {
		try {
			Integer.parseInt(this.get_value());
			return true;
		} catch (NumberFormatException var2) {
			return false;
		}
	}
	
	@Override
	public boolean getBoolean(boolean var1) {
		return this.isBooleanValue() ? Boolean.parseBoolean(this.get_value()) : var1;
	}
	
	@Override
	public boolean isBooleanValue() {
		return "true".equals(this.get_value().toLowerCase()) || "false".equals(this.get_value().toLowerCase());
	}
	
	@Override
	public boolean isDoubleValue() {
		try {
			Double.parseDouble(this.get_value());
			return true;
		} catch (NumberFormatException var2) {
			return false;
		}
	}
	
	@Override
	public double getDouble(double var1) {
		try {
			return Double.parseDouble(this.get_value());
		} catch (NumberFormatException var4) {
			return var1;
		}
	}
	
	@Override
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
	
	@Override
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
	
	@Override
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
	
	@Override
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
	
	@Override
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
	
	@Override
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
	
	@Override
	public String getName() {
		return this.get_name();
	}
	
	@Override
	public void setName(String var1) {
		this.set_name(var1);
	}
	
	@Override
	public boolean wasRead() {
		return this.get_wasRead();
	}
	
	@Override
	public IProperty.IType getType() {
		return this.get_type();
	}
	
	@Override
	public boolean isList() {
		return this.get_isList();
	}
	
	@Override
	public String get_name() {
		return this.name;
	}
	
	@Override
	public void set_name(String var1) {
		this.name = var1;
	}
	
	@Override
	public String get_value() {
		return this.value;
	}
	
	@Override
	public void set_value(String var1) {
		this.value = var1;
	}
	
	@Override
	public String get_comment() {
		return this.comment;
	}
	
	@Override
	public void set_comment(String var1) {
		this.comment = var1;
	}
	
	@Override
	public String[] get_valueList() {
		return this.valueList;
	}
	
	@Override
	public void set_valueList(String[] var1) {
		this.valueList = var1;
	}
	
	@Override
	public boolean get_wasRead() {
		return this.wasRead;
	}
	
	@Override
	public boolean get_isList() {
		return this.isList;
	}
	
	@Override
	public IProperty.IType get_type() {
		return this.type;
	}
	
	public enum RType implements IProperty.IType {
		STRING,
		INTEGER,
		BOOLEAN,
		DOUBLE;
		
		public static final RType[] values = values();
		
		public static IProperty.IType tryParse(char var0) {
			for(RType rType : values) {
				if(rType.getID() == var0) {
					return rType;
				}
			}
			
			return STRING;
		}
		
		public char getID() {
			return this.name().charAt(0);
		}
		
		public static IProperty.IType get_STRING() {
			return STRING;
		}
		
		public static IProperty.IType get_INTEGER() {
			return INTEGER;
		}
		
		public static IProperty.IType get_BOOLEAN() {
			return BOOLEAN;
		}
		
		public static IProperty.IType get_DOUBLE() {
			return DOUBLE;
		}
	}
}
