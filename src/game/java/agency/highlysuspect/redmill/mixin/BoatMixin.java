package agency.highlysuspect.redmill.mixin;

import agency.highlysuspect.redmill.oldschool.net.minecraft.entity.item.IEntityBoat;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Boat.class)
//RM-TODO: extend VehicleEntity instead
public class BoatMixin extends EntityMixin implements IEntityBoat {

}
