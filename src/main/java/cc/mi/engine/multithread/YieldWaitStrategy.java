package cc.mi.engine.multithread;

public class YieldWaitStrategy extends AbstractWaitStrategy {

	@Override
	public void waiting() {
		Thread.yield();
	}

}
