package me.mos.ti.packet;

/**
 * 通讯消息报文基类.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 上午12:52:46
 */
public abstract class Packet {

	/** 发起报文的用户的唯一ID */
	private long mid;

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}
	
	/**
	 * 将消息格式化为可发送的XML格式
	 */
	public abstract String toXML();

	/**
	 * 将消息格式化为可发送的XML格式
	 */
	@Override
	public String toString() {
		return toXML();
	}
}