package cc.mi.system;

import cc.mi.engine.concurrent.ConcurrentHashMap;
import cc.mi.logical.GamePlayer;
import cc.mi.logical.handler.CreateBattleHandler;
import cc.mi.logical.handler.CreateRoleHandler;
import cc.mi.logical.handler.KeepAliveHandler;
import cc.mi.logical.handler.LoginHandler;
import cc.mi.logical.handler.owner.Handler;

/**
 *	[账号登录(注册)/创角/进入]单线程组(以account作为hash分配) 
 *	1> 账号登录(及注册, 如果已登录就顶号/或者不能登/或者2个都踢掉!), 返回角色选择界面[信息存到某个地方Map<String, List<Integer>>, account:userIdList],
 *		并记录{channelname:account}
 *	2> 创建角色(先判断账号登录情况!), [信息存到某个地方Map<String, List<Integer>>, account:userIdList],
 *	3> 选择角色进入场景, 通过userId进行确认
 *
 *	需要解决 [TODO登录账号读数据的时候, 突然下线], 需要加锁？ 还是放到同一线程么？
 */
public class Dispatcher {
	private static final int SIZE = 1 << 12;
	/** handler */
	public static final Handler[] handlers = new Handler[SIZE];
	
	/** 玩家缓存 FIXME 尽量减少同步*/
	private static final ConcurrentHashMap<Integer, GamePlayer> playerHash = new ConcurrentHashMap<>();
	
	static {
		handlers[InboundOpcode.LOGIN] = new LoginHandler();
		handlers[InboundOpcode.CREATE_ROLE] = new CreateRoleHandler();
		handlers[InboundOpcode.BATTLE_BEGIN] = new CreateBattleHandler();
		handlers[InboundOpcode.KEEP_ALIVE] = new KeepAliveHandler();
		
	}
	
	public static boolean containsHandler(int opcode) {
		return opcode >= 0 && opcode < handlers.length && handlers[opcode] != null;
	}
	
	public static GamePlayer getGamePlayer(int userId) {
		return playerHash.get(userId);
	}
	
	public static boolean containsGamePlayer(int userId) {
		return playerHash.containsKey(userId);
	}
	
	public static void setGamePlayer(int userId, GamePlayer player) {
		playerHash.put(userId, player);
	}
	
	public static GamePlayer removeGamePlayer(int userId) {
		return playerHash.remove(userId);
	}
}