package me.mos.ti.packet;

/**
 * 用户上线消息报文定义.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 上午1:11:50
 */
public class Presence extends Packet {
	
	/** 用户密码 */
	private String passwd;
	
	@Override
	public String toXML() {
		return null;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}