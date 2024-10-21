package agency.highlysuspect.redmill.oldschool.net.minecraft.block.material;

import net.minecraft.world.level.material.MapColor;

public interface IMapColor {
	int get_colorValue();
	int get_colorIndex();
	
	IMapColor[] redmill$get_mapColorArray();
}
