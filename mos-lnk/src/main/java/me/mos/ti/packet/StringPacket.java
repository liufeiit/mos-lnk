package me.mos.ti.packet;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月6日 下午8:59:55
 */
public class StringPacket implements Packet {

	private final String packet;
	
	public StringPacket(String packet) {
		super();
		this.packet = packet;
	}

	@Override
	public String toPacket() {
		return packet;
	}

	@Override
	public Type getType() {
		return Type.String;
	}
}