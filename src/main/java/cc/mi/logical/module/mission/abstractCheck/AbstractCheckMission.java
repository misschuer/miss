package cc.mi.logical.module.mission.abstractCheck;

import cc.mi.logical.GamePlayer;
import cc.mi.logical.module.mission.MissionData;
import cc.mi.logical.module.mission.param.MissionParameter;

public abstract class AbstractCheckMission implements CheckMission {
	
	private final int category;
	private final int seq;
	
	public AbstractCheckMission(final int category, final int seq) {
		this.category = category;
		this.seq = seq;
	}
	
	public void check(GamePlayer player, MissionData missionData, MissionParameter param) {
		
	}
	
	public void checkPrevFinish(GamePlayer player, MissionData missionData) {
		
	}
	
	public void notifyProcess(GamePlayer player, MissionData missionData) {
		
	}
	
	public abstract void check0(GamePlayer player, MissionData missionData, MissionParameter param);
	
	protected void updateMissionAndCheckFinish(GamePlayer player, int addTimes, MissionData missionData) {
		
	}

	@Override
	public int getCompleteTimes(int missionId) {
		return 1;
	}


	public int getCategory() {
		return category;
	}


	public int getSeq() {
		return seq;
	}

}
