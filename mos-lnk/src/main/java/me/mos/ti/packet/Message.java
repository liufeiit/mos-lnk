package me.mos.ti.packet;

/**
 * 聊天通讯消息报文定义.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午9:46:53
 */
public class Message extends Packet {
	
	/** 消息到达方的用户的唯一ID */
	private long tid;

	/** 消息内容体 */
	private String body;

	/** 消息发送时间 */
	private long gmt_created;

	@Override
	public String toXML() {
		return null;
	}

	public long getTid() {
		return tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public long getGmt_created() {
		return gmt_created;
	}

	public void setGmt_created(long gmt_created) {
		this.gmt_created = gmt_created;
	}
}