package me.mos.ti.srv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lnk服务通道事件处理句柄.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 上午12:08:50
 */
final class ServerHandler implements Runnable {
	
	private final static Logger log = LoggerFactory.getLogger(ServerHandler.class);

	private final Channel channel;
	
	private final ServerProcessor processor;
	
	public ServerHandler(Channel channel, ServerProcessor processor) {
		super();
		this.channel = channel;
		this.processor = processor;
	}

	@Override
	public void run() {
		try {
			processor.process(channel);
		} catch (Throwable e) {
			log.error("ServerHandler Process Channel Error.", e);
		}
	}
}