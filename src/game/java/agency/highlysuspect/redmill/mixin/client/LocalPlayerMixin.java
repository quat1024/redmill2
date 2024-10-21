package agency.highlysuspect.redmill.mixin.client;

import agency.highlysuspect.redmill.mixin.EntityMixin;
import agency.highlysuspect.redmill.oldschool.net.minecraft.block.material.IMaterial;
import agency.highlysuspect.redmill.oldschool.net.minecraft.block.material.RMaterial;
import agency.highlysuspect.redmill.oldschool.net.minecraft.client.entity.IEntityClientPlayerMP;
import agency.highlysuspect.redmill.oldschool.net.minecraft.entity.IEntity;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin extends EntityMixin implements IEntityClientPlayerMP {
	@Override
	public boolean isInsideOfMaterial(IMaterial mat) {
		//TODO more robust material implementation.
		if(mat == RMaterial.water)  {
			return ((LocalPlayer) (Object) this).isUnderWater();
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isSneaking() {
		return ((LocalPlayer) (Object) this).isCrouching();
	}
	
	@Override
	public IEntity get_ridingEntity() {
		return (IEntity) ((LocalPlayer) (Object) this).getVehicle();
	}
}
