package me.mos.ti.srv.sock;

import me.mos.ti.packet.InIQ;
import me.mos.ti.packet.InMessage;
import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.InPresence;
import me.mos.ti.packet.InRegister;
import me.mos.ti.packet.InSubscribe;
import me.mos.ti.packet.OutPacket;
import me.mos.ti.srv.sock.handler.IQHandler;
import me.mos.ti.srv.sock.handler.MessageHandler;
import me.mos.ti.srv.sock.handler.PresenceHandler;
import me.mos.ti.srv.sock.handler.RegisterHandler;
import me.mos.ti.srv.sock.handler.SubscribeHandler;

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
	
	private static final Logger log = LoggerFactory.getLogger(ServerProcessor.class);

	private IQHandler iqHandler;

	private MessageHandler messageHandler;

	private PresenceHandler presenceHandler;

	private RegisterHandler registerHandler;

	private SubscribeHandler subscribeHandler;

	public DefaultServerProcessor() {
		super();
		iqHandler = new IQHandler();
		messageHandler = new MessageHandler();
		presenceHandler = new PresenceHandler();
		registerHandler = new RegisterHandler();
		subscribeHandler = new SubscribeHandler();
	}

	@Override
	public <I extends InPacket> OutPacket process(Channel channel, I packet) throws Throwable {
		OutPacket outPacket = null;
		switch (packet.getType()) {
		case IQ:
			outPacket = iqHandler.process(channel, (InIQ) packet);
			break;
		case Message:
			outPacket = messageHandler.process(channel, (InMessage) packet);
			break;
		case Presence:
			outPacket = presenceHandler.process(channel, (InPresence) packet);
			break;
		case Register:
			outPacket = registerHandler.process(channel, (InRegister) packet);
			break;
		case Subscribe:
			outPacket = subscribeHandler.process(channel, (InSubscribe) packet);
			break;
		default:
			break;
		}
		log.debug("Incoming Packet : {} \nOutcoming Packet : {}", packet, outPacket);
		return outPacket;
	}
}