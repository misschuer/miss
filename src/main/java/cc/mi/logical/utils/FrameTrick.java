package cc.mi.logical.utils;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FrameTrick {
	static final ScheduledThreadPoolExecutor[] executors = new ScheduledThreadPoolExecutor[16];
	static {
		for (int i = 0; i < executors.length; ++ i) {
			executors[ i ] = new ScheduledThreadPoolExecutor(1);
			final int index = i;
			executors[ i ].scheduleAtFixedRate(new Runnable() {
				public void run() {
					trick(index);
				}
			}, 0, 16_666_667, TimeUnit.NANOSECONDS);
		}
	}
	
	static void trick(int index) {
		
	}
}