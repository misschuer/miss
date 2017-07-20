package cc.mi.logical.domain.decoder;

import cc.mi.logical.coder.AbstractCoder;
import io.netty.buffer.ByteBuf;

public class EmptyArgument extends AbstractCoder {
	public static final EmptyArgument DEFAULT = new EmptyArgument();
	
	private EmptyArgument() {
		super(-1);
	}
	
	@Override
	public void encode(ByteBuf buffer) {}
	
	@Override
	public void decode(ByteBuf buffer) {}
}
