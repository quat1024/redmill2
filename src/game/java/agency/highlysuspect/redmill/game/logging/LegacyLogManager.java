package agency.highlysuspect.redmill.game.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LegacyLogManager extends LogManager {
	public static final LegacyLogManager INSTANCE = new LegacyLogManager();
	
	private final ConcurrentHashMap<String, Logger> LOGGERS = new ConcurrentHashMap<>();
	
	public static LogManager getLogManager() {
		return INSTANCE;
	}
	
	@Override
	public void reset() throws SecurityException {
		//nope
	}
	
	@Override
	public boolean addLogger(Logger logger) {
		if(LOGGERS.containsKey(logger.getName())) return false;
		
		LOGGERS.put(logger.getName(), logger);
		return true;
	}
	
	@Override
	public Logger getLogger(String name) {
		return LOGGERS.computeIfAbsent(name, LegacyLogger::new);
	}
	
	@Override
	public Enumeration<String> getLoggerNames() {
		return LOGGERS.keys();
	}
	
	@Override
	public void readConfiguration() throws IOException, SecurityException {
		//hell no
	}
	
	@Override
	public void readConfiguration(InputStream ins) throws IOException, SecurityException {
		//hell no
	}
	
	@Override
	public void updateConfiguration(Function<String, BiFunction<String, String, String>> mapper) throws IOException {
		//hell no
	}
	
	@Override
	public void updateConfiguration(InputStream ins, Function<String, BiFunction<String, String, String>> mapper) throws IOException {
		//hell no
	}
	
	@Override
	public String getProperty(String name) {
		return null;
	}
	
	@Override
	public LogManager addConfigurationListener(Runnable listener) {
		//hell no
		return this;
	}
	
	@Override
	public void removeConfigurationListener(Runnable listener) {
		//hell no
	}
}
