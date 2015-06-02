package me.mos.ti.srv;

import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.OutPacket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lnk服务通道消息业务处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 上午12:44:18
 */
final class DefaultServerProcessor implements ServerProcessor {

	private final static Logger log = LoggerFactory.getLogger(DefaultServerProcessor.class);
	
	@Override
	public <I extends InPacket, O extends OutPacket> O process(I packet) throws Throwable {
		System.out.println(packet);
		return null;
	}

	@Override
	public void online(Channel channel) {
		
	}

	@Override
	public void offline(Channel channel) {
		
	}
}