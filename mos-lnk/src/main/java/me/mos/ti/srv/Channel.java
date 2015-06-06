package me.mos.ti.srv;

import java.nio.channels.SocketChannel;

import me.mos.ti.packet.Packet;

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
	 * 获取用户通道ID
	 */
	String getMID();

	/**
	 * 注入用户通道ID
	 */
	Channel setMID(long mid);
	
	/**
	 * 获取通道的远程端口
	 */
	int getPort();
	
	/**
	 * 获取客户端网络地址
	 */
	java.net.InetAddress getPeerAddress();
	
	/**
	 * 获取内部原始通道
	 */
	SocketChannel getOriginalChannel();
	
	/**
	 * 读消息
	 */
	String read();

	/**
	 * 写消息
	 */
	void write(Packet packet);

	/**
	 * 通道是否处于连接状态
	 */
	boolean isConnect();

	/**
	 * 关闭通道
	 */
	void close();
}