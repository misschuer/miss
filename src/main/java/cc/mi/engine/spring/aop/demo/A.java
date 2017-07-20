package cc.mi.engine.spring.aop.demo;

import org.springframework.stereotype.Service;

import cc.mi.engine.annotation.utils.NotNone;
import cc.mi.engine.annotation.utils.Range;

@Service
public class A implements AI {
	
	public A() {
	}

	@Override
	public void show(@Range(a = 1, b = 999) @NotNone Integer a) {
		System.out.println("成功调用pp方法");
	}
}
