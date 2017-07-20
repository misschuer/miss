package cc.mi.engine.aop.test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class MethodHash {
	private Map<String, Method> hash;
	
	protected MethodHash() {
		Method[] methods = this.getClass().getDeclaredMethods();
		hash = new HashMap<>();
		for (Method method : methods) {
			if ((method.getModifiers() & Modifier.STATIC) == 0) {
				if (!hash.containsKey(method.getName())) {
					hash.put(method.getName(), method);
					System.out.println(method.getName());
				}
			}
		}
		System.out.println(hash.size());
	}
}
