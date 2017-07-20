package cc.mi.engine.spring.aop.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.mi.engine.spring.aop.AOPInstrumenter;

public class Demo {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		@SuppressWarnings({ "unused", "resource" })
		ApplicationContext ctx = 
				new ClassPathXmlApplicationContext("bean.xml");//读取bean.xml中的内容
		
		AOPInstrumenter instrumenter = new AOPInstrumenter();
		A a = (A) instrumenter.getInstrumentedClass(A.class);
		a.show(1);
	}
}
