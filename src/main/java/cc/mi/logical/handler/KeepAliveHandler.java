package cc.mi.logical.handler;

import cc.mi.logical.GamePlayer;
import cc.mi.logical.coder.Coder;
import cc.mi.logical.domain.decoder.EmptyArgument;
import cc.mi.logical.domain.encoder.KeepAliveResult;
import cc.mi.logical.handler.owner.AbstractHandler;
import cc.mi.logical.handler.owner.AsyncThreadGroupCategory;
import io.netty.channel.Channel;

public class KeepAliveHandler extends AbstractHandler {

	public KeepAliveHandler() {
		
		super(AsyncThreadGroupCategory.PRIVATE_LOGICAL);
	}

	@Override
	public void handle(GamePlayer player, Channel channel, Coder decoder) {
		KeepAliveResult result = new KeepAliveResult();
		player.sendMsg(result);
	}

	@Override
	public Coder newCoder() {
		return EmptyArgument.DEFAULT;
	}

}
