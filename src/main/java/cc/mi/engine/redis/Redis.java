package cc.mi.engine.redis;

import java.util.List;

import cc.mi.engine.buffer.ByteBuffer;
import cc.mi.engine.db.utils.DBUtils;
import cc.mi.logical.mappin.Account;
import cc.mi.logical.mappin.User;
import cc.mi.logical.module.utils.AccountCache;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Redis {
	static final String HOST = "192.168.1.9";
	static final int PORT = 6379;
	static final String ACCOUNT_CACHE_KEY_PREFIX = "accountcache#";
	static final String PLAYER_CACHE_KEY_PREFIX = "playercache#";
	private JedisPool jedisPool;
	static Redis cacheRedis;
	
	public static void init() {
		cacheRedis = new Redis();
		cacheRedis.flush();
		loadAccountCache();
	}
	
	private static void loadAccountCache() {
		List<Account> accountList = DBUtils.fetchMany(Account.class);
		AccountCache accountCache = new AccountCache();
		ByteBuffer buffer = ByteBuffer.alloc();
		for (Account account : accountList) {
			buffer.writerIndex(0);
			accountCache.setAccount(account.getAccount());
			accountCache.setPassword(account.getPassword());
			accountCache.setUsable(0);
			accountCache.setUserId(1);
			User user = DBUtils.fecthOne(User.class, "account", account.getAccount());
			if (null != user) {
				accountCache.setUsable(user.getUsable());
				accountCache.setUserId(user.getUserId());
			}
			accountCache.encode(buffer);
			Redis.accountCache(account.getAccount(), buffer.getBytes());
		}
	}
	
	private Redis() {
		this(HOST, PORT);
	}
	
	private Redis(String host, int port) {
		JedisPoolConfig config = new JedisPoolConfig();
		//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		config.setBlockWhenExhausted(true);
		 
		//设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
		config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
		 
		//是否启用pool的jmx管理功能, 默认true
		config.setJmxEnabled(true);
		 
		//MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
		config.setJmxNamePrefix("pool");
		 
		//是否启用后进先出, 默认true
		config.setLifo(true);
		 
		//最大空闲连接数, 默认8个
		config.setMaxIdle(8);
		 
		/**
		 * dbcp的修改日志显示：change "maxActive" -> "maxTotal" 
		 * and "maxWait" -> "maxWaitMillis" in all examples.
		 */
		//最大连接数, 默认8个
		config.setMaxTotal(8);
		 
		//获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
		config.setMaxWaitMillis(-1);
		 
		//逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		config.setMinEvictableIdleTimeMillis(1800000);
		 
		//最小空闲连接数, 默认0
		config.setMinIdle(0);
		 
		//每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		config.setNumTestsPerEvictionRun(3);
		 
		//对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)   
		config.setSoftMinEvictableIdleTimeMillis(1800000);
		 
		//在获取连接的时候检查有效性, 默认false
		config.setTestOnBorrow(false);
		 
		//在空闲时检查有效性, 默认false
		config.setTestWhileIdle(false);
		 
		//逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
		config.setTimeBetweenEvictionRunsMillis(-1);
		
		jedisPool = new JedisPool(config, host, port);
	}
	
	/************************************ 账号缓存 *****************************************/
	public static void accountCache(String account, byte[] values) {
		try (Jedis jedis = cacheRedis.jedisPool.getResource();) {
			jedis.set((ACCOUNT_CACHE_KEY_PREFIX + account).getBytes(), values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isAccountExist(String account) {
		try (Jedis jedis = cacheRedis.jedisPool.getResource();) {
			return jedis.exists((ACCOUNT_CACHE_KEY_PREFIX + account).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static byte[] getAccountCache(String account) {
		try (Jedis jedis = cacheRedis.jedisPool.getResource();) {
			byte[] keys = (ACCOUNT_CACHE_KEY_PREFIX + account).getBytes();
			if (jedis.exists(keys)) {
				return jedis.get(keys);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void flush() {
		try (Jedis jedis = jedisPool.getResource();) {
			jedis.flushAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void test() {
		try (Jedis jedis = jedisPool.getResource();) {
			jedis.flushAll();
			jedis.set("aa", "12");
			System.out.println(jedis.get("aa"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Redis redis = new Redis();
		redis.test();
	}
}
