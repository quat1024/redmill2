//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package agency.highlysuspect.redmill.oldschool.net.minecraft.block.material;

public class RMaterial implements IMaterial {
	public static final IMaterial air = new RMaterialTransparent(RMapColor.get_airColor());
	public static final IMaterial grass = new RMaterial(RMapColor.get_grassColor());
	public static final IMaterial ground = new RMaterial(RMapColor.get_dirtColor());
	public static final IMaterial wood = (new RMaterial(RMapColor.get_woodColor())).setBurning();
	public static final IMaterial rock = (new RMaterial(RMapColor.get_stoneColor())).setRequiresTool();
	public static final IMaterial iron = (new RMaterial(RMapColor.get_ironColor())).setRequiresTool();
	public static final IMaterial anvil = (new RMaterial(RMapColor.get_ironColor())).setRequiresTool().setImmovableMobility();
	public static final IMaterial water = (new RMaterialLiquid(RMapColor.get_waterColor())).setNoPushMobility();
	public static final IMaterial lava = (new RMaterialLiquid(RMapColor.get_tntColor())).setNoPushMobility();
	public static final IMaterial leaves = (new RMaterial(RMapColor.get_foliageColor())).setBurning().setTranslucent().setNoPushMobility();
	public static final IMaterial plants = (new RMaterialLogic(RMapColor.get_foliageColor())).setNoPushMobility();
	public static final IMaterial vine = (new RMaterialLogic(RMapColor.get_foliageColor())).setBurning().setNoPushMobility().setReplaceable();
	public static final IMaterial sponge = new RMaterial(RMapColor.get_clothColor());
	public static final IMaterial cloth = (new RMaterial(RMapColor.get_clothColor())).setBurning();
	public static final IMaterial fire = (new RMaterialTransparent(RMapColor.get_airColor())).setNoPushMobility();
	public static final IMaterial sand = new RMaterial(RMapColor.get_sandColor());
	public static final IMaterial circuits = (new RMaterialLogic(RMapColor.get_airColor())).setNoPushMobility();
	public static final IMaterial glass = (new RMaterial(RMapColor.get_airColor())).setTranslucent().func_85158_p();
	public static final IMaterial redstoneLight = (new RMaterial(RMapColor.get_airColor())).func_85158_p();
	public static final IMaterial tnt = (new RMaterial(RMapColor.get_tntColor())).setBurning().setTranslucent();
	public static final IMaterial coral = (new RMaterial(RMapColor.get_foliageColor())).setNoPushMobility();
	public static final IMaterial ice = (new RMaterial(RMapColor.get_iceColor())).setTranslucent().func_85158_p();
	public static final IMaterial snow = (new RMaterialLogic(RMapColor.get_snowColor())).setReplaceable().setTranslucent().setRequiresTool().setNoPushMobility();
	public static final IMaterial craftedSnow = (new RMaterial(RMapColor.get_snowColor())).setRequiresTool();
	public static final IMaterial cactus = (new RMaterial(RMapColor.get_foliageColor())).setTranslucent().setNoPushMobility();
	public static final IMaterial clay = new RMaterial(RMapColor.get_clayColor());
	public static final IMaterial pumpkin = (new RMaterial(RMapColor.get_foliageColor())).setNoPushMobility();
	public static final IMaterial dragonEgg = (new RMaterial(RMapColor.get_foliageColor())).setNoPushMobility();
	public static final IMaterial portal = (new RMaterialPortal(RMapColor.get_airColor())).setImmovableMobility();
	public static final IMaterial cake = (new RMaterial(RMapColor.get_airColor())).setNoPushMobility();
	public static final IMaterial web = (new RMaterialWeb(RMapColor.get_clothColor())).setRequiresTool().setNoPushMobility();
	public static final IMaterial piston = (new RMaterial(RMapColor.get_stoneColor())).setImmovableMobility();
	private boolean canBurn;
	private boolean replaceable;
	private boolean isTranslucent;
	public final IMapColor materialMapColor;
	private boolean requiresNoTool = true;
	private int mobilityFlag;
	private boolean field_85159_M;
	
	public RMaterial(IMapColor var1) {
		this.materialMapColor = var1;
	}
	
