package cc.mi.engine.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;

public class CustomLogger {
	protected static final String FQCN = CustomLogger.class.getName();
	protected static final Level ERROR_LOG = Level.forName("ERROR", 12);
	protected static final Level MSG_LOG   = Level.forName("MSG"  , 23);
	protected static final Level DEV_LOG   = Level.forName("DEV"  , 30);
	protected static final Level DB_LOG    = Level.forName("DB"   , 89);
	
	private final ExtendedLoggerWrapper extendLogger;
	
	public CustomLogger(Class<?> clazz) {
		Logger logger = LogManager.getLogger(clazz);
		extendLogger = new ExtendedLoggerWrapper((ExtendedLogger) logger, clazz.getName(), logger.getMessageFactory());
	}
			
	public static CustomLogger getLogger(Class<?> clazz) {
		return new CustomLogger(clazz);
	}
	
	public void devLog(String message, Object... params) {
		extendLogger.logIfEnabled(FQCN, DEV_LOG, null, message, params);
	}
}
