//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.net.minecraft.block.material;

public class RMaterialLiquid extends RMaterial {
	public RMaterialLiquid(IMapColor var1) {
		super(var1);
		this.setReplaceable();
		this.setNoPushMobility();
	}
	
	public boolean isLiquid() {
		return true;
	}
	
	public boolean blocksMovement() {
		return false;
	}
	
	public boolean isSolid() {
		return false;
	}
}
