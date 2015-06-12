package me.mos.ti.parser;

import me.mos.ti.packet.InPacket;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月13日 上午12:02:29
 */
public interface PacketParser {
	InPacket parse(String packet) throws Throwable;
}