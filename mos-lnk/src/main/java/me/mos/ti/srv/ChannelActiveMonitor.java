package me.mos.ti.srv;

import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月5日 下午9:38:13
 */
public class ChannelActiveMonitor implements Runnable {

	private final static Logger log = LoggerFactory.getLogger(ChannelActiveMonitor.class);

	private static final Timer TIMER = new Timer("Channel Active Monitor", false);

	@Override
	public void run() {
		try {
			TIMER.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						Enumeration<String> channels = Channels.channels();
						while (channels.hasMoreElements()) {
							String mid = channels.nextElement();
							Channel channel = Channels.channel(mid);
							if (channel == null) {
								Channels.offline(mid);
							}
							if (!channel.isConnect()) {
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