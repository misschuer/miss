package cc.mi.engine.disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

public class Native {
	private static final int BUFFER_SIZE = 1 << 10;
	private static final int THREADS = 4;
	public static void main(String[] args) throws InterruptedException {
		// #1 create ringbuffer
		final RingBuffer<Domain> ringBuffer = RingBuffer.create(
				ProducerType.SINGLE, new EventFactory<Domain>() {
					@Override
					public Domain newInstance() {
						return new Domain();
					}
				}, 
				BUFFER_SIZE, 
				new YieldingWaitStrategy());
		
		// #2 create EventProcessor
		SequenceBarrier barrier = ringBuffer.newBarrier();
		BatchEventProcessor<Domain> batchEventProcessor = new BatchEventProcessor<Domain>(
				ringBuffer, 
				barrier, 
				new C1());
		
		// #3 start
		ringBuffer.addGatingSequences(batchEventProcessor.getSequence());
		
		ExecutorService executor = Executors.newFixedThreadPool(THREADS);
		executor.submit(batchEventProcessor);
		
		executor.submit(new Runnable() {
			public void run() {
				for (int i = 0; i < 1000; ++ i) {
					long seq = ringBuffer.next();
					ringBuffer.get(seq).setId((int) seq);
					ringBuffer.publish(seq);
				}
			}
		});
		Thread.sleep(10000);
		// #4 shutdown
		batchEventProcessor.halt();
		executor.shutdown();
	}
}
