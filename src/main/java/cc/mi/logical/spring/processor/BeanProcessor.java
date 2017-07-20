package cc.mi.logical.spring.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class BeanProcessor implements BeanPostProcessor {

	private BeanProcessor() {}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String arg1)
			throws BeansException {
		
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String arg1)
			throws BeansException {
		
		return bean;
	}


}
