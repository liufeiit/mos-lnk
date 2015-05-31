package me.mos.ti.packet;

import me.mos.ti.xml.XStreamParser;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 通讯消息报文基类.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 上午12:52:46
 */
public abstract class AbstractPacket implements Packet {

	/** 发起报文的用户的唯一ID */
	@XStreamAlias("mid")
	@XStreamAsAttribute
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
	@Override
	public String toXML() {
		return XStreamParser.toXML(this);
	}

	/**
	 * 将消息格式化为可发送的XML格式
	 */
	@Override
	public String toString() {
		return toXML();
	}
}