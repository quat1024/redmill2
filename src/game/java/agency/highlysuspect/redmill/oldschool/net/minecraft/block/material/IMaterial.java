package agency.highlysuspect.redmill.oldschool.net.minecraft.block.material;

public interface IMaterial {
	boolean isLiquid();
	
	boolean isSolid();
	
	boolean getCanBlockGrass();
	
	boolean blocksMovement();
	
	IMaterial setTranslucent(); //RM: was private for some reason
	
	IMaterial setRequiresTool();
	
	IMaterial setBurning();
	
	boolean getCanBurn();
	
	IMaterial setReplaceable();
	
	boolean isReplaceable();
	
	boolean isOpaque();
	
	boolean isToolNotRequired();
	
	int getMaterialMobility();
	
	IMaterial setNoPushMobility();
	
	IMaterial setImmovableMobility();
	
	IMaterial func_85158_p();
	
	boolean func_85157_q();
	
	boolean get_canBurn();
	
	void set_canBurn(boolean var1);
	
	boolean get_replaceable();
	
	void set_replaceable(boolean var1);
	
	boolean get_isTranslucent();
	
	void set_isTranslucent(boolean var1);
	
	IMapColor get_materialMapColor();
	
	boolean get_requiresNoTool();
	
	void set_requiresNoTool(boolean var1);
	
	int get_mobilityFlag();
	
	void set_mobilityFlag(int var1);
	
	boolean get_field_85159_M();
	
	void set_field_85159_M(boolean var1);
}
