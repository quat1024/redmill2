package agency.highlysuspect.redmill.oldschool.net.minecraft.client.entity;

import agency.highlysuspect.redmill.oldschool.net.minecraft.block.material.IMaterial;
import agency.highlysuspect.redmill.oldschool.net.minecraft.entity.IEntity;

public interface IEntityClientPlayerMP extends IEntity {
	boolean isInsideOfMaterial(IMaterial mat);
	boolean isSneaking();
	
	IEntity get_ridingEntity();
}
