package cc.mi.logical.module.user;

import cc.mi.engine.db.DBState;
import cc.mi.logical.mappin.User;

public class GameUser extends DBState {
	private final User user;
	
	public GameUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	@Override
	public Object getMappinData() {
		return user;
	}
}