package cc.mi.logical.handler.owner;

import cc.mi.logical.GamePlayer;
import cc.mi.logical.coder.Coder;
import io.netty.channel.Channel;

public interface Handler {
	public void handle(GamePlayer player, Channel channel, Coder decoder);
	
	public Coder newCoder();
	
	public AsyncThreadGroupCategory getCategory();
}
