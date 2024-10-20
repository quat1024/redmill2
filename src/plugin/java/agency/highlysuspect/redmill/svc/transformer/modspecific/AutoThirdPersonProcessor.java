package agency.highlysuspect.redmill.svc.transformer.modspecific;

import agency.highlysuspect.redmill.svc.transformer.ClassProcessor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class AutoThirdPersonProcessor implements ClassProcessor, Opcodes {
	private static final String FORGE_IMPL = "agency/highlysuspect/autothirdperson/forge/ForgeImpl";
	private static final String VINTAGE_FORGE_SETTINGS = "agency/highlysuspect/autothirdperson/forge/VintageForgeSettings";
	private static final String OFSATP = "agency/highlysuspect/autothirdperson/forge/OneFourSevenAutoThirdPerson";
	
	@Override
	public void accept(ClassNode node) {
		if(FORGE_IMPL.equals(node.name)) {
			//remove the renderLast method, which is for fixHandGlitch stuff
			//the hand glitch is fixed, and there's nooooo way that reflection will work
			node.methods.removeIf(m -> m.name.equals("renderLast"));
		} else if(VINTAGE_FORGE_SETTINGS.equals(node.name)) {
			for(MethodNode method : node.methods) {
				//fix get() returning null when loading the now-missing options
				if(method.name.equals("<init>")) {
					for(AbstractInsnNode ins : method.instructions) {
						if(ins instanceof MethodInsnNode min &&
							min.owner.equals("java/util/Map") &&
							min.name.equals("get")
						) {
							min.setOpcode(INVOKESTATIC);
							min.owner = "agency/highlysuspect/redmill/game/modspecific/ATPSupport";
							min.desc = "(Ljava/util/Map;Ljava/lang/Object;)Ljava/lang/Object;";
							min.itf = false;
						}
					}
				}
			}
		} else if(OFSATP.equals(node.name)) {
			for(MethodNode method : node.methods) {
				if(method.name.equals("caps")) {
					//the hand glitch is fixed, so no need for hasHandGlitch
					//sneak-to-dismount is implemented, so no need for noSneakDismount
					//in fact we should just aload the VersionCapabilities arg and return it unchanged
					method.instructions.clear();
					method.instructions.add(new VarInsnNode(ALOAD, 1));
					method.instructions.add(new InsnNode(ARETURN));
				}
			}
		}
	}
}
