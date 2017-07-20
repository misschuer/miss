package cc.mi.engine.thread;

public class Task implements Runnable {
	Runnable runnable;
	
	public Task(Runnable runnable) {
		this.runnable = runnable;
	}
	
	@Override
	public void run() {
		this.runnable.run();
	}
}