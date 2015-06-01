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

	String getMID();

	/**
	 * 获取客户端网络地址
	 */
	java.net.InetAddress getPeerAddress();

	/**
	 * 读取消息
	 * 
	 * @param timeout
	 *            消息读取超时时间, 单位秒, 默认15秒
	 */
	<I extends InPacket> I read(int timeout);

	/**
	 * 写消息
	 */
	<O extends OutPacket> void write(O packet);

	/**
	 * 通道是否处于连接状态
	 */
	boolean isConnected();

	/**
	 * 关闭通道
	 */
	void close();
}