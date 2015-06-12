package me.mos.ti.packet;

import me.mos.ti.serializer.SerializerAdapter;

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
@XStreamAlias(Alias.SUBSCRIBE_NAME)
public class InSubscribe extends AbstractInPacket {
	
	private static final byte SUB = 1;

	private static final byte SUB_CANCEL = 2;

	private static final byte SUB_QUERY =3;

	/** 发起报文的用户的唯一ID */
	@XStreamAlias("mid")
	@XStreamAsAttribute
	private long mid;

	/** 被订阅的用户的唯一ID */
	@XStreamAlias("smid")
	@XStreamAsAttribute
	private long smid;

	/** 订阅动作 */
	@XStreamAlias("act")
	@XStreamAsAttribute
	private byte act;

	public InSubscribe() {
		super(Type.Subscribe.type);
	}

	public static InSubscribe fromPacket(String packet) {
		return SerializerAdapter.currentSerializer().deserialize(InSubscribe.class, packet);
	}

	@Override
	public OutSubscribe toOutPacket() {
		OutSubscribe outSubscribe = new OutSubscribe();
		outSubscribe.setMid(mid);
		outSubscribe.setAct(act);
		return outSubscribe.ok();
	}
	
	public boolean isSubAction() {
		return SUB == this.act;
	}
	
	public boolean isSubCancel() {
		return SUB_CANCEL == this.act;
	}
	
	public boolean isSubQuery() {
		return SUB_QUERY == this.act;
	}

	@Override
	public Type getPacketType() {
		return Type.Subscribe;
	}

	public byte getAct() {
		return act;
	}

	public void setAct(byte act) {
		this.act = act;
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