package agency.highlysuspect.redmill.mixin;

import agency.highlysuspect.redmill.oldschool.net.minecraft.entity.item.IEntityMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Minecart.class)
public class MinecartMixin extends EntityMixin implements IEntityMinecart {
}
