package cc.mi.engine.event.param;

import cc.mi.engine.event.manager.EventParam;

public class LevelUpEvent implements EventParam {
	public static final String LEVEL_UP_EVENT = "level_up_event";
	private final int level;
	
	public LevelUpEvent(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}