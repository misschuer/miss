package cc.mi.engine.multithread;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo {
	public static void main(String[] args) {
		Random random = new Random();
		int num = 1 << 23;
		int users = 2000;
		Set<Integer> userIdSet = new HashSet<>();
		while (userIdSet.size() < users) {
			userIdSet.add(random.nextInt(1 << 30) + 1);
		}
		
		Integer[] userIdArray = new Integer[users];
		userIdSet.toArray(userIdArray);
		
		int threads = Runtime.getRuntime().availableProcessors() << 1;
		final int[] msgForUser = new int[num];
		final Map<Integer, Integer> nums = new HashMap<>();
		for (int i = 0; i < num; ++ i) {
			msgForUser[ i ] = userIdArray[random.nextInt(users)];
			int userId = msgForUser[ i ];
			if (!nums.containsKey(userId)) {
				nums.put(userId, 0);
			}
			nums.put(userId, nums.get(userId) + 1);
		}
		System.out.println("threads:" + threads + " realUserNum:" + nums.size());
		yieldDemo(users, num, msgForUser, nums, threads);
		hashDemo(users, num, msgForUser, nums, threads);
	}
	
	public static void yieldDemo(final int users, 
								final int num, 
								final int[] msgForUser, 
								final Map<Integer, Integer> nums, 
								int threads) {
		final Map<Integer, PaddedAtomicLong> userCounter = new HashMap<>();
		for (int userId : nums.keySet()) {
			userCounter.put(userId, new PaddedAtomicLong());
		}
		final ExecutorService es = Executors.newFixedThreadPool(threads);
		Map<Integer, Integer> conter = new HashMap<>();
		final CountDownLatch cdl = new CountDownLatch(nums.size());
		long mill = System.currentTimeMillis();
		for (int i = 0; i < num; ++ i) {
			final int userId = msgForUser[ i ];
			if (!conter.containsKey(userId))
				conter.put(userId, 0);
			final int id = conter.get(userId);
			conter.put(userId, id + 1);
			es.execute(new Task(userCounter.get(userId), id, new Runnable() {
				@Override
				public void run() {
					if (id == nums.get(userId) - 1) {
						cdl.countDown();
					}
				}
			}));
		}
		try {
			cdl.await();
			es.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("yield:" + (System.currentTimeMillis() - mill) + "ms");
	}
	
	public static void hashDemo(final int users, 
								final int num, 
								final int[] msgForUser, 
								final Map<Integer, Integer> nums, 
								int threads) {
		final Map<Integer, PaddedAtomicLong> userCounter = new HashMap<>();
		for (int userId : nums.keySet()) {
			userCounter.put(userId, new PaddedAtomicLong());
		}
		int mask = threads - 1;
		final ExecutorService[] esa = new ExecutorService[threads];
		for (int i = 0; i < esa.length; ++ i) {
			esa[ i ] = Executors.newSingleThreadExecutor();
		}
		Map<Integer, Integer> userThreadIndex = new HashMap<>();
		AtomicInteger currThreadIndex = new AtomicInteger(0);
		Map<Integer, Integer> conter = new HashMap<>();
		final CountDownLatch cdl = new CountDownLatch(nums.size());
		long mill = System.currentTimeMillis();
		for (int i = 0; i < num; ++ i) {
			final int userId = msgForUser[ i ];
			if (!conter.containsKey(userId))
				conter.put(userId, 0);
			final int id = conter.get(userId);
			conter.put(userId, id + 1);
			if (!userThreadIndex.containsKey(userId)) {
				userThreadIndex.put(userId, currThreadIndex.getAndIncrement() & mask);
			}
			int threadIndex = userThreadIndex.get(userId);
			esa[threadIndex].execute(new Task(userCounter.get(userId), id, new Runnable() {
				@Override
				public void run() {
//					System.out.println(spaces(userId) + userId + ":" + id + " > " + Thread.currentThread().getName());
					if (id == nums.get(userId) - 1) {
						cdl.countDown();
					}
				}
			}));
		}
		try {
			cdl.await();
			for (int i = 0; i < esa.length; ++ i) {
				esa[ i ].shutdown();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("hash:" + (System.currentTimeMillis() - mill) + "ms");
	}
	
	public static String spaces(int nums) {
		String string = "";
		for (int i = 0; i < nums; ++ i) string += " ";
		return string;
	}
}