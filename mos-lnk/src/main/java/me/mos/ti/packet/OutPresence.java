package me.mos.ti.packet;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 用户上线消息报文定义.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 上午1:11:50
 */
@XStreamAlias(PacketAlias.PRESENCE_NAME)
public class OutPresence extends AbstractOutPacket {
	
	@XStreamAlias("success")
	@XStreamAsAttribute
	private boolean success;

	@Override
	public Type getType() {
		return Type.Presence;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}