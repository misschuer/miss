package cc.mi.engine.aop;

import java.lang.reflect.Method;

public abstract class MethodInterceptor {
	public abstract Object intercept(Object o, Method method, Object[] args) throws Throwable;
}