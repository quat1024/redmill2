package agency.highlysuspect.redmill.oldschool.net.minecraft.crash;

import net.minecraft.CrashReport;

import java.io.File;

public interface ICrashReport {
	CrashReport redmill$getDelegate();
	
	String getDescription();
	
	Throwable getCause();
	
	String func_90021_c();
	
	void getSectionsInStringBuilder(StringBuilder sb);
	
	String getCauseStackTraceOrString();
	
	String getCompleteReport();
	
	File getFile();
	
	boolean saveToFile(File var1);
	
	String get_description();
	
	Throwable get_cause();
}
