package me.mos.ti.packet;

/**
 * 用户在线状态心跳检测消息报文.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 上午1:06:02
 */
public class IQ extends Packet {
	
	@Override
	public String toXML() {
		return "<iq mid=\"" + getMid() + "\"/>";
	}
}