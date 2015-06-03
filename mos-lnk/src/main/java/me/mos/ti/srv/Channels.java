package me.mos.ti.srv;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端通道寄存器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午3:43:32
 */
public class Channels {

	private static final ConcurrentHashMap<String/** MID */, Channel/** 通道 */> channels = new ConcurrentHashMap<String, Channel>(2000);

	public static void online(Channel channel) {
		channels.put(channel.getMID(), channel);
	}

	public static void offline(Channel channel) {
		channels.remove(channel.getMID());
	}
	
	public static Channel channel(String mid) {
		return channels.get(mid);
	}
}