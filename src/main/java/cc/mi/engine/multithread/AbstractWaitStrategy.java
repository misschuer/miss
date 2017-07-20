package cc.mi.engine.multithread;

public class AbstractWaitStrategy implements WaitStrategy {
	@Override
	public void waiting() {
		throw new RuntimeException("请重写AbstractStrategy的waiting");
	}
}
