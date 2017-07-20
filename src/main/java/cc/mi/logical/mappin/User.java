package cc.mi.logical.mappin;

import cc.mi.engine.db.annotation.Column;
import cc.mi.engine.db.annotation.Table;

@Table(name="user", pks={"userId"}, keys = {"account"}, comment = "角色表")
public class User {
	
	@Column(name="userId", nullable = false, defaultValue = "-1", comment = "角色id")
	private int userId;
	
	@Column(name="account", nullable = false, comment = "所属账号")
	private String account;
	
	@Column(name="name", nullable = false, comment = "角色名")
	private String name;
	
	@Column(name="level", nullable = false, defaultValue = "0", comment = "等级")
	private int level;
	
	@Column(name="exp", nullable = false, defaultValue = "0", comment = "经验")
	private int exp;
	
	@Column(name="vipLevel", nullable = false, defaultValue = "0", comment = "vip等级")
	private int vipLevel;
	
	@Column(name="registerTime", nullable = false, defaultValue = "0", comment = "注册时间")
	private int registerTime;
	
	@Column(name="logoutTime", nullable = false, defaultValue = "0", comment = "下线时间")
	private int logoutTime;
	
	@Column(name="usable", nullable = false, defaultValue = "1", comment = "是否可用")
	private int usable;
	
	@Column(name="forbidden", nullable = false, defaultValue = "0", comment = "是否禁言")
	private int forbidden;

	public int getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public int getExp() {
		return exp;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public int getRegisterTime() {
		return registerTime;
	}

	public int getLogoutTime() {
		return logoutTime;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public void setRegisterTime(int registerTime) {
		this.registerTime = registerTime;
	}

	public void setLogoutTime(int logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getUsable() {
		return usable;
	}

	public int getForbidden() {
		return forbidden;
	}

	public void setUsable(int usable) {
		this.usable = usable;
	}

	public void setForbidden(int forbidden) {
		this.forbidden = forbidden;
	}
}