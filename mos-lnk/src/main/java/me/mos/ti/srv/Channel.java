package me.mos.ti.srv;

import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.OutPacket;

/**
 * 表示一个客户端连接通道.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月1日 下午5:33:03
 */
public interface Channel {

	/**
	 * 获取客户端网络地址
	 */
	java.net.InetAddress getPeerAddress();

	/**
	 * 读消息
	 * 
	 * @param timeout
	 *            超时时间（毫秒），-1表示无限超时
	 * @return
	 */
	<T extends InPacket> T read(long timeout);
	
	/**
	 * 写消息
	 */
	<T extends OutPacket> void write(T packet);
	
	/**
	 * 通道是否处于连接状态
	 */
	boolean isConnected();
	
	/**
	 * 关闭通道
	 */
	void close();
}