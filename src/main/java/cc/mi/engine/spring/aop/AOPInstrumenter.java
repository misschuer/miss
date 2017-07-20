package cc.mi.engine.spring.aop;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import cc.mi.engine.annotation.parser.AnnotationMethod;

public class AOPInstrumenter implements MethodInterceptor {
	private Enhancer enhancer = new Enhancer();

	public Object getInstrumentedClass(Class<?> clz) {
		enhancer.setSuperclass(clz);
		enhancer.setCallback(this);
		return enhancer.create();
	}
	
	public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		boolean checked = AnnotationMethod.check(method, args);
		if (!checked) return null;
		Object result = proxy.invokeSuper(o, args);
		return result;
	}
	
	public void aa(int t) {
		boolean checked = t != 0;
		if (!checked) {
//			System.out.println("");
			return;
		}
//		System.out.println();
	}
}