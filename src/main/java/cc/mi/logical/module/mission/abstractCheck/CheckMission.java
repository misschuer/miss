package cc.mi.logical.module.mission.abstractCheck;

import cc.mi.logical.GamePlayer;
import cc.mi.logical.module.mission.MissionData;
import cc.mi.logical.module.mission.param.MissionParameter;

public interface CheckMission {

	public void check(GamePlayer player, MissionData missionData, MissionParameter param);
	
	public void checkPrevFinish(GamePlayer player, MissionData MissionData);
	
	public int getCompleteTimes(int missionId);
	
	public void notifyProcess(GamePlayer player, MissionData MissionData);
}
