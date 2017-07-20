package cc.mi.engine.asm;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author mi
 */
public final class Primitive {
	private static final String Byte		= "java/lang/Byte";
	private static final String Short		= "java/lang/Short";
	private static final String Boolean		= "java/lang/Boolean";
	private static final String Float		= "java/lang/Float";
	private static final String Double		= "java/lang/Double";
	private static final String Long		= "java/lang/Long";
	private static final String Integer		= "java/lang/Integer";
	private static final String Character	= "java/lang/Character";
	
	private static final Map<String, String> primitiveType = new HashMap<>();
	private static final Map<Integer, Integer> primitiveSerial = new HashMap<>();
	private static final Map<String, Integer> primitiveLoad = new HashMap<>();
	private static final Map<String, Integer> primitiveReturn = new HashMap<>();
	static {
		// asm 描述符对应的类全名限定符
		primitiveType.put(Type.BYTE_TYPE.getDescriptor(), Byte);
		primitiveType.put(Type.SHORT_TYPE.getDescriptor(), Short);
		primitiveType.put(Type.BOOLEAN_TYPE.getDescriptor(), Boolean);
		primitiveType.put(Type.FLOAT_TYPE.getDescriptor(), Float);
		primitiveType.put(Type.DOUBLE_TYPE.getDescriptor(), Double);
		primitiveType.put(Type.LONG_TYPE.getDescriptor(), Long);
		primitiveType.put(Type.INT_TYPE.getDescriptor(), Integer);
		primitiveType.put(Type.CHAR_TYPE.getDescriptor(), Character);
		primitiveType.put("L"+Byte+";", Byte);
		primitiveType.put("L"+Short+";", Short);
		primitiveType.put("L"+Boolean+";", Boolean);
		primitiveType.put("L"+Float+";", Float);
		primitiveType.put("L"+Double+";", Double);
		primitiveType.put("L"+Long+";", Long);
		primitiveType.put("L"+Integer+";", Integer);
		primitiveType.put("L"+Character+";", Character);
		// asm 前6个的序号
		primitiveSerial.put(0, Opcodes.ICONST_0);
		primitiveSerial.put(1, Opcodes.ICONST_1);
		primitiveSerial.put(2, Opcodes.ICONST_2);
		primitiveSerial.put(3, Opcodes.ICONST_3);
		primitiveSerial.put(4, Opcodes.ICONST_4);
		primitiveSerial.put(5, Opcodes.ICONST_5);
		// asm 描述符对应的load
		primitiveLoad.put(Type.BYTE_TYPE.getDescriptor(), Opcodes.ILOAD);
		primitiveLoad.put(Type.SHORT_TYPE.getDescriptor(), Opcodes.ILOAD);
		primitiveLoad.put(Type.BOOLEAN_TYPE.getDescriptor(), Opcodes.ILOAD);
		primitiveLoad.put(Type.FLOAT_TYPE.getDescriptor(), Opcodes.FLOAD);
		primitiveLoad.put(Type.DOUBLE_TYPE.getDescriptor(), Opcodes.DLOAD);
		primitiveLoad.put(Type.LONG_TYPE.getDescriptor(), Opcodes.LLOAD);
		primitiveLoad.put(Type.INT_TYPE.getDescriptor(), Opcodes.ILOAD);
		primitiveLoad.put(Type.CHAR_TYPE.getDescriptor(), Opcodes.ILOAD);
		// asm 描述符对应的return
		primitiveReturn.put(Type.BYTE_TYPE.getDescriptor(), Opcodes.IRETURN);
		primitiveReturn.put(Type.SHORT_TYPE.getDescriptor(), Opcodes.IRETURN);
		primitiveReturn.put(Type.BOOLEAN_TYPE.getDescriptor(), Opcodes.IRETURN);
		primitiveReturn.put(Type.FLOAT_TYPE.getDescriptor(), Opcodes.FRETURN);
		primitiveReturn.put(Type.DOUBLE_TYPE.getDescriptor(), Opcodes.DRETURN);
		primitiveReturn.put(Type.LONG_TYPE.getDescriptor(), Opcodes.LRETURN);
		primitiveReturn.put(Type.INT_TYPE.getDescriptor(), Opcodes.IRETURN);
		primitiveReturn.put(Type.CHAR_TYPE.getDescriptor(), Opcodes.IRETURN);
	}
	
	final String owner;
	final String descriptor;
    
    private Primitive(String owner, String type) {
    	this.owner = owner;
    	Void g = null;
    	System.out.println(g);
    	this.descriptor  =  String.format("(%s)L%s;", type, owner);
    }
    
    /**
     * 获得需要转换的基本类型对象类的限定符
     * @param typeDescriptor
     * @return
     */
    public static String getOwner(String typeDescriptor) {
    	return primitiveType.get(typeDescriptor);
    }
    
    public static boolean containsType(Object key) {
    	return primitiveType.containsKey(key);
    }
    /**
     * 获得需要转换的基本类型对象类的描述符
     * @param typeDescriptor
     * @return
     */
    public static String getDescriptor(String typeDescriptor) {
    	String owner = getOwner(typeDescriptor);
    	return String.format("(%s)L%s;", typeDescriptor, owner);
    }
    
    /**
     * 获得iconst的opcode
     * @param key
     * @return
     */
    public static int opcodeICONST(Object key) {
    	ensureContainsKey(primitiveSerial, key);
    	return primitiveSerial.get(key);
    }
    
    /**
     * 获得各类型load的opcode
     * @param key
     * @return
     */
    public static int opcodeLoad(Object key) {
    	return primitiveLoad.get(key);
    }
    
    public static boolean containsLoad(Object key) {
    	return primitiveLoad.containsKey(key);
    }
    
    public static int opcodeReturn(Object key) {
    	return primitiveReturn.get(key);
    }
    
    public static boolean containsReturn(Object key) {
    	return primitiveReturn.containsKey(key);
    }
    
    private static final void ensureContainsKey(Map<?, ?> hash, Object key) {
    	if(!hash.containsKey(key))
    		throw new RuntimeException(String.format("不存在key:%s", key.toString()));
    }

	public String getOwner() {
		return owner;
	}
	
	public String getDescriptor() {
		return descriptor;
	}
}
