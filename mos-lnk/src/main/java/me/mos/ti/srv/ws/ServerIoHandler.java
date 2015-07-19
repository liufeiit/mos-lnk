package me.mos.ti.srv.ws;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.mos.ti.channel.Channels;
import me.mos.ti.channel.WsChannel;
import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.OutPacket;
import me.mos.ti.parser.JsonPacketParser;
import me.mos.ti.parser.PacketParser;
import me.mos.ti.srv.process.DefaultServerProcessor;
import me.mos.ti.srv.process.ServerProcessor;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2015年7月19日 上午8:49:26
 */
@ServerEndpoint("/lnk")
public final class ServerIoHandler {

	private static final Logger log = LoggerFactory.getLogger(ServerIoHandler.class);

	private static final String IO_CHANNEL = "IO-CHANNEL";

	private final ServerProcessor processor;

	private final PacketParser parser;

	public ServerIoHandler() {
		super();
		processor = new DefaultServerProcessor();
		parser = new JsonPacketParser();
	}

	@OnOpen
	public void onOpen(Session session) {
		WsChannel channel = Channels.newChannel(session);
		session.getUserProperties().put(IO_CHANNEL, channel);
	}

	@OnMessage
	public String onMessage(String message, Session session) {
		WsChannel channel = (WsChannel) session.getUserProperties().get(IO_CHANNEL);
		try {
			InPacket inPacket = parser.parse(message);
			channel.setChannelId(inPacket.getMid());
			OutPacket outPacket = processor.process(channel, inPacket);
			if (outPacket == null) {
				return StringUtils.EMPTY;
			}
			channel.deliver(outPacket);
		} catch (Throwable e) {
			log.error("ServerIoHandler MessageReceived Error.", e);
		}
		return StringUtils.EMPTY;
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		WsChannel channel = (WsChannel) session.getUserProperties().get(IO_CHANNEL);
		log.error("ServerIoHandler: Closing channel due to session Closed: " + channel);
		channel.close();
	}
}