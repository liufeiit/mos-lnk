package me.mos.ti.srv.handler;

import me.mos.ti.packet.InIQ;
import me.mos.ti.packet.OutIQ;
import me.mos.ti.packet.OutPacket;
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
public class IQHandler extends AbstractPacketHandler<InIQ> {

	public IQHandler(ServerProcessor processor) {
		super(processor);
	}

	@Override
	public OutPacket process(Channel channel, InIQ packet) throws Throwable {
		OutIQ outIQ = packet.toOutPacket();
		if (Channels.isOnline(packet.getMid())) {
			outIQ.online();
		} else {
			outIQ.offline();
			Channels.online(channel);
		}
		return outIQ;
	}
}