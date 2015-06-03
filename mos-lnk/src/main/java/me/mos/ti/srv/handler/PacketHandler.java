package me.mos.ti.srv.handler;

import me.mos.ti.packet.OutPacket;
import me.mos.ti.packet.InPacket;

/**
 * 通讯数据报文处理.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:15:56
 */
public interface PacketHandler<I extends InPacket<?>, O extends OutPacket> {
	O process(I packet) throws Throwable;
}