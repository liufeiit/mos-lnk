package me.mos.ti.srv;

import me.mos.ti.packet.InIQ;
import me.mos.ti.packet.InMessage;
import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.InPresence;
import me.mos.ti.packet.InRegister;
import me.mos.ti.packet.InSubscribe;
import me.mos.ti.packet.OutIQ;
import me.mos.ti.packet.OutMessage;
import me.mos.ti.packet.OutPacket;
import me.mos.ti.packet.OutPresence;
import me.mos.ti.packet.OutRegister;
import me.mos.ti.packet.OutSubscribe;

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
	public <I extends InPacket> OutPacket process(I packet) throws Throwable {
		OutPacket outPacket = null;
		switch (packet.getType()) {
			case IQ :
				outPacket = processResponse((InIQ) packet);
				break;
			case Message :
				outPacket = processResponse((InMessage) packet);
				break;
			case Presence :
				outPacket = processResponse((InPresence) packet);
				break;
			case Register :
				outPacket = processResponse((InRegister) packet);
				break;
			case Subscribe :
				outPacket = processResponse((InSubscribe) packet);
				break;
			default :
				break;
		}
		return outPacket;
	}

	private OutSubscribe processResponse(InSubscribe subscribe) {
		OutSubscribe resp = new OutSubscribe();
		
		return resp;
	}

	private OutRegister processResponse(InRegister register) {
		OutRegister resp = new OutRegister();

		return resp;
	}

	private OutPresence processResponse(InPresence presence) {
		OutPresence resp = new OutPresence();

		return resp;
	}

	private OutMessage processResponse(InMessage message) {
		OutMessage resp = new OutMessage();

		return resp;
	}

	private OutIQ processResponse(InIQ iq) {
		OutIQ resp = new OutIQ();
		Channel channel = ChannelsMemory.channel(String.valueOf(iq.getMid()));
		if (channel == null || !channel.isConnected()) {
			resp.setOnline(false);
			return resp;
		}
		resp.setOnline(true);
		return resp;
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