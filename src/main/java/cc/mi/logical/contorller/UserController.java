package cc.mi.logical.contorller;

import cc.mi.engine.db.DBOperate;
import cc.mi.engine.db.utils.DBUtils;
import cc.mi.logical.GamePlayer;
import cc.mi.logical.manager.AsyncThreadGroupSwitch;
import cc.mi.logical.mappin.User;
import cc.mi.logical.module.user.GameUser;

public class UserController implements DBOperate {
	private GamePlayer player;
	private GameUser gameUser;
	
	public UserController(GamePlayer player) {
		this.player = player;
	}
	
	public void initUser(User user) {
		gameUser = new GameUser(user);
	}
	
	public void setRoleName(String name) {
		gameUser.getUser().setName(name);
		gameUser.notifyUpdate();
	}
	
	public String getRoleName() {
		return gameUser.getUser().getName();
	}

	@Override
	public void load() {
		User user = DBUtils.fecthOne(User.class, "userid", player.getUserId());
		gameUser = new GameUser(user);
	}

	@Override
	public void save() {
		AsyncThreadGroupSwitch.executeDB(player.getUserId(), gameUser);
	}
}