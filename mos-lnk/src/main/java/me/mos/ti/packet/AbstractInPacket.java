package me.mos.ti.packet;

import me.mos.ti.xml.XStreamParser;

/**
 * 上行通讯消息报文基类.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 上午12:52:46
 */
public abstract class AbstractInPacket implements InPacket {

	@Override
	@SuppressWarnings("unchecked")
	public <T extends InPacket> T fromXML(String xml) {
		return (T) XStreamParser.toObj(getClass(), xml);
	}

	/**
	 * 将消息格式化为可发送的XML格式
	 */
	@Override
	public String toString() {
		return XStreamParser.toXML(this);
	}
}