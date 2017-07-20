package cc.mi.logical.net;

import cc.mi.logical.coder.Coder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ServerEnfrager extends MessageToByteEncoder<Coder> {
	@Override
	protected void encode(ChannelHandlerContext ctx, Coder msg, ByteBuf out) throws Exception {
		// 这样才能避免跨线程创建ByteBuf和销毁ByteBuf(全部在IO线程中完成)
		System.out.println("发送消息:" + msg.getOpcode());
		msg.onEncode(out);
	}
}