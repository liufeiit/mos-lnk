package me.mos.ti.channel;

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

	private IoSession session;

	private boolean closed;

	BoundIoSessionChannel(IoSession session) {
		super();
		this.session = session;
		this.closed = false;
	}

	@Override
	public void deliver(Packet packet) {
		session.write(packet);
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
	protected void _close() {
		session.close(false);
		closed = true;
	}

	@Override
	public boolean isConnect() {
		return session.isConnected() && !closed;
	}
}