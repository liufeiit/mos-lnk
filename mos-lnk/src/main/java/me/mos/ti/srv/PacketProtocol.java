package me.mos.ti.srv;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月13日 下午11:28:20
 */
public interface PacketProtocol {
	
	String DOT = "/";

	/**
	 * 定义头信息的字节数
	 */
	int HEAD_BYTE_LENGTH = 20;

	/**
	 * 在头信息中, 前4位表示该报文的长度, 剩余的16位头信息备用
	 */
	int PACKET_BYTE_LENGTH = 4;

	/**
	 * 版本号所在的头位置
	 */
	int VERSION_POSITION = 5;
}