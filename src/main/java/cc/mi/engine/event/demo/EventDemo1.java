package cc.mi.engine.event.demo;

import cc.mi.engine.event.manager.EventListener;
import cc.mi.engine.event.manager.EventManager;
import cc.mi.engine.event.manager.EventParam;
import cc.mi.engine.event.param.LevelUpEvent;

public class EventDemo1 {

	public static void action1() {
		EventListener activeChapterListener = new EventListener() {
			@Override
			public void dispatched(EventParam eventParam) {
				System.out.println("升级解锁关卡");
			}
		};
		EventManager.addEventListener(EventDemo1.class, LevelUpEvent.LEVEL_UP_EVENT, activeChapterListener);
	}
}
