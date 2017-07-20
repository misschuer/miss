package cc.mi.engine.aop.test;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cc.mi.engine.annotation.utils.Min;
import cc.mi.engine.aop.Instruction;
import cc.mi.engine.aop.test.Drag.Color;

public class A {
	public A() {}
	
	public void aa(@Min(1) int b) {
		System.out.println(b);
	}
	
	public char gfd() {
		Character g = '0';
		return g;
	}
	
	public int gf() {
		return 0;
	}
	
	public Integer ffd() {
		return 0;
	}
	
	public void aa(A a) {}
	
	public Void ggg() {
		return null;
	}
	
	public void print(@Drag(ia=1, sa="a1", ca=A.class, iarr={2, 3, 4}, min = @Min(1), et = {ElementType.FIELD}, sarr={"6", "7", "8"}, carr={A.class, Demo.class}, color=Color.YELLOW) int a, Integer ia) {
		System.out.println("a print:" + a);
		
	}
	
	public List<Integer> gfgf(Void a) {
		List<Integer> list = new LinkedList<>();
		return list;
	}
	
	public Integer[] bf() {
		try {
			Method method = this.getClass().getDeclaredMethod("aa", A.class);
			Object[] gg = {null};
			method.invoke(this, gg);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer dd() {
		return null;
	}
	
	public static void main(String[] args) throws ClassFormatError, InstantiationException, IllegalAccessException, IOException {
		Instruction ins = new Instruction();
		A a = (A) ins.instruct(A.class);
		a.aa(1);
//		a.aa(0);
		a.aa(new A());
		System.out.println(Arrays.toString(a.bf()));
	}
}
