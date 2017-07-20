package cc.mi.engine.concurrent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcurrentHashMap<K, V> {
	private static final int DEFAULT_CONCURRENCY_LEVEL = 8;
	private int mask;
	private final List<Map<K, V>> hashList;
	
	public ConcurrentHashMap(int concurrencyLevel) {
		if (Integer.bitCount(concurrencyLevel) != 1) {
			throw new RuntimeException("concurrencyLevel need to be pow of 2");
		}
		mask = concurrencyLevel - 1;
		hashList = new ArrayList<>(concurrencyLevel);
		for (int i = 0; i < concurrencyLevel; ++ i) {
			hashList.add(new HashMap<K, V>());
		}
	}
	
	public int size() {
		int cnt = 0;
		synchronized (this) {
			for (Map<K, V> hash : hashList) {
				cnt += hash.size();
			}
		}
		return cnt;
	}
	
	public ConcurrentHashMap() {
		this(DEFAULT_CONCURRENCY_LEVEL);
	}

	public boolean containsKey(K key) {
		int index = key.hashCode() & mask;
		return hashList.get(index).containsKey(key);
	}

	public V get(K key) {
		int index = key.hashCode() & mask;
		return hashList.get(index).get(key);
	}

	public V put(K key, V value) {
		int index = key.hashCode() & mask;
		return hashList.get(index).put(key, value);
	}

	public V remove(K key) {
		int index = key.hashCode() & mask;
		return hashList.get(index).remove(key);
	}

	public void clear() {
		synchronized (this) {
			for (Map<K, V> hash : hashList) {
				hash.clear();
			}
		}
	}
}