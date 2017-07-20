package cc.mi.engine.disruptor;

public class Demo {
//	private static final int BUFFER_SIZE = 1 << 10;
//	private static final int THREADS = 4;
	public static void main(String[] args) throws InterruptedException {
//		ExecutorService executor = Executors.newFixedThreadPool(THREADS);
//		final Disruptor<Domain> disruptor = new Disruptor<>(
//				new EventFactory<Domain>() {
//					@Override
//					public Domain newInstance() {
//						return new Domain();
//					}
//				}, 
//				BUFFER_SIZE, 
//				executor, 
//				ProducerType.SINGLE, 
//				new BusySpinWaitStrategy());
//		
//		EventHandlerGroup<Domain> handlerGroup = 
//				disruptor.handleEventsWith(new C1(), new C2());
//		handlerGroup.then(new C3());
//		disruptor.start();
//		executor.submit(new Runnable() {
//			@Override
//			public void run() {
//				TransactionEventTranslator trans = new TransactionEventTranslator();
//				for (int i = 0; i < 100; ++ i) {
//					disruptor.publishEvent(trans);
//				}
//			}
//		});
//		Thread.sleep(10000);
//		executor.shutdown();
//		disruptor.shutdown();
	}
}
