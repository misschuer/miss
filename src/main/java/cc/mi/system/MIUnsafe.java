package cc.mi.system;

public class MIUnsafe {
//	// Unsafe mechanics
//    public static final sun.misc.Unsafe UNSAFE;
////    private static final long nextOffset;
//    static {
//        try {
//            UNSAFE = getUnsafe();
////            Class<?> sk = null;
////            nextOffset = UNSAFE.objectFieldOffset
////                (sk.getDeclaredField("next"));
//        } catch (Exception e) {
//            throw new Error(e);
//        }
//    }
//
//    /**
//     * Returns a sun.misc.Unsafe.  Suitable for use in a 3rd party package.
//     * Replace with a simple call to Unsafe.getUnsafe when integrating
//     * into a jdk.
//     *
//     * @return a sun.misc.Unsafe
//     */
//    private static sun.misc.Unsafe getUnsafe() {
//        try {
//            return sun.misc.Unsafe.getUnsafe();
//        } catch (SecurityException se) {
//            try {
//                return java.security.AccessController.doPrivileged
//                    (new java.security
//                        .PrivilegedExceptionAction<sun.misc.Unsafe>() {
//                        public sun.misc.Unsafe run() throws Exception {
//                            java.lang.reflect.Field f = sun.misc
//                                .Unsafe.class.getDeclaredField("theUnsafe");
//                            f.setAccessible(true);
//                            return (sun.misc.Unsafe) f.get(null);
//                        }});
//            } catch (java.security.PrivilegedActionException e) {
//                throw new RuntimeException("Could not initialize intrinsics",
//                    e.getCause());
//            }
//        }
//    }

}
