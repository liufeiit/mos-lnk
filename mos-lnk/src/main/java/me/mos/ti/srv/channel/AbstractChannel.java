package me.mos.ti.srv.channel;

import java.net.InetSocketAddress;

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

	private String mid;
	
	@Override
	public String getMID() {
		return mid;
	}

	@Override
	public AbstractChannel<I> setMID(long mid) {
		this.mid = String.valueOf(mid);
		return this;
	}

	@Override
	public String toString() {
		InetSocketAddress address = getPeerAddress();
		if (address != null) {
			return address.getAddress() + DOT + getMID();
		}
		if (getChannel() != null) {
			return getChannel().toString() + DOT + getMID();
		}
		return super.toString() + DOT + getMID();
	}
}