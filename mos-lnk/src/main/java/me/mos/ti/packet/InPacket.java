package me.mos.ti.packet;

/**
 * 上行通讯消息报文.
 * 
 * @author 刘飞
 * 
 * @version 1.0.0
 * @since 2015年6月1日 下午2:03:13
 */
public interface InPacket<O extends OutPacket> extends Packet {

	<T extends InPacket<O>> T fromXML(String xml);
	
	O toOutPacket();
}