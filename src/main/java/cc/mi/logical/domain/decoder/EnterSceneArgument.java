package cc.mi.logical.domain.decoder;

import cc.mi.logical.coder.AbstractCoder;
import io.netty.buffer.ByteBuf;

public class EnterSceneArgument extends AbstractCoder {

	public EnterSceneArgument() {
		super(-1);
	}

	@Override
	public void encode(ByteBuf buffer) {}

	@Override
	public void decode(ByteBuf buffer) {}

}
