package agency.highlysuspect.redmill.oldschool.cpw.mods.fml.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface Mod {
	String modid();
	boolean useMetadata();
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@interface Block {
		String name();
		Class itemTypeClass() default void.class; //ItemBlock.class; //RM-TODO
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface FingerprintWarning {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface IMCCallback { }
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface Init {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@interface Instance {
		String value() default "";
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@interface Item {
		String name();
		String typeClass();
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@interface Metadata {
		String value() default "";
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface PostInit {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface PreInit {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface ServerAboutToStart {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface ServerStarted {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface ServerStarting {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface ServerStopped {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface ServerStopping {}
	
	
}
