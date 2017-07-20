package cc.mi.engine.spring.aop.demo2;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class AOPInstrumenter2 implements MethodInterceptor {
	private Enhancer enhancer = new Enhancer();

	public Object getInstrumentedClass(Class<?> className) {
		enhancer.setSuperclass(className);
		enhancer.setCallback(this);
		return enhancer.create();
	}

	public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println(method.toString());
		System.out.println("调用日志方法"+method.getName());
		
		Object result = proxy.invokeSuper(o, args);
		return result;
	}
}