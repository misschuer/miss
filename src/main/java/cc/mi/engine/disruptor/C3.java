package cc.mi.engine.disruptor;

import com.lmax.disruptor.EventHandler;

public class C3 implements EventHandler<Domain> {
	@Override
	public void onEvent(Domain domain, long sequence, boolean endOfBatch) throws Exception {
		System.out.println(String.format("C3:id[%d], sequence[%d]", domain.getId(), sequence));
	}
}
