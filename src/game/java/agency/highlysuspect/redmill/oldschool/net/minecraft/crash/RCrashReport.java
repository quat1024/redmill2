package agency.highlysuspect.redmill.oldschool.net.minecraft.crash;

import net.minecraft.CrashReport;
import net.minecraft.ReportType;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class RCrashReport implements ICrashReport {
	public final CrashReport delegate;
	
	public RCrashReport(String title, Throwable cause) {
		delegate = new CrashReport(title, cause);
	}
	
	@Override
	public CrashReport redmill$getDelegate() {
		return delegate;
	}
	
	@Override
	public String getDescription() {
		return delegate.getTitle();
	}
	
	@Override
	public Throwable getCause() {
		return delegate.getException();
	}
	
	@Override
	public String func_90021_c() {
		return delegate.getDetails();
	}
	
	@Override
	public void getSectionsInStringBuilder(StringBuilder sb) {
		delegate.getDetails(sb);
	}
	
	@Override
	public String getCauseStackTraceOrString() {
		return delegate.getExceptionMessage();
	}
	
	@Override
	public String getCompleteReport() {
		return delegate.getFriendlyReport(ReportType.CRASH);
	}
	
	@Override
	public File getFile() {
		Path saveFile = delegate.getSaveFile();
		if(saveFile == null) return null;
		else return saveFile.toFile();
	}
	
	@Override
	public boolean saveToFile(File var1) {
		return delegate.saveToFile(var1.toPath(), ReportType.CRASH);
	}
	
	//TODO: func_85056_g (getCrashReportCategory)
	//TODO: makeCategory
	//TODO: makeCategoryDepth
	
	@Override
	public String get_description() {
		return getDescription();
	}
	
	@Override
	public Throwable get_cause() {
		return getCause();
	}
	
	//  public ICrashReportCategory get_field_85061_c() {
	//    return this.field_85061_c;
	//  }
//	public List get_crashReportSections() {
//		return this.crashReportSections;
//	}
//
//	public File get_crashReportFile() {
//		return this.crashReportFile;
//	}
//
//	public void set_crashReportFile(File var1) {
//		this.crashReportFile = var1;
//	}
//
//	public boolean get_field_85059_f() {
//		return this.field_85059_f;
//	}
//
//	public void set_field_85059_f(boolean var1) {
//		this.field_85059_f = var1;
//	}
//
//	public StackTraceElement[] get_field_85060_g() {
//		return this.field_85060_g;
//	}
//
//	public void set_field_85060_g(StackTraceElement[] var1) {
//		this.field_85060_g = var1;
//	}
}
