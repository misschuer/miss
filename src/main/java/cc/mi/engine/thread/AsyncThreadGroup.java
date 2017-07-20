package cc.mi.engine.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class AsyncThreadGroup {
	/** 账号登录/角色创建组 */
	private static AsyncThreadGroup loginGroup;
	
	/** 私人逻辑组 */
	private static AsyncThreadGroup logicalGroup;
	
	/** 公共逻辑 */
	private static AsyncThreadGroup publicGroup;
	
	/** 房间逻辑组 */
	private static AsyncThreadGroup roomGroup;
	
	/** 数据保存组 */
	private static AsyncThreadGroup dbSaveGroup;
	
	public static void init() {
		loginGroup = 
				new AsyncThreadGroup("login", Runtime.getRuntime().availableProcessors() << 1);
		
		logicalGroup = 
				new AsyncThreadGroup("logical", Runtime.getRuntime().availableProcessors() << 1);
		
		publicGroup = new AsyncThreadGroup("public", 1);
		
		roomGroup = 
				new AsyncThreadGroup("room", Runtime.getRuntime().availableProcessors() << 1);
		
		dbSaveGroup = 
				new AsyncThreadGroup("dbSave", Runtime.getRuntime().availableProcessors() << 1);
	}
	
	/**
	 * 登录任务
	 * @param hash
	 * @param task
	 */
	public static void sumbitAccountLoginTask(int hash, Task task) {
		loginGroup.sumbit(hash, task);
	}
	
	/**
	 * 逻辑任务
	 * @param userId
	 * @param task
	 */
	public static void submitLogicalTask(int userId, Task task) {
		logicalGroup.sumbit(userId, task);
	}
	
	/**
	 * 公共任务
	 * @param userId
	 * @param task
	 */
	public static void submitPublicTask(Task task) {
		publicGroup.sumbit(0, task);
	}
	
	/**
	 * 房间任务
	 * @param userId
	 * @param task
	 */
	public static void submitRoomTask(int roomId, Task task) {
		roomGroup.sumbit(roomId, task);
	}
	
	/**
	 * 数据存储任务
	 * @param userId
	 * @param task
	 */
	public static void submitDBSaveTask(int userId, Task task) {
		dbSaveGroup.sumbit(userId, task);
	}
	
	ExecutorService[] executors;
	int mask;
	
	private AsyncThreadGroup(final String name, int threads) {
		ensure(threads);
		mask = threads - 1;
		executors = new ExecutorService[threads];
		for (int i = 0; i < executors.length; ++ i) {
			final int index = i;
			executors[ i ] = Executors.newSingleThreadExecutor(new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread thread = new Thread(r, name + "-" + index);
					thread.setDaemon(true);
					return thread;
				}
			});
		}
	}
	
	private void ensure(int threads) {
		if (Integer.bitCount(threads) != 1) {
			throw new RuntimeException("threads必须是2的n次方");
		}
	}
	
	public void sumbit(int hash, Task task) {
		int index = hash & mask;
		executors[index].submit(task);
	}
}
