package cc.mi.engine.aop;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;

import cc.mi.engine.asm.Primitive;

/**
 * primitive type to object
 * @author mi
 */
public class MethodAdapter extends MethodVisitor {
	private final int access;
	private final Type[] argsType;
	
	public MethodAdapter(MethodVisitor mv, int access, String desc) {
		super(Opcodes.ASM5, mv);
		argsType = Type.getArgumentTypes(desc);
		this.access = access;
	}
	
	public Object primitive(int a, byte b, short c, boolean d, float e, double f, long g, char h, Object o) {
//		String methodName = "ddd";
		Object[] args = new Object[9];
		args[ 0 ] = a;
		args[ 1 ] = b;
		args[ 2 ] = c;
		args[ 3 ] = d;
		args[ 4 ] = e;
		args[ 5 ] = f;
		args[ 6 ] = g;
		args[ 7 ] = h;
		args[ 8 ] = o;
//		Instruction a = new Instruction();
//		a.intercepter(proxy, method, args);
		return null;
	}
	
	private int getSlot(int index) {
		ensureIndex(index);
		int var = startSlot();
		for (int i = 0; i < index; ++i) {
			var += argsType[i].getSize();
		}
		return var;
	}
	
	private int startSlot() {
		return (access & Opcodes.ACC_STATIC) == 0 ? 1 : 0;
	}
	
	private void ensureIndex(int index) {
		if (index < 0 || index > argsType.length)
			throw new RuntimeException(String.format("index:%d不再范围[0, %d)", index, argsType.length));
	}
	
	/**
	 * parameters annotation parser
	 */
	public void visitCode() {
		super.visitCode();
		int slot = getSlot(argsType.length);
		// String methodName = Thread.currentThread().getStackTrace()[ 1 ].getMethodName();
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Thread", "currentThread", "()Ljava/lang/Thread;", false);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Thread", "getStackTrace", "()[Ljava/lang/StackTraceElement;", false);
		mv.visitInsn(Opcodes.ICONST_1);
		mv.visitInsn(Opcodes.AALOAD);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StackTraceElement", "getMethodName", "()Ljava/lang/String;", false);
		mv.visitVarInsn(Opcodes.ASTORE, slot);
		// args
		mv.visitIntInsn(Opcodes.BIPUSH, argsType.length);
		mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
		mv.visitVarInsn(Opcodes.ASTORE, slot+1);
		int var = startSlot();
		for (int i = 0 ; i < argsType.length; ++ i) {
			Type type = argsType[ i ];
			String typeDescriptor = type.getDescriptor();
			mv.visitVarInsn(Opcodes.ALOAD, slot+1);
			if (i < 6) mv.visitInsn(Primitive.opcodeICONST(i));
			else mv.visitIntInsn(Opcodes.BIPUSH, i);
			int load = Opcodes.ALOAD;
			if (Primitive.containsLoad(typeDescriptor)) {
				load = Primitive.opcodeLoad(typeDescriptor);
			}
			mv.visitVarInsn(load, var);
			var += type.getSize();
			if (typeDescriptor.length() == 1 && Primitive.containsType(typeDescriptor)) {
				mv.visitMethodInsn(Opcodes.INVOKESTATIC, Primitive.getOwner(typeDescriptor), "valueOf", Primitive.getDescriptor(typeDescriptor), false);
			}
			mv.visitInsn(Opcodes.AASTORE);
		}
		// invoke method
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
		mv.visitVarInsn(Opcodes.ALOAD, slot);
		mv.visitVarInsn(Opcodes.ALOAD, slot+1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "cc/mi/engine/annotation/parser/AnnotationMethod", "check", "(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Z", false);
		//TODO 返回值得判断
		mv.visitVarInsn(Opcodes.ASTORE, slot+2);
	}
	
	public void visitInsn(int opcode) {
        if (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) {
	        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "cc/mi/engine/aop/SecurityChecker", "after", "()V", false);
	    }
        if (opcode != Opcodes.RETURN) {
        	int slot = getSlot(argsType.length);
        	mv.visitVarInsn(Opcodes.ALOAD, slot+2);
        }
	    super.visitInsn(opcode);
	}
	
	public void visitFrame(int type, int nLocal, Object[] local, int nStack,
            Object[] stack) {

    }

    public void visitIntInsn(int opcode, int operand) {
        
    }

    public void visitVarInsn(int opcode, int var) {
        
    }

    public void visitTypeInsn(int opcode, String type) {
        
    }

    public void visitFieldInsn(int opcode, String owner, String name,
            String desc) {
        
    }

    public void visitMethodInsn(int opcode, String owner, String name,
            String desc, boolean itf) {
       
    }

    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm,
            Object... bsmArgs) {
    	
    }

    public void visitJumpInsn(int opcode, Label label) {
        
    }

    public void visitLabel(Label label) {
        
    }

    public void visitLdcInsn(Object cst) {
        
    }

    public void visitIincInsn(int var, int increment) {
    	
    }

    public void visitTableSwitchInsn(int min, int max, Label dflt,
            Label... labels) {
    	
    }

    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        
    }

    public void visitMultiANewArrayInsn(String desc, int dims) {
    	
    }

    public AnnotationVisitor visitInsnAnnotation(int typeRef,
            TypePath typePath, String desc, boolean visible) {
    	return null;
    }
}
