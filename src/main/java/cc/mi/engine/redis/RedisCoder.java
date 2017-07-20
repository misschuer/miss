package cc.mi.engine.redis;

import cc.mi.engine.buffer.ByteBuffer;

public interface RedisCoder {
	public void encode(ByteBuffer buffer);
	
	public void decode(ByteBuffer buffer);
}
