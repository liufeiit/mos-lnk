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

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
}