package me.mos.ti.packet;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 好友关系订阅.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午11:56:09
 */
@XStreamAlias(PacketAlias.SUBSCRIBE_NAME)
public class OutSubscribe extends AbstractOutPacket {

	private static final byte ILLEGAL_ACTION = 4;

	private static final byte ERR = 3;

	private static final byte PEER_NOT_EXIST = 2;

	private static final byte OK = 1;

	/** 订阅动作 */
	@XStreamAlias("act")
	@XStreamAsAttribute
	private byte act;
	
	@XStreamImplicit(itemFieldName = "sub-usr")
	private List<SubUsr> subUsrs;
	
	@XStreamAlias("status")
	@XStreamAsAttribute
	private byte status;
	
	public OutSubscribe ok() {
		status = OK;
		return this;
	}
	
	public OutSubscribe err() {
		status = ERR;
		return this;
	}
	
	public OutSubscribe peerNotExist() {
		status = PEER_NOT_EXIST;
		return this;
	}
	
	public OutSubscribe illegalAction() {
		status = ILLEGAL_ACTION;
		return this;
	}

	@Override
	public Type getType() {
		return Type.Subscribe;
	}

	public List<SubUsr> getSubUsrs() {
		return subUsrs;
	}

	public void setSubUsrs(List<SubUsr> subUsrs) {
		this.subUsrs = subUsrs;
	}

	public byte getAct() {
		return act;
	}

	public void setAct(byte act) {
		this.act = act;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
}