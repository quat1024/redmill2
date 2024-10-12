package agency.highlysuspect.redmill.mixin.client;

import agency.highlysuspect.redmill.oldschool.net.minecraft.client.multiplayer.IWorldClient;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientLevel.class)
public class ClientLevelMixin implements IWorldClient {
	//..
}
