package agency.highlysuspect.redmill.game.logging;

import org.apache.logging.log4j.LogManager;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LegacyLogger extends Logger {
	public LegacyLogger(String name) {
		super(name, null);
		
		this.real = LogManager.getLogger(name);
		
		addHandler(new Handler() {
			@Override
			public void publish(LogRecord record) {
				real.log(getLog4jLevel(record.getLevel()), fmt(record));
			}
			
			@Override
			public void flush() {
				//no-op
			}
			
			@Override
			public void close() throws SecurityException {
				//no-op
			}
		});
	}
	
	public final org.apache.logging.log4j.Logger real;
	
	public org.apache.logging.log4j.Level getLog4jLevel(Level level) {
		if(level == Level.SEVERE) return org.apache.logging.log4j.Level.FATAL;
		else if(level == Level.WARNING) return org.apache.logging.log4j.Level.WARN;
		else if(level == Level.INFO || level == Level.CONFIG) return org.apache.logging.log4j.Level.INFO;
		else if(level == Level.FINE) return org.apache.logging.log4j.Level.DEBUG;
		else if(level == Level.FINER || level == Level.FINEST) return org.apache.logging.log4j.Level.TRACE;
		else return org.apache.logging.log4j.Level.ALL;
	}
	
	protected final String fmt(String msg, Object arg) {
		return MessageFormat.format(msg, arg);
	}
	
	protected final String fmtArray(String msg, Object[] args) {
		return MessageFormat.format(msg, args);
	}
	
	protected final String fmt(LogRecord record) {
		return fmtArray(record.getMessage(), record.getParameters());
	}
	
	@Override
	public boolean isLoggable(Level level) {
		return real.isEnabled(getLog4jLevel(level));
	}
	
	@Override
	public void setResourceBundle(ResourceBundle bundle) {
		//We're not gonna use resource bundles
	}
	
	@Override
	public ResourceBundle getResourceBundle() {
		return null;
	}
	
	@Override
	public void setLevel(Level newLevel) throws SecurityException {
		//We're not gonna support logger-wide log levels
	}
	
	@Override
	public Level getLevel() {
		return null;
	}
	
	@Override
	public void setParent(Logger parent) {
		//We're not gonna support logger hierarchies
	}
	
	@Override
	public Logger getParent() {
		return null;
	}
	
	@Override
	public void setUseParentHandlers(boolean useParentHandlers) {
		//We're not gonna support logger hierarchies
	}
	
	@Override
	public boolean getUseParentHandlers() {
		return false;
	}
	
	public static Logger getLogger(String name) {
		return LegacyLogManager.getLogManager().getLogger(name);
	}
	
	public static Logger getLogger(String name, String resourceBundleName) {
		return getLogger(name);
	}
	
	public static Logger getLogger(String name, Class<?> callerClass) {
		return getLogger(name + " (" + callerClass.getSimpleName() + ")");
	}
	
	public static Logger getLogger(String name, String resourceBundleName, Class<?> callerClass) {
		return getLogger(name, callerClass);
	}

	/*
	@Override
	public void log(LogRecord record) {
		real.log(getLog4jLevel(record.getLevel()),
			MessageFormat.format(record.getMessage(), record.getParameters()));
	}
	
	@Override
	public void log(Level level, String msg) {
		real.log(getLog4jLevel(level), msg);
	}
	
	@Override
	public void log(Level level, Supplier<String> msgSupplier) {
		org.apache.logging.log4j.Level l = getLog4jLevel(level);
		if(real.isEnabled(l)) {
			real.log(l, msgSupplier.get());
		}
	}
	
	@Override
	public void log(Level level, String msg, Object param1) {
		org.apache.logging.log4j.Level l = getLog4jLevel(level);
		if(real.isEnabled(l)) {
			real.log(l, fmt(msg, param1));
		}
	}
	
	@Override
	public void log(Level level, String msg, Object[] params) {
		org.apache.logging.log4j.Level l = getLog4jLevel(level);
		if(real.isEnabled(l)) {
			real.log(l, fmtArray(msg, params));
		}
	}
	
	@Override
	public void log(Level level, String msg, Throwable thrown) {
		org.apache.logging.log4j.Level l = getLog4jLevel(level);
		if(real.isEnabled(l)) {
			real.log(l, msg, thrown);
		}
	}
	
	@Override
	public void log(Level level, Throwable thrown, Supplier<String> msgSupplier) {
		org.apache.logging.log4j.Level l = getLog4jLevel(level);
		if(real.isEnabled(l)) {
			real.log(l, msgSupplier.get(), thrown);
		}
	}
	
	@Override
	public void logp(Level level, String sourceClass, String sourceMethod, String msg) {
		org.apache.logging.log4j.Level l = getLog4jLevel(level);
		if(real.isEnabled(l)) {
			real.log(l, sourceClass + "#" + sourceMethod + ": " + msg);
		}
	}
	
	@Override
	public void logp(Level level, String sourceClass, String sourceMethod, Supplier<String> msgSupplier) {
		org.apache.logging.log4j.Level l = getLog4jLevel(level);
		if(real.isEnabled(l)) {
			real.log(l, sourceClass + "#" + sourceMethod + ": " + msgSupplier.get());
		}
	}
	
	@Override
	public void logp(Level level, String sourceClass, String sourceMethod, String msg, Object param1) {
		org.apache.logging.log4j.Level l = getLog4jLevel(level);
		if(real.isEnabled(l)) {
			real.log(l, sourceClass + "#" + sourceMethod + ": " + fmt(msg, param1));
		}
	}
	
	@Override
	public void logp(Level level, String sourceClass, String sourceMethod, String msg, Object[] params) {
		org.apache.logging.log4j.Level l = getLog4jLevel(level);
		if(real.isEnabled(l)) {
			real.log(l, sourceClass + "#" + sourceMethod + ": " + fmtArray(msg, params));
		}
	}
	
	@Override
	public void logp(Level level, String sourceClass, String sourceMethod, String msg, Throwable thrown) {
		org.apache.logging.log4j.Level l = getLog4jLevel(level);
		if(real.isEnabled(l)) {
			real.log(l, sourceClass + "#" + sourceMethod + ": " + msg, thrown);
		}
	}
	
	@Override
	public void logp(Level level, String sourceClass, String sourceMethod, Throwable thrown, Supplier<String> msgSupplier) {
		org.apache.logging.log4j.Level l = getLog4jLevel(level);
		if(real.isEnabled(l)) {
			real.log(l, sourceClass + "#" + sourceMethod + ": " + msgSupplier.get(), thrown);
		}
	}
	 */
	
	/// Ignore all the resource bundle crap ///
	
//	@Override
//	public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg) {
//		logp(level, sourceClass, sourceMethod, msg);
//	}
//
//	@Override
//	public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object param1) {
//		logp(level, sourceClass, sourceMethod, msg, param1);
//	}
//
//	@Override
//	public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object[] params) {
//		logp(level, sourceClass, sourceMethod, msg, params);
//	}
//
//	@Override
//	public void logrb(Level level, String sourceClass, String sourceMethod, ResourceBundle bundle, String msg, Object... params) {
//		logp(level, sourceClass, sourceMethod, msg, params);
//	}
//
//	@Override
//	public void logrb(Level level, ResourceBundle bundle, String msg, Object... params) {
//		log(level, msg, params);
//	}
//
//	@Override
//	public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Throwable thrown) {
//		logp(level, sourceClass, sourceMethod, msg, thrown);
//	}
//
//	@Override
//	public void logrb(Level level, String sourceClass, String sourceMethod, ResourceBundle bundle, String msg, Throwable thrown) {
//		logp(level, sourceClass, sourceMethod, msg, thrown);
//	}
//
//	@Override
//	public void logrb(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
//		log(level, msg, thrown);
//	}
}
