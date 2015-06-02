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
				}
				if (StringUtils.startsWith(packet, XMLSTART_TAG + PacketAlias.MESSAGE_NAME)) {
					inPacket = new InMessage().fromXML(packet);
				}
				if (StringUtils.startsWith(packet, XMLSTART_TAG + PacketAlias.PRESENCE_NAME)) {
					inPacket = new InPresence().fromXML(packet);
				}
				if (StringUtils.startsWith(packet, XMLSTART_TAG + PacketAlias.REGISTER_NAME)) {
					inPacket = new InRegister().fromXML(packet);
				}
				if (StringUtils.startsWith(packet, XMLSTART_TAG + PacketAlias.SUBSCRIBE_NAME)) {
					inPacket = new InSubscribe().fromXML(packet);
				}
				if (inPacket == null) {
					log.error("Parse InPacket form Data Packet Error, Packet : " + packet);
					continue;
				}
				OutPacket outPacket = processor.process(inPacket);
				if (outPacket == null) {
					log.error("process InPacket to OutPacket Error, Packet : " + packet);
					continue;
				}
				channel.write(outPacket);
				System.err.println("已经回复：" + outPacket);
			} catch (Throwable e) {
				log.error("ServerHandler Process Channel Packet Error.", e);
			}
		}
	}
}