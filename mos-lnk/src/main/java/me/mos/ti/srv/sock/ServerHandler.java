package me.mos.ti.srv.sock;

import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.OutPacket;
import me.mos.ti.parser.PacketParser;
import me.mos.ti.srv.channel.Channels;
import me.mos.ti.srv.channel.SockChannel;
import me.mos.ti.srv.process.ServerProcessor;

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

	private final SockChannel channel;

	private final ServerProcessor processor;

	private final PacketParser parser;

	public ServerHandler(SockChannel channel, ServerProcessor processor, PacketParser parser) {
		super();
		this.channel = channel;
		this.processor = processor;
		this.parser = parser;
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
				InPacket inPacket = parser.parse(packet);
				if (inPacket == null) {
					continue;
				}
				channel.setMID(inPacket.getMid());
				OutPacket outPacket = processor.process(channel, inPacket);
				if (outPacket == null) {
					continue;
				}
				channel.deliver(outPacket);
			} catch (Throwable e) {
			}
		}
	}
}