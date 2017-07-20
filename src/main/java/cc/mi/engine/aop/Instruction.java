package cc.mi.engine.aop;

import java.lang.reflect.Method;

import cc.mi.engine.annotation.parser.AnnotationMethod;

public class Instruction extends MethodInterceptor {
	private Enhance enhance;
	
	public Instruction() {
		enhance = new Enhance();
	}
	
	public Object instruct(Class<?> superClazz) {
		return enhance.create(superClazz, this);
	}
	
	@Override
	public Object intercept(Object proxy, Method method, Object[] args) throws Throwable {
		boolean checked = AnnotationMethod.check(method, args);
		if (!checked) return null;
		Object ret = method.invoke(proxy, args);
		//TODO after
		return ret;
	}
}
