package me.mos.ti.packet;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 服务器消息回执.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午4:12:35
 */
public class Acknowledge extends AbstractOutPacket {
	
	/**
	 * 消息状态
	 */
	@XStreamAlias("status")
	@XStreamAsAttribute
	private byte status;
	
	@Override
	public Type getType() {
		return Type.Acknowledge;
	}
	
	/**
	 * 消息已经发送
	 */
	public Acknowledge ok() {
		status = 1;
		return this;
	}
	
	/**
	 * 对方不在线, 已经发送离线消息
	 */
	public Acknowledge waitForPeerOnline() {
		status = 2;
		return this;
	}
	
	/**
	 * 对方不存在
	 */
	public Acknowledge peerNoExist() {
		status = 3;
		return this;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
}