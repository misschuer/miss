package cc.mi.engine.db.task;

import cc.mi.engine.thread.Task;

public class TaskFactory {
	public static Task createTask(Runnable runnable) {
		return new Task(runnable);
	}
}