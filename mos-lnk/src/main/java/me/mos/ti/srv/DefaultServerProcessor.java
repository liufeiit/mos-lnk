package me.mos.ti.srv;

import me.mos.ti.packet.InIQ;
import me.mos.ti.packet.InMessage;
import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.InPresence;
import me.mos.ti.packet.InRegister;
import me.mos.ti.packet.InSubscribe;
import me.mos.ti.packet.OutPacket;
import me.mos.ti.srv.handler.IQHandler;
import me.mos.ti.srv.handler.MessageHandler;
import me.mos.ti.srv.handler.PresenceHandler;
import me.mos.ti.srv.handler.RegisterHandler;
import me.mos.ti.srv.handler.SubscribeHandler;

/**
 * Lnk服务通道消息业务处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 上午12:44:18
 */
final class DefaultServerProcessor implements ServerProcessor {
	
	private IQHandler iqHandler;
	
	private MessageHandler messageHandler;
	
	private PresenceHandler presenceHandler;
	
	private RegisterHandler registerHandler;
	
	private SubscribeHandler subscribeHandler;

	public DefaultServerProcessor() {
		super();
		iqHandler = new IQHandler(this);
		messageHandler = new MessageHandler(this);
		presenceHandler = new PresenceHandler(this);
		registerHandler = new RegisterHandler(this);
		subscribeHandler = new SubscribeHandler(this);
	}

	@Override
	public <I extends InPacket> OutPacket process(I packet) throws Throwable {
		OutPacket outPacket = null;
		switch (packet.getType()) {
			case IQ :
				outPacket = iqHandler.process((InIQ) packet);
				break;
			case Message :
				outPacket = messageHandler.process((InMessage) packet);
				break;
			case Presence :
				outPacket = presenceHandler.process((InPresence) packet);
				break;
			case Register :
				outPacket = registerHandler.process((InRegister) packet);
				break;
			case Subscribe :
				outPacket = subscribeHandler.process((InSubscribe) packet);
				break;
			default :
				break;
		}
		return outPacket;
	}

	@Override
	public void online(Channel channel) {
		ChannelsMemory.online(channel);
	}

	@Override
	public void offline(Channel channel) {
		ChannelsMemory.offline(channel);
	}
}