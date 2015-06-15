package me.mos.ti.srv.channel;

import java.net.InetSocketAddress;

import me.mos.ti.packet.Packet;

import org.apache.mina.core.session.IoSession;

/**
 * 基于mina的连接通道.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月14日 下午12:39:39
 */
final class BoundIoSessionChannel extends AbstractChannel<IoSession> implements IoSessionChannel {

	private String mid;

	private IoSession session;

	private boolean closed;

	public BoundIoSessionChannel(IoSession session) {
		super();
		this.session = session;
		this.closed = false;
	}

	@Override
	public InetSocketAddress getPeerAddress() {
		return (InetSocketAddress) session.getRemoteAddress();
	}

	@Override
	public IoSession getChannel() {
		return session;
	}

	@Override
	public String getMID() {
		return mid;
	}

	@Override
	public BoundIoSessionChannel setMID(long mid) {
		this.mid = String.valueOf(mid);
		return this;
	}

	@Override
	public void deliver(Packet packet) {
		session.write(packet);
	}

	@Override
	public void close() {
		synchronized (this) {
			if (isConnect()) {
				session.close(false);
				closed = true;
			}
		}
	}

	@Override
	public boolean isConnect() {
		return session.isConnected() && !closed;
	}
}