package me.mos.ti.srv.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.mos.ti.packet.InSubscribe;
import me.mos.ti.packet.OutPacket;
import me.mos.ti.packet.OutSubscribe;
import me.mos.ti.srv.channel.Channel;
import me.mos.ti.subscribe.Subscribe;
import me.mos.ti.user.SubscribeUser;
import me.mos.ti.user.User;

import org.springframework.util.CollectionUtils;

/**
 * Subscribe消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:23:45
 */
public class SubscribeHandler extends AbstractPacketHandler<InSubscribe> {

	@Override
	public OutPacket process(Channel channel, InSubscribe packet) throws Throwable {
		OutSubscribe outSubscribe = packet.toOutPacket();
		try {
			if (packet.isSub()) {
				User user = userProvider.query(packet.getSmid());
				if (user == null) {
					return outSubscribe.peerNotExist();
				}
				SubscribeUser subUsr = new SubscribeUser();
				subUsr.setSmid(packet.getSmid());
				subUsr.setAvatar(user.getAvatar());
				subUsr.setNick(user.getNick());
				subUsr.setParty_id(user.getParty_id());
				Subscribe subscribe = Subscribe.newInstance(outSubscribe);
				subscribe.setAvatar(user.getAvatar());
				subscribe.setNick(user.getNick());
				subscribe.setParty_id(user.getParty_id());
				subscribe.setSmid(packet.getSmid());
				subscribeProvider.save(subscribe);
				outSubscribe.setSubs(Arrays.asList(subUsr));
			} else if (packet.isSubCancel()) {
				User user = userProvider.query(packet.getSmid());
				if (user == null) {
					return outSubscribe.peerNotExist();
				}
				SubscribeUser subUsr = new SubscribeUser();
				subUsr.setSmid(packet.getSmid());
				subUsr.setAvatar(user.getAvatar());
				subUsr.setNick(user.getNick());
				subUsr.setParty_id(user.getParty_id());
				Subscribe subscribe = Subscribe.newInstance(outSubscribe);
				subscribe.setAvatar(user.getAvatar());
				subscribe.setNick(user.getNick());
				subscribe.setParty_id(user.getParty_id());
				subscribe.setSmid(packet.getSmid());
				subscribeProvider.delete(packet.getMid(), packet.getSmid());
				outSubscribe.setSubs(Arrays.asList(subUsr));
			} else if (packet.isSubQuery()) {
				List<Subscribe> subs = subscribeProvider.queryMessageList(packet.getMid());
				if (CollectionUtils.isEmpty(subs)) {
					return outSubscribe;
				}
				List<SubscribeUser> subsUsr = new ArrayList<SubscribeUser>();
				for (Subscribe subscribe : subs) {
					SubscribeUser subUsr = new SubscribeUser();
					subUsr.setAvatar(subscribe.getAvatar());
					subUsr.setNick(subscribe.getNick());
					subUsr.setParty_id(subscribe.getParty_id());
					subUsr.setSmid(subscribe.getSmid());
					subsUsr.add(subUsr);
				}
				outSubscribe.setSubs(subsUsr);
			} else {
				outSubscribe.illegalAction();
			}
		} catch (Exception e) {
			outSubscribe.err();
		}
		return outSubscribe;
	}
}