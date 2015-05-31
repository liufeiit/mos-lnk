package me.mos.ti.packet;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 用户在线状态心跳检测消息报文.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 上午1:06:02
 */
@XStreamAlias(PacketAlias.IQ_NAME)
public class OutIQ extends AbstractPacket {
	
	@XStreamAlias("online")
	@XStreamAsAttribute
	private boolean online;

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
}