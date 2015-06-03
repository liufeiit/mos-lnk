package me.mos.ti.srv.handler;

import me.mos.ti.message.DefaultMessageProvider;
import me.mos.ti.packet.Acknowledge;
import me.mos.ti.packet.InMessage;
import me.mos.ti.packet.OutMessage;
import me.mos.ti.srv.Channel;
import me.mos.ti.srv.Channels;
import me.mos.ti.srv.ServerProcessor;
import me.mos.ti.user.DefaultUserProvider;
import me.mos.ti.user.User;

/**
 * Message消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:20:27
 */
public class MessageHandler extends AbstractPacketHandler<InMessage, OutMessage> {

	public MessageHandler(ServerProcessor processor) {
		super(processor);
	}

	@Override
	public OutMessage process(InMessage packet) throws Throwable {
		OutMessage resp = packet.toOutPacket();
		User user = DefaultUserProvider.getInstance().query(packet.getMid());
		if (user == null) {
			resp.acknowledge(new Acknowledge().peerNoExist());
			return resp;
		}
		resp.setAvatar(user.getAvatar());
		resp.setNick(user.getNick());
		resp.setParty_id(user.getParty_id());
		Channel channel = Channels.channel(String.valueOf(packet.getMid()));
		if (channel == null) {
			DefaultMessageProvider.getInstance().save(null);
			resp.acknowledge(new Acknowledge().waitForPeerOnline());
			return resp;
		}
		channel.write(resp);
		return resp;
	}
}