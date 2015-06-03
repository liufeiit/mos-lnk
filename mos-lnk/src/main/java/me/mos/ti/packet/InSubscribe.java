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
public class InSubscribe extends AbstractInPacket<OutSubscribe> {
	
	/** 发起报文的用户的唯一ID */
	@XStreamAlias("mid")
	@XStreamAsAttribute
	private long mid;

	/** 被订阅的用户的唯一ID */
	@XStreamAlias("smid")
	@XStreamAsAttribute
	private long smid;

	@Override
	public OutSubscribe toOutPacket() {
		OutSubscribe outSubscribe = new OutSubscribe();
		outSubscribe.setMid(mid);
		outSubscribe.setSmid(smid);
		return outSubscribe;
	}

	@Override
	public Type getType() {
		return Type.Subscribe;
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
}