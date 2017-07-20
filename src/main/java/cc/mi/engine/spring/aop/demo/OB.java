package cc.mi.engine.spring.aop.demo;

import org.springframework.stereotype.Service;

@Service
public class OB implements AI {

	@Override
	public void show(Integer a) {
		System.out.println("具体类" + a);
	}

}
