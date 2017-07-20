package cc.mi.engine.aop.test;

public class Sub extends A {
	/**
	 * ASM 获得方法名字
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/Thread", "currentThread", "()Ljava/lang/Thread;", false);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Thread", "getStackTrace", "()[Ljava/lang/StackTraceElement;", false);
		mv.visitInsn(ICONST_1);
		mv.visitInsn(AALOAD);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StackTraceElement", "getMethodName", "()Ljava/lang/String;", false);
		mv.visitVarInsn(ASTORE, 2);
	 * @param a
	 */
}
