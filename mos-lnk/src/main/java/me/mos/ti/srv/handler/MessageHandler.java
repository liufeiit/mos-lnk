package me.mos.ti.srv.handler;

import me.mos.ti.message.DefaultMessageProvider;
import me.mos.ti.message.Message;
import me.mos.ti.packet.Acknowledge;
import me.mos.ti.packet.InMessage;
import me.mos.ti.packet.OutMessage;
import me.mos.ti.packet.OutPacket;
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
public class MessageHandler extends AbstractPacketHandler<InMessage> {

	public MessageHandler(ServerProcessor processor) {
		super(processor);
	}

	@Override
	public OutPacket process(Channel channel, InMessage packet) throws Throwable {
		User user = DefaultUserProvider.getInstance().query(packet.getMid());
		if (user == null) {
			// 对方不存在 丢弃消息
			return new Acknowledge().peerNoExist();
		}
		OutMessage resp = packet.toOutPacket();
		resp.setAvatar(user.getAvatar());
		resp.setNick(user.getNick());
		resp.setParty_id(user.getParty_id());
		Channel peerChannel = Channels.channel(String.valueOf(packet.getMid()));
		if (peerChannel == null || !peerChannel.isConnected()) {
			// 对方不存在离线消息保存并回执
			DefaultMessageProvider.getInstance().save(Message.newInstance(resp));
			return new Acknowledge().waitForPeerOnline();
		}
		peerChannel.write(resp);// 发送给对方
		return new Acknowledge().ok();
	}
}