package me.mos.ti.srv;

import me.mos.ti.packet.InIQ;
import me.mos.ti.packet.InMessage;
import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.InPresence;
import me.mos.ti.packet.InRegister;
import me.mos.ti.packet.InSubscribe;
import me.mos.ti.packet.OutPacket;
import me.mos.ti.packet.PacketAlias;

import org.apache.commons.lang3.StringUtils;

/**
 * Lnk服务通道事件处理句柄.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 上午12:08:50
 */
final class ServerHandler implements Runnable {

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
				if (!channel.isConnected()) {
					channel.close();
					processor.offline(channel);
					break;
				}
				packet = channel.read();
				if (StringUtils.isBlank(packet)) {
					continue;
				}
				InPacket inPacket = null;
				if (StringUtils.startsWith(packet, XMLSTART_TAG + PacketAlias.IQ_NAME)) {
					inPacket = new InIQ().fromXML(packet);
					channel.setMID(((InIQ) inPacket).getMid());
				}
				if (StringUtils.startsWith(packet, XMLSTART_TAG + PacketAlias.MESSAGE_NAME)) {
					inPacket = new InMessage().fromXML(packet);
					channel.setMID(((InMessage) inPacket).getMid());
				}
				if (StringUtils.startsWith(packet, XMLSTART_TAG + PacketAlias.PRESENCE_NAME)) {
					inPacket = new InPresence().fromXML(packet);
					channel.setMID(((InPresence) inPacket).getMid());
				}
				if (StringUtils.startsWith(packet, XMLSTART_TAG + PacketAlias.REGISTER_NAME)) {
					inPacket = new InRegister().fromXML(packet);
				}
				if (StringUtils.startsWith(packet, XMLSTART_TAG + PacketAlias.SUBSCRIBE_NAME)) {
					inPacket = new InSubscribe().fromXML(packet);
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
			} catch (Throwable e) {}
		}
	}
}