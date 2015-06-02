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
		String packet = StringUtils.EMPTY;
		while (true) {
			try {
				if (!channel.isConnected()) {
					channel.close();
					// TOTO 并且从服务器下线
					break;
				}
				packet = channel.getReader().readLine();
				if (StringUtils.isBlank(packet)) {
					log.error("Blank Packet : " + packet);
					continue;
				}
				InPacket inPacket = null;
				if (StringUtils.startsWith(packet, PacketAlias.IQ_NAME)) {
					inPacket = new InIQ().fromXML(packet);
				}
				if (StringUtils.startsWith(packet, PacketAlias.MESSAGE_NAME)) {
					inPacket = new InMessage().fromXML(packet);
				}
				if (StringUtils.startsWith(packet, PacketAlias.PRESENCE_NAME)) {
					inPacket = new InPresence().fromXML(packet);
				}
				if (StringUtils.startsWith(packet, PacketAlias.REGISTER_NAME)) {
					inPacket = new InRegister().fromXML(packet);
				}
				if (StringUtils.startsWith(packet, PacketAlias.SUBSCRIBE_NAME)) {
					inPacket = new InSubscribe().fromXML(packet);
				}
				if (inPacket == null) {
					log.error("Parse InPacket form Data Packet Error.");
					continue;
				}
				OutPacket outPacket = processor.process(inPacket);
				channel.write(outPacket);
			} catch (Throwable e) {
				log.error("ServerHandler Process Channel Packet Error.", e);
			}
		}
	}
}