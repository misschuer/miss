package cc.mi.engine.disruptor;

import com.lmax.disruptor.EventTranslator;

public class TransactionEventTranslator implements EventTranslator<Domain> {
	@Override
	public void translateTo(Domain domain, long sequence) {
		domain.setId((int) sequence);
	}
}