package agency.highlysuspect.redmill.mixin;

import agency.highlysuspect.redmill.oldschool.net.minecraft.entity.passive.IEntityAnimal;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Animal.class)
public class AnimalMixin extends EntityMixin implements IEntityAnimal {
}
