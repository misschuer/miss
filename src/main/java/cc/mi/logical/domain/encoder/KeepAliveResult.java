package cc.mi.logical.domain.encoder;

import cc.mi.logical.coder.AbstractCoder;
import cc.mi.system.OutboundOpcode;
import io.netty.buffer.ByteBuf;

public class KeepAliveResult extends AbstractCoder {
	public KeepAliveResult() {
		super(OutboundOpcode.KEEP_ALIVE_RESULT);
	}

	@Override
	public void encode(ByteBuf buffer) {}

	@Override
	public void decode(ByteBuf buffer) {}
}
