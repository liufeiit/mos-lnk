package me.mos.ti.srv.sock;

import me.mos.ti.packet.InIQ;
import me.mos.ti.packet.InMessage;
import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.InPresence;
import me.mos.ti.packet.InRegister;
import me.mos.ti.packet.InSubscribe;
import me.mos.ti.packet.OutPacket;
import me.mos.ti.packet.Alias;
import me.mos.ti.srv.channel.Channel;
import me.mos.ti.srv.channel.Channels;
import me.mos.ti.srv.processor.ServerProcessor;

import org.apache.commons.lang3.StringUtils;
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

	private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);

	private static final String XMLSTART_TAG = "<";

	private final Channel channel;

	private final ServerProcessor processor;

	public ServerHandler(Channel channel, ServerProcessor processor) {
		super();
		this.channel = channel;
		this.processor = processor;
	}

	@Override
	public void run() {
		String packet = StringUtils.EMPTY;
		while (true) {
			try {
				if (!channel.isConnect()) {
					channel.close();
					Channels.offline(channel);
					break;
				}
				packet = channel.read();
				if (StringUtils.isBlank(packet)) {
					continue;
				}
				log.error("Original Incoming Packet : {}", packet);
				InPacket inPacket = null;
				if (StringUtils.startsWith(packet, XMLSTART_TAG + Alias.IQ_NAME)) {
					inPacket = InIQ.fromPacket(packet);
					channel.setMID(((InIQ) inPacket).getMid());
				}
				if (StringUtils.startsWith(packet, XMLSTART_TAG + Alias.MESSAGE_NAME)) {
					inPacket = InMessage.fromPacket(packet);
					channel.setMID(((InMessage) inPacket).getMid());
				}
				if (StringUtils.startsWith(packet, XMLSTART_TAG + Alias.PRESENCE_NAME)) {
					inPacket = InPresence.fromPacket(packet);
					channel.setMID(((InPresence) inPacket).getMid());
				}
				if (StringUtils.startsWith(packet, XMLSTART_TAG + Alias.REGISTER_NAME)) {
					inPacket = InRegister.fromPacket(packet);
				}
				if (StringUtils.startsWith(packet, XMLSTART_TAG + Alias.SUBSCRIBE_NAME)) {
					inPacket = InSubscribe.fromPacket(packet);
					channel.setMID(((InSubscribe) inPacket).getMid());
				}
				if (inPacket == null) {
					continue;
				}
				OutPacket outPacket = processor.process(channel, inPacket);
				if (outPacket == null) {
					continue;
				}
				channel.write(outPacket);
			} catch (Throwable e) {
			}
		}
	}
}