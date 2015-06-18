package me.mos.ti.srv.process;

import me.mos.ti.channel.Channel;
import me.mos.ti.packet.InIQ;
import me.mos.ti.packet.InMessage;
import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.InPresence;
import me.mos.ti.packet.InRegister;
import me.mos.ti.packet.InRevise;
import me.mos.ti.packet.OutPacket;
import me.mos.ti.srv.handler.IQHandler;
import me.mos.ti.srv.handler.MessageHandler;
import me.mos.ti.srv.handler.PresenceHandler;
import me.mos.ti.srv.handler.RegisterHandler;
import me.mos.ti.srv.handler.ReviseHandler;

/**
 * Lnk服务通道消息业务处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 上午12:44:18
 */
public class DefaultServerProcessor implements ServerProcessor {

	private IQHandler iqHandler;

	private MessageHandler messageHandler;

	private PresenceHandler presenceHandler;

	private RegisterHandler registerHandler;

	private ReviseHandler reviseHandler;

	public DefaultServerProcessor() {
		super();
		iqHandler = new IQHandler();
		messageHandler = new MessageHandler();
		presenceHandler = new PresenceHandler();
		registerHandler = new RegisterHandler();
		reviseHandler = new ReviseHandler();
	}

	@Override
	public <I extends InPacket> OutPacket process(Channel<?> channel, I packet) throws Throwable {
		OutPacket outPacket = null;
		switch (packet.getPacketType()) {
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
		case Revise:
			outPacket = reviseHandler.process(channel, (InRevise) packet);
			break;
		default:
			break;
		}
		return outPacket;
	}
}