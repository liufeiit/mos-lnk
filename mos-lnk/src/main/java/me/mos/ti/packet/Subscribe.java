package me.mos.ti.packet;

/**
 * 好友关系订阅.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午11:56:09
 */
public class Subscribe extends Packet {

	/** 被订阅的用户的唯一ID */
	private long smid;

	@Override
	public String toXML() {
		return "<subscribe mid=\"" + getMid() + "\" smid=\"" + smid + "\"/>";
	}

	public long getSmid() {
		return smid;
	}

	public void setSmid(long smid) {
		this.smid = smid;
	}
}