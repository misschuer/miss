package cc.mi.logical.net;

import org.apache.log4j.Logger;

import cc.mi.logical.GamePlayer;
import cc.mi.logical.coder.Coder;
import cc.mi.logical.manager.AsyncThreadGroupSwitch;
import cc.mi.system.Dispatcher;
import cc.mi.system.InboundOpcode;
import cc.mi.system.NettyAttributeKey;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerInboundHandler extends SimpleChannelInboundHandler<Coder> {
	private static final Logger logger = Logger.getLogger(ServerInboundHandler.class);
	
	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		System.out.println("server建立连接" + System.currentTimeMillis());
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				ctx.close();
			}
			else if (event.state() == IdleState.WRITER_IDLE) {
				
			}
			else if (event.state() == IdleState.ALL_IDLE) {
				
			}
		}
	}
	
	@Override
	public void channelRead0(final ChannelHandlerContext ctx, final Coder msg) throws Exception {
		final int opcode = msg.getOpcode();
		logger.info("收到消息:" + opcode);
		
		switch(opcode) {
			case InboundOpcode.LOGIN:
				// 已经登录过了
				if (ctx.channel().hasAttr(NettyAttributeKey.USER_ID)) {
					return;
				}
				AsyncThreadGroupSwitch.dealLoginMessage(ctx.channel(), msg);
				return;
			default:
				// 未登录过
				if (!ctx.channel().hasAttr(NettyAttributeKey.USER_ID)) {
					return;
				}
				AsyncThreadGroupSwitch.dealOtherMessage(ctx.channel(), msg);
				return;
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.out.println("服务器关闭:");
		cause.printStackTrace();
		ctx.close();
	}
	
	 @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
		 // 确保登录流程走了
		 if (ctx.channel().hasAttr(NettyAttributeKey.USER_ID)) {
			 int userId = ctx.channel().attr(NettyAttributeKey.USER_ID).get();
			 GamePlayer player = Dispatcher.getGamePlayer(userId);
			 AsyncThreadGroupSwitch.switchToPrivateLogicalGroupWithLogout(ctx.channel(), player);
		 }
    }
}
