package cc.mi;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Demo {
	private int a = 10;
	private volatile int b = 3;

	public static void main(String[] args) {
		MethodVisitor mv = new MethodVisitor(Opcodes.ASM5) {
			
		};
		mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {Opcodes.INTEGER}, 0, null);
		
		new Demo().swap();
	}
	
	public void swap() {
		a = b + 0 * (b = a);
		System.out.println(a + " " + b);
	}
}
