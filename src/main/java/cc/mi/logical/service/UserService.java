package cc.mi.logical.service;

import cc.mi.logical.mappin.User;

public class UserService {

	public static User createUser(String account, int userId) {
		User user = new User();
		user.setUserId(userId);
		user.setAccount(account);
		return user;
	}
}
