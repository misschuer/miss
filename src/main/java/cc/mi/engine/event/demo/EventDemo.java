package cc.mi.engine.event.demo;

import cc.mi.engine.event.manager.EventListener;
import cc.mi.engine.event.manager.EventManager;
import cc.mi.engine.event.manager.EventParam;
import cc.mi.engine.event.param.LevelUpEvent;

public class EventDemo {
	
	public static void main(String[] args) {
		EventDemo1.action1();
		EventListener unlockBuildingListener = new EventListener() {
			@Override
			public void dispatched(EventParam eventParam) {
				System.out.println("升级解锁建筑");
			}
		};
		EventManager.addEventListener(EventDemo.class, LevelUpEvent.LEVEL_UP_EVENT, unlockBuildingListener);
		
		
		int level = 2;
		System.out.println("我升级了, 升到了" + level + "级");
		EventManager.dispatch(LevelUpEvent.LEVEL_UP_EVENT, new LevelUpEvent(level));
	}
}
