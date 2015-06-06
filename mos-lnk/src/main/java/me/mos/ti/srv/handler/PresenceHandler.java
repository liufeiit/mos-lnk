package me.mos.ti.srv.handler;

import java.util.List;

import me.mos.ti.message.Message;
import me.mos.ti.packet.InPresence;
import me.mos.ti.packet.OutMessage;
import me.mos.ti.packet.OutPacket;
import me.mos.ti.packet.OutPresence;
import me.mos.ti.srv.Channel;
import me.mos.ti.srv.Channels;

import org.springframework.util.CollectionUtils;

/**
 * Presence出席消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:22:00
 */
public class PresenceHandler extends AbstractPacketHandler<InPresence> {

	@Override
	public OutPacket process(Channel channel, InPresence packet) throws Throwable {
		OutPresence outPresence = packet.toOutPacket();
		try {
			Channels.online(channel);
			// 我出席了之后 拉取我的离线消息
			List<Message> offlineMessageList = messageProvider.queryMessageList(packet.getMid());
			if (CollectionUtils.isEmpty(offlineMessageList)) {
				return outPresence;
			}
			for (Message message : offlineMessageList) {
				if (message == null) {
					continue;
				}
				OutMessage outMessage = message.toOutMessage();
				channel.write(outMessage);
				messageProvider.delete(message.getId());
			}
		} catch (Exception e) {
			log.error("Presence Processing Error.", e);
			return outPresence.err();
		}
		return outPresence;
	}
}