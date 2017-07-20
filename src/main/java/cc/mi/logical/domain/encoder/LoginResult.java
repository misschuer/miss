package cc.mi.logical.domain.encoder;

import cc.mi.logical.coder.AbstractCoder;
import cc.mi.system.OutboundOpcode;
import io.netty.buffer.ByteBuf;

public class LoginResult extends AbstractCoder {
	/**
	 * -2:账号被封
	 * -1:登录失败(密码不正确)
	 */
	private int ret = -1;
	
	public LoginResult() {
		super(OutboundOpcode.LOGIN_RESULT);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(ret);
	}
	
	@Override
	public void decode(ByteBuf buffer) {
		this.ret = buffer.readInt();
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}
}