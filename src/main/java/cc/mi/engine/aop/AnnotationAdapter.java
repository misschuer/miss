package cc.mi.engine.aop;

import java.lang.reflect.Array;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

public class AnnotationAdapter extends AnnotationVisitor {

	public AnnotationAdapter(AnnotationVisitor av) {
		super(Opcodes.ASM5, av);
	}

	public void visit(String name, Object value) {
		String pt = value.toString();
		if (value.getClass().isArray()) {
			pt = "[";
			int length = Array.getLength(value);
			for (int i = 0; i < length; ++ i) {
				if (i > 0) pt += ", ";
				pt += Array.get(value, i);
			}
			pt += "]";
		}
        System.out.println(name + " " + pt);
    }
	
	public void visitEnum(String name, String desc, String value) {
		System.out.println(name + " " + desc + " " + value);
    }
	
    public AnnotationVisitor visitAnnotation(String name, String desc) {
    	System.out.println("visitAnnotation:" + name + " = " + desc);
        return new AnnotationAdapter(av.visitAnnotation(name, desc));
    }
	
    public AnnotationVisitor visitArray(String name) {
    	System.out.println("visitArray:" + name);
    	return new AnnotationAdapter(av.visitArray(name));
    }
	
	public void visitEnd() {
		System.out.println("annotation visit end============================");
	}
}