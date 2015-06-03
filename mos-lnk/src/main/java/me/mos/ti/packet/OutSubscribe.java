package me.mos.ti.packet;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

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

	private static final int ERR = 3;

	private static final int PEER_NOT_EXIST = 2;

	private static final int OK = 1;

	/** 被订阅的用户的唯一ID */
	@XStreamAlias("smid")
	@XStreamAsAttribute
	private long smid;
	
	/** 第三方系统账号ID */
	@XStreamAlias("party-id")
	@XStreamAsAttribute
	private String party_id;

	/** 用户昵称 */
	@XStreamAlias("nick")
	private String nick;

	/** 用户头像 */
	@XStreamAlias("avatar")
	private String avatar;
	
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

	@Override
	public Type getType() {
		return Type.Subscribe;
	}

	public long getSmid() {
		return smid;
	}

	public void setSmid(long smid) {
		this.smid = smid;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getParty_id() {
		return party_id;
	}

	public void setParty_id(String party_id) {
		this.party_id = party_id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}