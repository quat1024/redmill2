package agency.highlysuspect.redmill.mixin;

import agency.highlysuspect.redmill.oldschool.net.minecraft.block.material.IMapColor;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MapColor.class)
public class MapColorMixin implements IMapColor {
	@Shadow private static MapColor[] MATERIAL_COLORS;

	@Override
	public int get_colorValue() {
		return ((MapColor) (Object) this).col;
	}

	@Override
	public int get_colorIndex() {
		return ((MapColor) (Object) this).id;
	}
	
	@Override
	public IMapColor[] redmill$get_mapColorArray() {
		return (IMapColor[]) MATERIAL_COLORS;
	}
}
