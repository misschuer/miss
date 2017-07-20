package cc.mi.logical.module.utils;

import cc.mi.engine.buffer.ByteBuffer;
import cc.mi.engine.redis.RedisCoder;

public class AccountCache implements RedisCoder {
	private String account;
	private String password;
	private int userId;
	private int usable = 1;
	
	public AccountCache() {}

	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public int getUserId() {
		return userId;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public void encode(ByteBuffer buffer) {
		buffer.writeString(this.account);
		buffer.writeString(this.password);
		buffer.writeInt(this.userId);
		buffer.writeInt(this.usable);
	}

	@Override
	public void decode(ByteBuffer buffer) {
		this.account = buffer.readString();
		this.password = buffer.readString();
		this.userId = buffer.readInt();
		this.usable = buffer.readInt();
	}

	public int getUsable() {
		return usable;
	}

	public void setUsable(int usable) {
		this.usable = usable;
	}
}
