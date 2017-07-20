package cc.mi.logical;

/**
 * 对登录的分析
 * 	通过 channel来达到有无在线的标志
 * 	一个账号登录时会判断
 * 	if (player存在内存中) {
 * 		// 这里无所谓前一个用户在干嘛
 * 		// 即使前一个用户正在下线 或者正在登录
 * 		if (如果player在线) {
 * 			让前一个用户下线
 * 			当前用户占用该player
 * 			转logical线程
 * 		}
 * 		else {
 * 			当前用户占用该player
 * 			转logical线程
 * 		}
 *  }
 *  else {
 *  	从数据库中load
 *  	转logical线程
 *  }
 *  
 * @author misschuer
 *
 */
public class MultiLoginAnalysis {

}
