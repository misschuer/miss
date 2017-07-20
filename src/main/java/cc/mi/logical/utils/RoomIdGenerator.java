package cc.mi.logical.utils;

import java.util.concurrent.locks.ReentrantLock;

public class RoomIdGenerator {
	static int roomId = 0;
	static int mask = (1 << 23) - 1;
	static ReentrantLock lock;
	
	public static int generate() {
		try {
			lock.lock();
			roomId = (roomId + 1) & mask;
			return roomId;
		} finally {
			lock.unlock();
		}
	}
}
