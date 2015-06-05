package me.mos.ti.srv.handler;

import me.mos.ti.message.DefaultMessageProvider;
import me.mos.ti.message.MessageProvider;
import me.mos.ti.packet.InPacket;
import me.mos.ti.subscribe.DefaultSubscribeProvider;
import me.mos.ti.subscribe.SubscribeProvider;
import me.mos.ti.user.DefaultUserProvider;
import me.mos.ti.user.UserProvider;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月3日 下午4:31:36
 */
public abstract class AbstractPacketHandler<I extends InPacket> implements PacketHandler<I> {

	protected MessageProvider messageProvider;
	
	protected UserProvider userProvider;
	
	protected SubscribeProvider subscribeProvider;

	public AbstractPacketHandler() {
		super();
		messageProvider = DefaultMessageProvider.getInstance();
		userProvider = DefaultUserProvider.getInstance();
		subscribeProvider = DefaultSubscribeProvider.getInstance();
	}
}