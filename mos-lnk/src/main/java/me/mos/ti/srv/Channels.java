package me.mos.ti.srv;

import java.net.Socket;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import me.mos.ti.user.DefaultUserProvider;

import org.apache.commons.lang3.StringUtils;
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

	private static final ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap<String, Channel>(2000);

	private static final Timer TIMER = new Timer("Channel Active Monitor", false);

	public static void online(Channel channel) {
		String mid = channel.getMID();
		Channels.channels.put(mid, channel);
		try {
			if (StringUtils.isNotBlank(mid)) {
				DefaultUserProvider.getInstance().online(Long.parseLong(mid));
			}
		} catch (Exception e) {
			log.error("Online Error.", e);
		}
	}

	public static void offline(Channel channel) {
		Channels.offline(channel.getMID());
	}

	public static void offline(String mid) {
		Channels.channels.remove(mid);
		try {
			if (StringUtils.isNotBlank(mid)) {
				DefaultUserProvider.getInstance().offline(Long.parseLong(mid));
			}
		} catch (Exception e) {
			log.error("Offline Error.", e);
		}
	}

	public static Channel channel(String mid) {
		return Channels.channels.get(mid);
	}

	public static boolean isOnline(String mid) {
		Channel channel = channel(mid);
		return channel != null && channel.isConnected();
	}

	public static boolean isOnline(long mid) {
		return Channels.isOnline(String.valueOf(mid));
	}

	public static boolean isOnline(Channel channel) {
		return Channels.isOnline(channel.getMID());
	}

	public static Channel newChannel(Socket socket) {
		return new BoundChannel(socket);
	}

	@Override
	public void run() {
		try {
			TIMER.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						Enumeration<String> mids = Channels.channels.keys();
						while (mids.hasMoreElements()) {
							String mid = mids.nextElement();
							Channel channel = Channels.channels.get(mid);
							if (channel == null) {
								Channels.offline(mid);
							}
							if (!channel.isConnected()) {
								Channels.offline(channel);
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