package cc.mi.logical;

import cc.mi.engine.db.DBOperate;
import cc.mi.logical.coder.Coder;
import cc.mi.logical.contorller.BagController;
import cc.mi.logical.contorller.UserController;
import cc.mi.logical.domain.encoder.EnterResult;
import cc.mi.logical.mappin.User;
import io.netty.channel.Channel;

public class GamePlayer implements DBOperate {
	private int userId;
	private Channel channel;
	private int roomId;
	
	private final UserController userController;
	private final BagController bagController;
	
	public GamePlayer(Channel channel, int userId) {
		this.channel = channel;
		this.userId = userId;
		
		userController = new UserController(this);
		bagController = new BagController(this);
	}
	
	public void enter() {
		EnterResult er = new EnterResult();
		sendMsg(er);
	}
	
	public boolean isRoleCreated() {
		String roleName = this.getRoleName();
		return roleName != null && !"".equals(roleName);
	}
	
	public void initUser(User user) {
		userController.initUser(user);
	}
	
	public void setRoleName(String name) {
		userController.setRoleName(name);
	}
	
	public String getRoleName() {
		return userController.getRoleName();
	}
	
	/** 下线操作 */
	public void disconnected() {
		this.save();
		this.channel = null;
	}
	
	public void sendMsg(Coder object) {
		if (channel != null && channel.isActive() && object != null) {
			channel.writeAndFlush(object);
		}
	}

	public int getUserId() {
		return userId;
	}

	@Override
	public void load() {
		userController.load();
		bagController.load();
	}

	@Override
	public void save() {
		userController.save();
		bagController.save();
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public Channel getChannel() {
		return channel;
	}
	


	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void reset() {
		this.roomId = 0;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public boolean isOnline() {
		return this.channel != null;
	}
}