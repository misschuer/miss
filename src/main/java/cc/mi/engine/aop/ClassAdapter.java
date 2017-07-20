package cc.mi.engine.aop;

import java.util.LinkedList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import cc.mi.engine.asm.Primitive;
import cc.mi.engine.utils.StringUtils;

/**
 * 外面的对象如何夹在到asm里面去
 * @author mi
 *
 */
public class ClassAdapter extends ClassVisitor {
	private final List<MethodVisit> methodVisitList;
	private static final String PROXY_LDC = "Proxy";
	private String owner;
	
	public ClassAdapter(ClassVisitor cv) {
		super(Opcodes.ASM5, cv);
		methodVisitList = new LinkedList<>();
	}
	
	public void visit(	final int version, final int access, 
						final String name, final String signature, 
						final String superName, final String[] interfaces) {
		this.owner = name + Generator.INNER_CLASS;
		super.visit(version, access, owner, signature,
				name, interfaces);
		{
			FieldVisitor fv = super.visitField(Opcodes.ACC_PRIVATE, "interceptor", "Lcc/mi/engine/aop/MethodInterceptor;", null, null);
			fv.visitEnd();
		}

		{
            MethodVisitor mv = super.visitMethod(Opcodes.ACC_PUBLIC, "<init>",
                          												"()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, name, "<init>", "()V", false);
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
	}
	
	public FieldVisitor visitField(int access, String name, String desc,
	            String signature, Object value) {
        return null;
    }
	
	public MethodVisitor visitMethod(final int access, final String name,
									final String desc, final String signature, 
									final String[] exceptions) {
		if (name.equals("<init>") || name.startsWith("set") || name.startsWith("get"))
			return null;
		if ((access & Opcodes.ACC_STATIC) == 0)
			methodVisitList.add(new MethodVisit(access, name, desc, signature, exceptions));
		return super.visitMethod(access, name+PROXY_LDC, desc, signature, exceptions);
	}
	
	public void visitEnd() {
		// 这里加方法
		for (MethodVisit methodVisit : methodVisitList) {
			MethodVisitor mv = super.visitMethod(methodVisit.getAccess(), methodVisit.getName(),
					methodVisit.getDesc(), methodVisit.getSignature(), methodVisit.exceptions);
			Type[] types = Type.getArgumentTypes(methodVisit.getDesc());
			Type returnType = Type.getReturnType(methodVisit.getDesc());
			int star = (methodVisit.getAccess() & Opcodes.ACC_STATIC) == 0 ? 1 : 0;
			int var = star;
			int slot = star;
			for (int i = 0; i < types.length; ++i) {
				slot += types[i].getSize();
			}
			mv.visitCode();
			mv.visitLdcInsn(methodVisit.getName()+PROXY_LDC);
			mv.visitVarInsn(Opcodes.ASTORE, slot);
			// args
			mv.visitIntInsn(Opcodes.BIPUSH, types.length);
			mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
			mv.visitVarInsn(Opcodes.ASTORE, slot+1);
			for (int i = 0 ; i < types.length; ++ i) {
				Type type = types[ i ];
				String typeDescriptor = type.getDescriptor();
				mv.visitVarInsn(Opcodes.ALOAD, slot+1);
				mv.visitIntInsn(Opcodes.BIPUSH, i);
				int load = Opcodes.ALOAD;
				if (Primitive.containsLoad(typeDescriptor)) {
					load = Primitive.opcodeLoad(typeDescriptor);
				}
				mv.visitVarInsn(load, var);
				var += type.getSize();
				primitiveValueOf(mv, typeDescriptor);
				mv.visitInsn(Opcodes.AASTORE);
			}
			// classArgs
			mv.visitIntInsn(Opcodes.BIPUSH, types.length);
			mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Class");
			mv.visitVarInsn(Opcodes.ASTORE, slot+2);
			for (int i = 0 ; i < types.length; ++ i) {
				Type type = types[ i ];
				String typeDescriptor = type.getDescriptor();
				mv.visitVarInsn(Opcodes.ALOAD, slot+2);
				mv.visitIntInsn(Opcodes.BIPUSH, i);
				if (typeDescriptor.length() == 1) {
					String owner = Primitive.getOwner(typeDescriptor);
					mv.visitFieldInsn(Opcodes.GETSTATIC, owner, "TYPE", "Ljava/lang/Class;");
				}
				else {
					mv.visitLdcInsn(Type.getType(typeDescriptor));
				}
				mv.visitInsn(Opcodes.AASTORE);
			}
			// invoke method
			Label l0 = new Label();
			Label l1 = new Label();
			Label l2 = new Label();
			mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Throwable");
			mv.visitLabel(l0);
			
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
			mv.visitVarInsn(Opcodes.ALOAD, slot);
			mv.visitVarInsn(Opcodes.ALOAD, slot+2);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
			mv.visitVarInsn(Opcodes.ASTORE, slot+3);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitFieldInsn(Opcodes.GETFIELD, this.owner, "interceptor", "Lcc/mi/engine/aop/MethodInterceptor;");
			// 调用实际方法
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, slot+3);
			mv.visitVarInsn(Opcodes.ALOAD, slot+1);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "cc/mi/engine/aop/MethodInterceptor", "intercept", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", false);
			mv.visitLabel(l1);
			parseReturn(mv, returnType);
			mv.visitLabel(l2);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Throwable"});
			mv.visitVarInsn(Opcodes.ASTORE, slot+3);
			mv.visitVarInsn(Opcodes.ALOAD, slot+3);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "printStackTrace", "()V", false);
			mv.visitInsn(Opcodes.ACONST_NULL);
			parseReturn(mv, returnType);
			mv.visitMaxs(4, slot+4);
			mv.visitEnd();
		}
		super.visitEnd();
	}
	
