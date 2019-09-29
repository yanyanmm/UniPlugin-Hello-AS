package com.gensee.fastsdk;

import android.content.Context;

import com.gensee.common.ServiceType;
import com.gensee.entity.InitParam;
import com.gensee.fastsdk.core.GSFastConfig;
import com.gensee.fastsdk.core.GSLive;
import com.gensee.fastsdk.core.GenseeVod;
import com.gensee.fastsdk.entity.OfflinePlayParam;
import com.gensee.fastsdk.util.PreferUtil;
import com.gensee.fastsdk.util.ResManager;
import com.gensee.rtlib.ChatResource;

public class GenseeLive {
	private static boolean isResourceInit = false;

	/**
     * @param context 传入的上下文
     * @param isPublishMode
     * 是否是发布端，true=发布端，false=观看端
     * @param domain 设置域名
	 * <p>若一个url为http://test.gensee.com/site/webcast   域名是“test.gensee.com”
	 * 若一个url为http://test.gensee.com/site/training   域名是“test.gensee.com”</p>
     * @param number 直播或点播编号<p>设置对应编号，如果是点播则是点播编号，是直播便是直播编号。
	 * 请注意不要将id理解为编号。
	 * 作用等价于id，但不是id。有id可以不用编号，有编号可以不用id</p>
     * @param loginAccount 站点登录账号
     * <p> 设置站点认证账号 即登录站点的账号
	 * @param loginPwd 站点登录密码
	 * <p> 设置站点认证密码 即登录站点的密码
	 * 可选，如果后台设置直播需要登录或点播需要登录，那么登录密码要正确  且帐号同时也必须填写正确 </p>
     * @param nickName 昵称
     * <p>设置昵称  用于直播间显示或统计   一定要填写</p>
     * @param joinPwd 直播口令
     * <p>设置口令 即直播的保护密码
	 * 可选 如果后台设置了保护密码 请填写对应的口令</p>
     * @param k
     * 第三方认证K值
     * @param serviceType
     * 设置服务类型   webcast站点对应 WEBCAST   training 对应 TRAINING
     */

	/**
	 * 加入直播
	 *
	 * @param context   context 传入的上下文
	 * @param config    发布与观看的配置
	 * @param initParam 加入直播的参数，参考后台的设置
	 */
	public static void startLive(Context context, GSFastConfig config, InitParam initParam) {
		initConfiguration(context);
		ResManager.getIns().init(context);
		PreferUtil.initPref(context.getApplicationContext());
		PreferUtil.getIns().putInt(PreferUtil.KEY_SERVICE_TYPE, initParam.getServiceType() == ServiceType.TRAINING ? 1 : 0);
		GSLive.getIns().startLive(context, config, initParam);
	}

	/**
	 * Application 里面调用一下该函数，保证表情资源是初始化的，这里作用
	 *
	 * @param context
	 */
	public static void initConfiguration(Context context) {
		if (!isResourceInit) {
			ChatResource.initChatResource(context.getApplicationContext());
			isResourceInit = true;
		}
	}

	/**
	 * 观看点播（回放、录播），点播在线播放 提供InitParam 参数来播放网络上的点播或服务端的点播
	 *
	 * @param context Contetxt
	 * @param param   点播的参数，包含域名、编号或id、昵称、密码、serviceType等
	 */
	public static void startVod(Context context, InitParam param) {
		initConfiguration(context);
		GenseeVod.startVod(context, param);
	}

	/**
	 * 离线播放  用离线播放参数OfflinePlayParam传入后播放本地已经下载好的点播
	 *
	 * @param context Context
	 * @param param   离线播放参数，包含本地路径和标题
	 */
	public static void startVod(Context context, OfflinePlayParam param) {
		initConfiguration(context);
		GenseeVod.startVod(context, param);
	}

}
