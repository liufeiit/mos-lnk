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
	
	private static final int PEER_NOT_EXIST = 3;
	private static final int PEER_OFFLINE = 2;
	private static final int OK = 1;
	
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
		status = OK;
		return this;
	}
	
	/**
	 * 对方不在线, 已经发送离线消息
	 */
	public Acknowledge peerOffline() {
		status = PEER_OFFLINE;
		return this;
	}
	
	/**
	 * 对方不存在
	 */
	public Acknowledge peerNotExist() {
		status = PEER_NOT_EXIST;
		return this;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
}