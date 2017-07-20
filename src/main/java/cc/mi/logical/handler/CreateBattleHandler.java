package cc.mi.logical.handler;

import cc.mi.logical.GamePlayer;
import cc.mi.logical.coder.Coder;
import cc.mi.logical.domain.decoder.EmptyArgument;
import cc.mi.logical.handler.owner.AbstractHandler;
import cc.mi.logical.handler.owner.AsyncThreadGroupCategory;
import cc.mi.logical.utils.RoomIdGenerator;
import io.netty.channel.Channel;

/**
 * 就是战斗之前
 * @author misschuer
 *
 */
public class CreateBattleHandler extends AbstractHandler {
	
	public CreateBattleHandler() {
		super(AsyncThreadGroupCategory.PRIVATE_LOGICAL);
	}

	public Coder newCoder() {
		return EmptyArgument.DEFAULT;
	}
	
	@Override
	public void handle(GamePlayer player, Channel channel, Coder decoder) {
		player.setRoomId(RoomIdGenerator.generate());
	}
}