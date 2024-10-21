//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.net.minecraft.block.material;

public class RMaterialTransparent extends RMaterial {
	public RMaterialTransparent(IMapColor var1) {
		super(var1);
		this.setReplaceable();
	}
	
	public boolean isSolid() {
		return false;
	}
	
	public boolean getCanBlockGrass() {
		return false;
	}
	
	public boolean blocksMovement() {
		return false;
	}
}
