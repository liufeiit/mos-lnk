package me.mos.ti.srv.handler;

import me.mos.ti.packet.InIQ;
import me.mos.ti.packet.OutIQ;
import me.mos.ti.srv.Channel;
import me.mos.ti.srv.Channels;
import me.mos.ti.srv.ServerProcessor;

/**
 * IQ消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:18:45
 */
public class IQHandler extends AbstractPacketHandler<InIQ, OutIQ> {
	
	protected IQHandler(ServerProcessor processor) {
		super(processor);
	}

	@Override
	public OutIQ process(InIQ packet) throws Throwable {
		OutIQ resp = packet.toOutPacket();
		Channel channel = Channels.channel(String.valueOf(packet.getMid()));
		if (channel == null || !channel.isConnected()) {
			resp.setOnline(false);
			return resp;
		}
		resp.setOnline(true);
		return resp;
	}
}