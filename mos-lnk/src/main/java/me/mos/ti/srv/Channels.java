package me.mos.ti.srv;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端通道寄存器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午3:43:32
 */
public class Channels implements Runnable {

	private final static Logger log = LoggerFactory.getLogger(Channels.class);

	private static final ConcurrentHashMap<String/** MID */, Channel/** 通道 */> channels = new ConcurrentHashMap<String, Channel>(2000);

	private static final Timer TIMER = new Timer("Channel Active Monitor", false);

	public static void online(Channel channel) {
		channels.put(channel.getMID(), channel);
	}

	public static void offline(Channel channel) {
		channels.remove(channel.getMID());
	}

	public static Channel channel(String mid) {
		return channels.get(mid);
	}

	@Override
	public void run() {
		try {
			TIMER.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						for (String mid : Channels.channels.keySet()) {
							Channel channel = Channels.channels.get(mid);
							if (channel == null) {
								Channels.channels.remove(mid);
							}
							if (!channel.isConnected()) {
								Channels.channels.remove(mid);
							}
						}
					} catch (Exception e) {
						log.error("Channel Active Monitor Running Error.", e);
					}
				}
			}, 60000L, 300000L);
		} catch (Exception e) {
			log.error("Channel Active Monitor Starting Error.", e);
		}
	}
}