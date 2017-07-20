package cc.mi.logical.domain.encoder;

import cc.mi.logical.coder.AbstractCoder;
import cc.mi.system.OutboundOpcode;
import io.netty.buffer.ByteBuf;

public class NotifyCreateRoleResult extends AbstractCoder {

	public NotifyCreateRoleResult() {
		super(OutboundOpcode.NOTIFY_CREATEROLE_RESULT);
	}

	@Override
	public void encode(ByteBuf buffer) {
	}

	@Override
	public void decode(ByteBuf buffer) {
	}
}
