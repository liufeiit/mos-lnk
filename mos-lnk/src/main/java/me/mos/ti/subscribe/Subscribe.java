package me.mos.ti.subscribe;

import me.mos.ti.packet.OutSubscribe;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月3日 下午11:45:57
 */
public class Subscribe {

	private long id;

	private long mid;

	/** 被订阅的用户的唯一ID */
	private long smid;

	/** 第三方系统账号ID */
	private String party_id;

	/** 用户昵称 */
	private String nick;

	/** 用户头像 */
	private String avatar;

	public static Subscribe newInstance(OutSubscribe outSubscribe) {
		Subscribe subscribe = new Subscribe();
		subscribe.setAvatar(outSubscribe.getAvatar());
		subscribe.setNick(outSubscribe.getNick());
		subscribe.setParty_id(outSubscribe.getParty_id());
		subscribe.setMid(outSubscribe.getMid());
		subscribe.setSmid(outSubscribe.getSmid());
		return subscribe;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	public long getSmid() {
		return smid;
	}

	public void setSmid(long smid) {
		this.smid = smid;
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