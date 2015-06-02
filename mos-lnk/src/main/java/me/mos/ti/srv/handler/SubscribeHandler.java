package me.mos.ti.srv.handler;

import me.mos.ti.packet.InSubscribe;
import me.mos.ti.packet.OutSubscribe;
import me.mos.ti.srv.ServerProcessor;

/**
 * Subscribe消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:23:45
 */
public class SubscribeHandler implements PacketHandler<InSubscribe, OutSubscribe> {
	
	private final ServerProcessor processor;

	public SubscribeHandler(ServerProcessor processor) {
		super();
		this.processor = processor;
	}

	@Override
	public OutSubscribe process(InSubscribe packet) throws Throwable {
		OutSubscribe resp = new OutSubscribe();

		return resp;
	}
}