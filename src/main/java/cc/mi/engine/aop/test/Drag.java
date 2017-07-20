package cc.mi.engine.aop.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cc.mi.engine.annotation.utils.Min;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Drag {
	int ia() default 0;
	ElementType[] et() default {};
	String sa() default "";
	Class<?> ca() default A.class;
	
	int[] iarr() default {};
	String[] sarr() default {};
	Class<?>[] carr() default {};
	
	Min min() default @Min(1);
	
	Color color() default Color.BLUE;
	enum Color {
		BLUE,
		RED,
		YELLOW
	};
}