	@Override
	public boolean isLiquid() {
		return false;
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
	@Override
	public boolean getCanBlockGrass() {
		return true;
	}
	
	@Override
	public boolean blocksMovement() {
		return true;
	}
	
	@Override
	public IMaterial setTranslucent() {
		this.set_isTranslucent(true);
		return this;
	}
	
	@Override
	public IMaterial setRequiresTool() {
		this.set_requiresNoTool(false);
		return this;
	}
	
	@Override
	public IMaterial setBurning() {
		this.set_canBurn(true);
		return this;
	}
	
	@Override
	public boolean getCanBurn() {
		return this.get_canBurn();
	}
	
	@Override
	public IMaterial setReplaceable() {
		this.set_replaceable(true);
		return this;
	}
	
	@Override
	public boolean isReplaceable() {
		return this.get_replaceable();
	}
	
	@Override
	public boolean isOpaque() {
		return this.get_isTranslucent() ? false : this.blocksMovement();
	}
	
	@Override
	public boolean isToolNotRequired() {
		return this.get_requiresNoTool();
	}
	
	@Override
	public int getMaterialMobility() {
		return this.get_mobilityFlag();
	}
	
	@Override
	public IMaterial setNoPushMobility() {
		this.set_mobilityFlag(1);
		return this;
	}
	
	@Override
	public IMaterial setImmovableMobility() {
		this.set_mobilityFlag(2);
		return this;
	}
	
	@Override
	public IMaterial func_85158_p() {
		this.set_field_85159_M(true);
		return this;
	}
	
	@Override
	public boolean func_85157_q() {
		return this.get_field_85159_M();
	}
	
	public static IMaterial get_air() {
		return air;
	}
	
	public static IMaterial get_grass() {
		return grass;
	}
	
	public static IMaterial get_ground() {
		return ground;
	}
	
	public static IMaterial get_wood() {
		return wood;
	}
	
	public static IMaterial get_rock() {
		return rock;
	}
	
	public static IMaterial get_iron() {
		return iron;
	}
	
	public static IMaterial get_anvil() {
		return anvil;
	}
	
	public static IMaterial get_water() {
		return water;
	}
	
	public static IMaterial get_lava() {
		return lava;
	}
	
	public static IMaterial get_leaves() {
		return leaves;
	}
	
	public static IMaterial get_plants() {
		return plants;
	}
	
	public static IMaterial get_vine() {
		return vine;
	}
	
	public static IMaterial get_sponge() {
		return sponge;
	}
	
	public static IMaterial get_cloth() {
		return cloth;
	}
	
	public static IMaterial get_fire() {
		return fire;
	}
	
	public static IMaterial get_sand() {
		return sand;
	}
	
	public static IMaterial get_circuits() {
		return circuits;
	}
	
	public static IMaterial get_glass() {
		return glass;
	}
	
	public static IMaterial get_redstoneLight() {
		return redstoneLight;
	}
	
	public static IMaterial get_tnt() {
		return tnt;
	}
	
	public static IMaterial get_coral() {
		return coral;
	}
	
	public static IMaterial get_ice() {
		return ice;
	}
	
	public static IMaterial get_snow() {
		return snow;
	}
	
	public static IMaterial get_craftedSnow() {
		return craftedSnow;
	}
	
	public static IMaterial get_cactus() {
		return cactus;
	}
	
	public static IMaterial get_clay() {
		return clay;
	}
	
	public static IMaterial get_pumpkin() {
		return pumpkin;
	}
	
	public static IMaterial get_dragonEgg() {
		return dragonEgg;
	}
	
	public static IMaterial get_portal() {
		return portal;
	}
	
	public static IMaterial get_cake() {
		return cake;
	}
	
	public static IMaterial get_web() {
		return web;
	}
	
	public static IMaterial get_piston() {
		return piston;
	}
	
	@Override
	public boolean get_canBurn() {
		return this.canBurn;
	}
	
	@Override
	public void set_canBurn(boolean var1) {
		this.canBurn = var1;
	}
	
	@Override
	public boolean get_replaceable() {
		return this.replaceable;
	}
	
	@Override
	public void set_replaceable(boolean var1) {
		this.replaceable = var1;
	}
	
	@Override
	public boolean get_isTranslucent() {
		return this.isTranslucent;
	}
	
	@Override
	public void set_isTranslucent(boolean var1) {
		this.isTranslucent = var1;
	}
	
	@Override
	public IMapColor get_materialMapColor() {
		return this.materialMapColor;
	}
	
	@Override
	public boolean get_requiresNoTool() {
		return this.requiresNoTool;
	}
	
	@Override
	public void set_requiresNoTool(boolean var1) {
		this.requiresNoTool = var1;
	}
	
	@Override
	public int get_mobilityFlag() {
		return this.mobilityFlag;
	}
	
	@Override
	public void set_mobilityFlag(int var1) {
		this.mobilityFlag = var1;
	}
	
	@Override
	public boolean get_field_85159_M() {
		return this.field_85159_M;
	}
	
	@Override
	public void set_field_85159_M(boolean var1) {
		this.field_85159_M = var1;
	}
}
