package cc.mi.logical.mappin;

import cc.mi.engine.db.annotation.AutoIncrement;
import cc.mi.engine.db.annotation.Column;
import cc.mi.engine.db.annotation.Table;

@Table(name="account", pks={"userId"}, keys={"account"}, comment = "账号表")
public class Account {
	@AutoIncrement
	@Column(name="userId", nullable = false, defaultValue="", comment = "角色惟一id")
	private int userId;
	
	@Column(name="account", nullable = false, defaultValue="", comment = "账号")
	private String account;
	
	@Column(name="password", nullable = false, defaultValue="", comment = "密码")
	private String password;

	public int getUserId() {
		return userId;
	}

	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}