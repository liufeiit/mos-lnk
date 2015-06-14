package me.mos.ti.parser;

import me.mos.ti.packet.InIQ;
import me.mos.ti.packet.InMessage;
import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.InPresence;
import me.mos.ti.packet.InRegister;
import me.mos.ti.packet.Type;
import net.sf.json.JSONObject;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月13日 上午12:14:04
 */
public class JsonPacketParser implements PacketParser {

	@Override
	public InPacket parse(String packet) throws Throwable {
		JSONObject json = JSONObject.fromObject(packet);
		int t = json.getInt("type");
		Type type = Type.parse((byte) t);
		if (type == null) {
			throw new IllegalStateException("Error Type of Packet " + packet);
		}
		switch (type) {
		case IQ:
			return InIQ.fromPacket(packet);
		case Message:
			return InMessage.fromPacket(packet);
		case Presence:
			return InPresence.fromPacket(packet);
		case Register:
			return InRegister.fromPacket(packet);
		default:
			throw new IllegalStateException("Error Type of Packet " + packet);
		}
	}
}