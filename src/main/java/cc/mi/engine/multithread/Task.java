package cc.mi.engine.multithread;
public final class Task implements Runnable {
	PaddedAtomicLong counter;
	long id;
	Runnable runnable;
	WaitStrategy strategy = new YieldWaitStrategy();

	public Task(PaddedAtomicLong counter, long id, Runnable runnable) {
		this.counter = counter;
		this.id = id;
		this.runnable = runnable;
	}
	
	public long sumPaddingToPreventOptimisation(final PaddedAtomicLong v) {
		long a = 
				v.getP0() + 
				v.getP1() + 
				v.getP2() + 
				v.getP3() + 
				v.getP4() + 
				v.getP5() + 
				v.getP6() + 
				1L;
			return a;
	}

	@Override
	public void run() {
		for(;;) {
			if (counter.get() !=  id) {
				strategy.waiting();
				continue;
			}
			runnable.run();
			if (!counter.compareAndSet(id, id+1)) {
				throw new RuntimeException(String.format("atomic = %d, id = %d", counter.get(), id));
			}
			return;
		}
	}

//	// Unsafe mechanics
//    private static final sun.misc.Unsafe UNSAFE;
//    static {
//        try {
//            UNSAFE = getUnsafe();
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
