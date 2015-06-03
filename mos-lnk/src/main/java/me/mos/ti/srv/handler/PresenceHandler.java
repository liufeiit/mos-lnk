package me.mos.ti.srv.handler;

import me.mos.ti.packet.InPresence;
import me.mos.ti.packet.OutPresence;
import me.mos.ti.srv.ServerProcessor;

/**
 * Presence消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:22:00
 */
public class PresenceHandler extends AbstractPacketHandler<InPresence, OutPresence> {

	protected PresenceHandler(ServerProcessor processor) {
		super(processor);
	}

	@Override
	public OutPresence process(InPresence packet) throws Throwable {
		OutPresence resp = new OutPresence();
		resp.setMid(packet.getMid());
		resp.setSuccess(true);
		return resp;
	}
}