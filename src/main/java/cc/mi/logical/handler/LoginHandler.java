package cc.mi.logical.handler;

import cc.mi.engine.buffer.ByteBuffer;
import cc.mi.engine.db.utils.DBUtils;
import cc.mi.engine.redis.Redis;
import cc.mi.logical.GamePlayer;
import cc.mi.logical.coder.Coder;
import cc.mi.logical.domain.decoder.LoginArgument;
import cc.mi.logical.domain.encoder.LoginResult;
import cc.mi.logical.handler.owner.AbstractHandler;
import cc.mi.logical.handler.owner.AsyncThreadGroupCategory;
import cc.mi.logical.manager.AsyncThreadGroupSwitch;
import cc.mi.logical.mappin.Account;
import cc.mi.logical.mappin.User;
import cc.mi.logical.module.utils.AccountCache;
import cc.mi.logical.service.AccountService;
import cc.mi.logical.service.UserService;
import cc.mi.system.Dispatcher;
import cc.mi.system.LoginResultConstant;
import cc.mi.system.NettyAttributeKey;
import io.netty.channel.Channel;

public class LoginHandler extends AbstractHandler {
	
	public LoginHandler() {
		super(AsyncThreadGroupCategory.LOGIN);
	}

	@Override
	public Coder newCoder() {
		return new LoginArgument();
	}
	
	@Override
	public void handle(GamePlayer player, final Channel channel, Coder decoder) {
		// 已经登录的不能再次登录
		if (channel.hasAttr(NettyAttributeKey.USER_ID)) {
			return;
		}
		// 消息内容
		LoginArgument login = (LoginArgument) decoder;
		String account = login.getAccount();
		String password = login.getPassword();
		
		int ret = 0;
		// 从redis中获得账号信息
		AccountCache accountCache = this.getAccountCache(account);
		// 账号已经注册
		if (accountCache != null) {
			ret = LoginResultConstant.FORBIDDEN;
			// 账号可用
			if (accountCache.getUsable() == 1) {
				ret = LoginResultConstant.PASSWORD_ERROR;
				if (accountCache.getPassword().equals(password)) {
					ret = 0;
					final int userId = accountCache.getUserId();
					// 从内存中读取缓存数据
					player = Dispatcher.getGamePlayer(userId);
					if (player != null) {
						channel.attr(NettyAttributeKey.USER_ID).set(userId);
						AsyncThreadGroupSwitch.switchToPrivateLogicalGroupWithKick(channel, player);
						return;
					}
					else {
//						player = new GamePlayer(channel, accountCache.getUserId());
//						//FIXME 从缓存(guava或者ehcache)中读数据, 缓存没有读db然后存缓存, 缓存需要一个超时删除机制和定时同步数据机制
//						player.load();
//						Dispatcher.setGamePlayer(userId, player);
//						
//						channel.attr(NettyAttributeKey.USER_ID).set(userId);
//						// 切换线程
//						AsyncThreadGroupSwitch.switchToPrivateLogicalGroupWithLogined(channel, player);
					}
				}
			}
		}
		else {
			// 创建账号
			Account acct = AccountService.createAccount(account, password);
			final int userId = DBUtils.insert(acct);
			//FIXME 自增主键得自己设 后期自动设置
			acct.setUserId(userId);
			// 创建无名称角色
			User user = UserService.createUser(account, userId);
			DBUtils.insert(user);
			// 设置账号缓存
			this.setAccountCache(userId, account, password);
			// 初始化角色信息
			final GamePlayer gp = new GamePlayer(channel, userId);
			gp.initUser(user);
			Dispatcher.setGamePlayer(userId, player);
			
			// 设置登录标志
			channel.attr(NettyAttributeKey.USER_ID).set(userId);
			// 切换线程
			AsyncThreadGroupSwitch.switchToPrivateLogicalGroupWithLogined(channel, player);
		}
		
		if (ret != 0) {
			LoginResult result = new LoginResult();
			result.setRet(ret);
		}
	}
	
	/**
	 * 通过account获得账号信息
	 * @param account
	 * @return
	 */
	private AccountCache getAccountCache(String account) {
		AccountCache accountCache = null;
		byte[] bytes = Redis.getAccountCache(account);
		// 账号已经注册
		if (bytes != null) {
			// 解析账号信息
			accountCache = new AccountCache();
			ByteBuffer buffer = new ByteBuffer(bytes);
			accountCache.decode(buffer);
		}
		
		return accountCache;
	}
	
	private void setAccountCache(int userId, String account, String password) {
		AccountCache accountCache = new AccountCache();
		
		accountCache.setUserId(userId);
		accountCache.setAccount(account);
		accountCache.setPassword(password);
		
		ByteBuffer buffer = ByteBuffer.alloc();
		accountCache.encode(buffer);
		Redis.accountCache(account, buffer.getBytes());
	}
}