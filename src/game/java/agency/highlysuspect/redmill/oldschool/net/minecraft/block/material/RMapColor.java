//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.net.minecraft.block.material;

import net.minecraft.world.level.material.MapColor;

public class RMapColor implements IMapColor {
	public static final IMapColor[] mapColorArray = rm$getArray();
	public static final IMapColor airColor = rm$get(MapColor.NONE);
	public static final IMapColor grassColor = rm$get(MapColor.GRASS);
	public static final IMapColor sandColor = rm$get(MapColor.SAND);
	public static final IMapColor clothColor = rm$get(MapColor.WOOL);
	public static final IMapColor tntColor = rm$get(MapColor.FIRE);
	public static final IMapColor iceColor = rm$get(MapColor.ICE);
	public static final IMapColor ironColor = rm$get(MapColor.METAL);
	public static final IMapColor foliageColor = rm$get(MapColor.PLANT);
	public static final IMapColor snowColor = rm$get(MapColor.SNOW);
	public static final IMapColor clayColor = rm$get(MapColor.CLAY);
	public static final IMapColor dirtColor = rm$get(MapColor.DIRT);
	public static final IMapColor stoneColor = rm$get(MapColor.STONE);
	public static final IMapColor waterColor = rm$get(MapColor.WATER);
	public static final IMapColor woodColor = rm$get(MapColor.WOOD);
	public final int colorValue;
	public final int colorIndex;
	
	private RMapColor(int var1, int var2) {
		this.colorIndex = var1;
		this.colorValue = var2;
		mapColorArray[var1] = this;
	}
	
	public static IMapColor[] get_mapColorArray() {
		return mapColorArray;
	}
	
	public static IMapColor get_airColor() {
		return airColor;
	}
	
	public static IMapColor get_grassColor() {
		return grassColor;
	}
	
	public static IMapColor get_sandColor() {
		return sandColor;
	}
	
	public static IMapColor get_clothColor() {
		return clothColor;
	}
	
	public static IMapColor get_tntColor() {
		return tntColor;
	}
	
	public static IMapColor get_iceColor() {
		return iceColor;
	}
	
	public static IMapColor get_ironColor() {
		return ironColor;
	}
	
	public static IMapColor get_foliageColor() {
		return foliageColor;
	}
	
	public static IMapColor get_snowColor() {
		return snowColor;
	}
	
	public static IMapColor get_clayColor() {
		return clayColor;
	}
	
	public static IMapColor get_dirtColor() {
		return dirtColor;
	}
	
	public static IMapColor get_stoneColor() {
		return stoneColor;
	}
	
	public static IMapColor get_waterColor() {
		return waterColor;
	}
	
	public static IMapColor get_woodColor() {
		return woodColor;
	}
	
	public int get_colorValue() {
		return this.colorValue;
	}
	
	public int get_colorIndex() {
		return this.colorIndex;
	}
	
	@Override
	public IMapColor[] redmill$get_mapColorArray() {
		return rm$getArray();
	}
	
	public static IMapColor rm$get(MapColor vanilla) {
		return (IMapColor) vanilla;
	}
	
	public static IMapColor[] rm$getArray() {
		return ((IMapColor) MapColor.CLAY).redmill$get_mapColorArray();
	}
}
