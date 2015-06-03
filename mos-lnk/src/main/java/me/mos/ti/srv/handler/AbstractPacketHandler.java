package me.mos.ti.srv.handler;

import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.OutPacket;
import me.mos.ti.srv.ServerProcessor;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月3日 下午4:31:36
 */
public abstract class AbstractPacketHandler<I extends InPacket<? extends OutPacket>, O extends OutPacket> implements PacketHandler<I, O> {

	protected final ServerProcessor processor;

	public AbstractPacketHandler(ServerProcessor processor) {
		super();
		this.processor = processor;
	}
}