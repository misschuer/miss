package cc.mi.logical.net;

import java.util.List;

import cc.mi.logical.coder.Coder;
import cc.mi.system.Dispatcher;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ServerDefrager extends ByteToMessageDecoder {
	
	/**
	 * 长度4bytes(包括前面包头, 后面的内容) + 内容(长度-4)bytes (这只是个demo)
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
							List<Object> out) throws Exception {
		int opcode = in.getInt(in.readerIndex());
		if (!Dispatcher.containsHandler(opcode)) {
			throw new RuntimeException(String.format("opcode:%d 未设置对应handler", opcode));
		}
		Coder object = Dispatcher.handlers[opcode].newCoder();
		object.onDecode(in);
		out.add(object);
	}
}