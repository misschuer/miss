package cc.mi.logical.manager;

import cc.mi.engine.db.DBState;
import cc.mi.engine.db.task.TaskFactory;
import cc.mi.engine.db.utils.DBUtils;
import cc.mi.engine.thread.AsyncThreadGroup;
import cc.mi.engine.thread.Task;
import cc.mi.logical.GamePlayer;
import cc.mi.logical.coder.Coder;
import cc.mi.logical.domain.decoder.LoginArgument;
import cc.mi.logical.domain.encoder.NotifyCreateRoleResult;
import cc.mi.logical.handler.owner.AsyncThreadGroupCategory;
import cc.mi.system.Dispatcher;
import cc.mi.system.NettyAttributeKey;
import io.netty.channel.Channel;

public class AsyncThreadGroupSwitch {
	/**
	 * 登录消息切换
	 * @param ctx
	 * @param player
	 */
	public static final void dealLoginMessage(final Channel channel, final Coder msg) {
		LoginArgument la = (LoginArgument) msg;
		int hash = la.getAccount().hashCode();
		final int opcode = la.getOpcode();
		AsyncThreadGroup.sumbitAccountLoginTask(hash, new Task(new Runnable() {
			public void run() {
				if (!channel.isActive()) {
					return;
				}
				Dispatcher.handlers[opcode].handle(null, channel, msg);
			}
		}));
	}
	
	/**
	 * 其他消息切换
	 * @param ctx
	 * @param player
	 */
	public static final void dealOtherMessage(final Channel channel, final Coder msg) {
		int userId = channel.attr(NettyAttributeKey.USER_ID).get();
		final int opcode = msg.getOpcode();
		final GamePlayer player = Dispatcher.getGamePlayer(userId);
		AsyncThreadGroupCategory category = Dispatcher.handlers[opcode].getCategory();
		Task task = new Task(new Runnable() {
			@Override
			public void run() {
				if (player == null || !channel.isActive() || player.getChannel() != channel) {
					return;
				}
				Dispatcher.handlers[opcode].handle(player, channel, msg);
			}
		});
		if (category == AsyncThreadGroupCategory.PRIVATE_LOGICAL) {
			AsyncThreadGroup.submitLogicalTask(player.getUserId(), task);
		}
		else if (category == AsyncThreadGroupCategory.PUBLIC_LOGICAL) {
			AsyncThreadGroup.submitPublicTask(task);
		}
		else if (category == AsyncThreadGroupCategory.ROOM_LOGICAL) {
			int roomId = player.getRoomId();
			if (roomId > 0) {
				AsyncThreadGroup.submitRoomTask(roomId, task);
			}
		}
	}
	
	/**
	 * 顶号操作
	 * @param channel
	 * @param player
	 */
	public static final void switchToPrivateLogicalGroupWithKick(final Channel channel, final GamePlayer player) {
		final int userId = player.getUserId();
		AsyncThreadGroup.submitLogicalTask(userId, new Task(new Runnable() {
			@Override
			public void run() {
				if (!channel.isActive())
					return;
				// 顶号, 先让原来的下线然后再上线
				player.disconnected();
				// 获得前一个人的channel
				Channel prevChannel = player.getChannel();
				// 设置当前玩家的channel
				player.setChannel(channel);
				// 让前一个玩家下线
				prevChannel.close();
				// 角色名字已被创建
				if (player.isRoleCreated()) {
					// 切换线程
					player.enter();
					return;
				}
				// 通知创建角色
				NotifyCreateRoleResult result = new NotifyCreateRoleResult();
				player.sendMsg(result);
			}
		}));
	}
	
	/**
	 * 登录成功切换
	 * @param ctx
	 * @param player
	 */
	public static final void switchToPrivateLogicalGroupWithLogined(final Channel channel, final GamePlayer player) {
		final int userId = player.getUserId();
		AsyncThreadGroup.submitLogicalTask(userId, new Task(new Runnable() {
			@Override
			public void run() {
				if (!channel.isActive())
					return;
				player.setChannel(channel);
				if (player.isRoleCreated()) {
					player.enter();
					return;
				}
				// 通知创建角色
				NotifyCreateRoleResult result = new NotifyCreateRoleResult();
				player.sendMsg(result);
			}
		}));
	}
	
	/**
	 * 下线处理切换
	 * @param ctx
	 * @param player
	 */
	public static final void switchToPrivateLogicalGroupWithLogout(final Channel channel, final GamePlayer player) {
		final int userId = player.getUserId();
		AsyncThreadGroup.submitLogicalTask(userId, new Task(new Runnable() {
			@Override
			public void run() {
				 if (player == null || player.getChannel() != channel)
					 return;
				 player.disconnected();
			}
		}));
	}
	
	/**
	 * 转到DB线程进行数据库操作
	 * @param userId
	 * @param stateObject
	 */
	public static void executeDB(int userId, final DBState stateObject) {
		AsyncThreadGroup.submitDBSaveTask(userId, TaskFactory.createTask(new Runnable() {
			@Override
			public void run() {
				Object data = stateObject.getMappinData();
				// 如果在插入的时候进行其他操作
				//	1> 更新操作 最多下次再跟新一遍, 这要保证内存数据在修改后才能置标志位
				//  2> 删除操作 又会执行deleteDB方法但是保证在同一条线程也无事
				if (stateObject.needInsert()) {
					stateObject.saved();
					DBUtils.insert(data);
				}
				// 如果在更新的时候进行其他操作
				// 删除操作 又会执行deleteDB方法但是保证在同一条线程也无事
				else if (stateObject.needUpdate()) {
					stateObject.saved();
					DBUtils.update(data);
				}
			}
		}));
	}
	
	/**
	 * 删除操作要立马执行不能等待
	 * @param userId
	 * @param stateObject
	 */
	public static void deleteDB(int userId, final DBState stateObject) {
		AsyncThreadGroup.submitDBSaveTask(userId, TaskFactory.createTask(new Runnable() {
			@Override
			public void run() {
				Object data = stateObject.getMappinData();
				stateObject.saved();
				DBUtils.delete(data);
			}
		}));
	}
}
