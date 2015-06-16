package me.mos.ti.channel;

import java.net.InetSocketAddress;

import me.mos.ti.packet.Packet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月14日 下午5:53:26
 */
public abstract class AbstractChannel<I> implements Channel<I> {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	private String channelId;

	@Override
	public String getChannelId() {
		return channelId;
	}

	@Override
	public AbstractChannel<I> setChannelId(long channelId) {
		if (channelId <= 0L) {
			return this;
		}
		this.channelId = String.valueOf(channelId);
		return this;
	}

	@Override
	public void deliver(Packet packet, boolean closeAfterDeliver) {
		deliver(packet);
		if (closeAfterDeliver) close();
	}

	@Override
	public String toString() {
		InetSocketAddress address = getPeerAddress();
		if (address != null) {
			return address.getAddress() + DOT + getChannelId();
		}
		if (getChannel() != null) {
			return getChannel().toString() + DOT + getChannelId();
		}
		return super.toString() + DOT + getChannelId();
	}
}