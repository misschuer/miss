package cc.mi.engine.event.manager;

import java.util.HashMap;
import java.util.Map;

public final class EventManager {
	/**
	 * 一定要在线程同步的情况下使用
	 */
	private static final Map<String, Map<Class<?>, EventListener>> listenerHash = new HashMap<>();
	
	/**
	 * 添加事件
	 * @param listener
	 */
	public static void addEventListener(Class<?> clazz, String eventType, EventListener listener) {
		if (!listenerHash.containsKey(eventType)) {
			listenerHash.put(eventType, new HashMap<Class<?>, EventListener>());
		}
		listenerHash.get(eventType).put(clazz, listener);
	}
	
	/**
	 * 触发事件
	 * @param eventType
	 */
	public static void dispatch(String eventType, EventParam eventParam) {
		if (!listenerHash.containsKey(eventType)) {
			return;
		}
		for (EventListener listener : listenerHash.get(eventType).values()) {
			listener.dispatched(eventParam);
		}
	}
	
	/**
	 * 移除事件
	 * @param eventType
	 * @param listener
	 */
	public static void removeEventListener(Class<?> clazz, String eventType, EventListener listener) {
		if (!listenerHash.containsKey(eventType)) {
			return;
		}
		listenerHash.get(eventType).remove(clazz);
	}
}