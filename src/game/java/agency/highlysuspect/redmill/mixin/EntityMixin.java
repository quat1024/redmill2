package agency.highlysuspect.redmill.mixin;

import agency.highlysuspect.redmill.oldschool.net.minecraft.entity.IEntity;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public class EntityMixin implements IEntity {
	@Override
	public boolean isEntityAlive() {
		return ((Entity) (Object) this).isAlive();
	}
}
