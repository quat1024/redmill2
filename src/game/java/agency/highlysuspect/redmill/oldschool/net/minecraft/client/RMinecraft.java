package agency.highlysuspect.redmill.oldschool.net.minecraft.client;

import net.minecraft.client.Minecraft;

public class RMinecraft {
	public static IMinecraft getMinecraft() {
		return (IMinecraft) Minecraft.getInstance();
	}
}