	private void parseReturn(MethodVisitor mv, Type type) {
		String desc = type.getDescriptor();
		if ("V".equals(desc)) {
			mv.visitInsn(Opcodes.RETURN);
			return;
		}
		int opcode = Opcodes.ARETURN;
		if (Primitive.containsReturn(desc)) {
			opcode = Primitive.opcodeReturn(desc);
		}
		cast(mv, desc);
		toPrimitive(mv, desc);
		mv.visitInsn(opcode);
	}
	
	private void cast(MethodVisitor mv, String typeDescriptor) {
		// 数组类型的是本身
		String castType = typeDescriptor;
		if (typeDescriptor.length() == 1) {
			castType = Primitive.getOwner(typeDescriptor);
		}
		else if (!typeDescriptor.contains("[")) {
			castType = StringUtils.descriptorToClazzName(typeDescriptor);
		}
		mv.visitTypeInsn(Opcodes.CHECKCAST, castType);
	}
	
	private void toPrimitive(MethodVisitor mv, String typeDescriptor) {
		if (typeDescriptor.length() != 1)
			return;
		String owner = Primitive.getOwner(typeDescriptor);
		String[] dirs = owner.split("/");
		String typeString = StringUtils.uncapitalize(dirs[dirs.length-1]);
		dirs = null;
		if ("integer".equals(typeString))
			typeString = "int";
		else if ("character".equals(typeString))
			typeString = "char";
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, owner, typeString+"Value", "()" + typeDescriptor, false);
	}
	
	private void primitiveValueOf(MethodVisitor mv, String typeDescriptor) {
		if (typeDescriptor.length() == 1 && Primitive.containsType(typeDescriptor)) {
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, Primitive.getOwner(typeDescriptor), "valueOf", Primitive.getDescriptor(typeDescriptor), false);
		}
	}
	
	static final class MethodVisit {
		final int access; 
		final String name;
		final String desc; 
		final String signature; 
		final String[] exceptions;
		
		public MethodVisit(final int access, final String name,
				final String desc, final String signature, 
				final String[] exceptions) {
			this.access = access;
			this.name = name;
			this.desc = desc;
			this.signature = signature;
			this.exceptions = exceptions;
		}

		public int getAccess() {
			return access;
		}

		public String getName() {
			return name;
		}

		public String getDesc() {
			return desc;
		}

		public String getSignature() {
			return signature;
		}

		public String[] getExceptions() {
			return exceptions;
		}
	}
}