package cc.mi.logical.domain.decoder;

import cc.mi.logical.coder.AbstractCoder;
import cc.mi.logical.domain.UTFString;
import io.netty.buffer.ByteBuf;

public class LoginArgument extends AbstractCoder {
	private String account;
	private String password;
	
	public LoginArgument() {
		super(-1);
	}
	

	@Override
	public void encode(ByteBuf buffer) {
		UTFString.writeString(buffer, account);
		UTFString.writeString(buffer, password);
	}
	
	@Override
	public void decode(ByteBuf buffer) {
		this.account = UTFString.readString(buffer);
		this.password = UTFString.readString(buffer);
	}

	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}