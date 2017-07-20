package cc.mi.engine;

import java.io.IOException;

import org.apache.log4j.Logger;

import cc.mi.engine.db.ConnectionPool;
import cc.mi.engine.db.check.TableChecker;
import cc.mi.engine.redis.Redis;
import cc.mi.engine.thread.AsyncThreadGroup;

public class Engine {
	private final static Logger logger = Logger.getLogger(Engine.class);
	
	public static void init() throws ClassNotFoundException, IOException, ClassFormatError, InstantiationException, IllegalAccessException {
		ConnectionPool.init();
		logger.info("连接池初始化完成");
		TableChecker.tableSync();
		logger.info("表同步初始化完成");
		Redis.init();
		logger.info("账号redis缓存初始化完成");
		AsyncThreadGroup.init();
		logger.info("自定义线程组初始化完成");
	}
	
	public static void main(String[] args) {
		try {
			Engine.init();
			logger.info("服务器初始化完成");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
