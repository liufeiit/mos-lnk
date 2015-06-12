package me.mos.ti.srv.channel;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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
public class Channels {

	private final static Logger log = LoggerFactory.getLogger(Channels.class);

	private static final ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap<String, Channel>(2000);

	public static Channel newChannel(Socket channel, String charset) {
		return new SockChannel(channel, charset);
	}

	public static Enumeration<String> channels() {
		return Channels.channels.keys();
	}

	public static List<String> channelList() {
		List<String> us = new ArrayList<String>();
		Enumeration<String> channels = Channels.channels();
		while (channels.hasMoreElements()) {
			us.add(channels.nextElement());
		}
		return us;
	}

	public static void online(Channel channel) {
		String mid = channel.getMID();
		if (StringUtils.isBlank(mid)) {
			log.error(channel + "'s MID is not Setting.");
			return;
		}
		try {
			Channels.channels.put(mid, channel);
			DefaultUserProvider.getInstance().online(Long.parseLong(mid));
		} catch (Exception e) {
			log.error("Online Error.", e);
		}

	}

	public static void offline(Channel channel) {
		String mid = channel.getMID();
		if (StringUtils.isBlank(mid)) {
			log.error(channel + "'s MID is not Setting.");
			return;
		}
		Channels.offline(mid);
	}

	public static void offline(String mid) {
		if (StringUtils.isBlank(mid)) {
			return;
		}
		try {
			Channels.channels.remove(mid);
			DefaultUserProvider.getInstance().offline(Long.parseLong(mid));
		} catch (Exception e) {
			log.error("Offline Error.", e);
		}
	}

	public static void offline(long mid) {
		Channels.offline(String.valueOf(mid));
	}

	public static Channel channel(String mid) {
		return Channels.channels.get(mid);
	}

	public static boolean isOnline(String mid) {
		Channel channel = Channels.channel(mid);
		return channel != null && channel.isConnect();
	}

	public static boolean isOnline(long mid) {
		return Channels.isOnline(String.valueOf(mid));
	}

	public static boolean isOnline(Channel channel) {
		String mid = channel.getMID();
		if (StringUtils.isBlank(mid)) {
			log.error(channel + "'s MID is not Setting.");
			return false;
		}
		return Channels.isOnline(mid);
	}
}