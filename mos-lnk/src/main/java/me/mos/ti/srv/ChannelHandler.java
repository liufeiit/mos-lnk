package me.mos.ti.srv;

import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.OutPacket;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月1日 下午11:52:40
 */
public interface ChannelHandler<I extends InPacket, O extends OutPacket> {

	void process(I packet);
	
	void process(O packet);
}