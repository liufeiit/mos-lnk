package me.mos.ti.srv;

import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.OutPacket;

/**
 * Lnk服务通道消息业务处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 上午12:10:24
 */
public interface ServerProcessor {
	
	/**
	 * 处理数据包接收和分发.
	 */
	<I extends InPacket> OutPacket process(I packet) throws Throwable;
	
	/**
	 * 上线
	 */
	void online(Channel channel);
	
	/**
	 * 下线
	 */
	void offline(Channel channel);
}