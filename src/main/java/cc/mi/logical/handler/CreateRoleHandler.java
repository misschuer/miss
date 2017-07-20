package cc.mi.logical.handler;

import cc.mi.logical.GamePlayer;
import cc.mi.logical.coder.Coder;
import cc.mi.logical.domain.decoder.CreateRoleArgument;
import cc.mi.logical.domain.encoder.CreateRoleResult;
import cc.mi.logical.handler.owner.AbstractHandler;
import cc.mi.logical.handler.owner.AsyncThreadGroupCategory;
import io.netty.channel.Channel;

public class CreateRoleHandler extends AbstractHandler {
	
	public CreateRoleHandler() {
		super(AsyncThreadGroupCategory.PRIVATE_LOGICAL);
	}
	
	public Coder newCoder() {
		return new CreateRoleArgument();
	}
	
	@Override
	public void handle(GamePlayer player, Channel channel, Coder decoder) {
		if (player.isRoleCreated()) {
			return;
		}
		CreateRoleArgument ra = (CreateRoleArgument) decoder;
		player.setRoleName(ra.getName());
		
		CreateRoleResult result = new CreateRoleResult();
		result.setName(ra.getName());
		player.sendMsg(result);
	}
}
