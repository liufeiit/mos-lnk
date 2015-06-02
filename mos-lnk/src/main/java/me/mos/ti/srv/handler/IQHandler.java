package me.mos.ti.srv.handler;

import me.mos.ti.packet.InIQ;
import me.mos.ti.packet.OutIQ;
import me.mos.ti.srv.Channel;
import me.mos.ti.srv.ChannelsMemory;
import me.mos.ti.srv.ServerProcessor;

/**
 * IQ消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:18:45
 */
public class IQHandler implements PacketHandler<InIQ, OutIQ> {
	
	private final ServerProcessor processor;

	public IQHandler(ServerProcessor processor) {
		super();
		this.processor = processor;
	}

	@Override
	public OutIQ process(InIQ packet) throws Throwable {
		OutIQ resp = new OutIQ();
		Channel channel = ChannelsMemory.channel(String.valueOf(packet.getMid()));
		if (channel == null || !channel.isConnected()) {
			resp.setOnline(false);
			return resp;
		}
		resp.setOnline(true);
		return resp;
	}
}