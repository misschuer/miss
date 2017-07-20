package cc.mi.engine.aop;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import cc.mi.engine.utils.ReflectUtils;

public class Enhance {
	private static GeneratorClassLoader classLoader = 
			new GeneratorClassLoader();
	private static final Map<Class<?>, Class<?>> aopHash = new HashMap<>();
	public Enhance() {}
	
	private static class GeneratorClassLoader extends ClassLoader {
		public Class<?> defineClassFromClassFile(String className,
								byte[] b) throws ClassFormatError {
			return defineClass(className, b, 0, b.length);
		}
	}

	public Object create(Class<?> superClazz, MethodInterceptor interceptor) {
		ensureNotNone(superClazz);
		ensureNotNone(interceptor);
		try {
			if (!aopHash.containsKey(superClazz)) {
				ClassReader cr = new ClassReader(superClazz.getName());
				ClassWriter cw = new ClassWriter(0);
				ClassVisitor classVisitor = new ClassAdapter(cw);
				cr.accept(classVisitor, ClassReader.SKIP_DEBUG);
				byte[] data = cw.toByteArray();
				File file = new File("/home/mi/Documents/asm/" + superClazz.getSimpleName() + Generator.INNER_CLASS + ".class");
		        FileOutputStream fout = new FileOutputStream(file);
		        fout.write(data);
		        fout.close();
		        Class<?> aopClass = classLoader.defineClassFromClassFile(
		        		superClazz.getName() + Generator.INNER_CLASS, data);
		        aopHash.put(superClazz, aopClass);
			}
			Object obj = aopHash.get(superClazz).newInstance();
			Field field = ReflectUtils.getField(obj.getClass(), "interceptor");
			field.setAccessible(true);
			field.set(obj, interceptor);
			return obj;
		} catch(Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void ensureNotNone(Object object) {
		if (object == null)
			throw new RuntimeException("对象不能为空");
	}
}