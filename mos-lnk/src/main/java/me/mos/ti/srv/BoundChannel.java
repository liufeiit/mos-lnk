package me.mos.ti.srv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

import me.mos.ti.packet.Packet;
import me.mos.ti.utils.Charsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端通道.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月1日 下午5:51:34
 */
final class BoundChannel implements Channel {

	private static final Logger log = LoggerFactory.getLogger(BoundChannel.class);

	private String mid;

	private Socket channel;

	private BufferedReader reader;

	private PrintWriter writer;

	public BoundChannel(Socket channel) {
		super();
		this.channel = channel;
		try {
			this.reader = new BufferedReader(new InputStreamReader(channel.getInputStream(), Charsets.UTF_8));
			this.writer = new PrintWriter(channel.getOutputStream(), true);
		} catch (Throwable e) {
			log.error("Channel Binding Error.", e);
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String getMID() {
		return this.mid;
	}

	@Override
	public Channel setMID(long mid) {
		this.mid = String.valueOf(mid);
		return this;
	}

	@Override
	public int getPort() {
		if (!isConnect()) {
			return 0;
		}
		return channel.getPort();
	}

	@Override
	public InetAddress getPeerAddress() {
		if (!isConnect()) {
			return null;
		}
		return channel.getInetAddress();
	}

	@Override
	public SocketChannel getOriginalChannel() {
		return channel.getChannel();
	}

	@Override
	public String read() {
		try {
			return reader.readLine();
		} catch (Throwable ingore) {
			try {
				if (!isConnect()) {
					Channels.offline(this);
				}
			} catch (Throwable e) {
			}
		}
		return null;
	}

	@Override
	public void write(Packet packet) {
		try {
			writer.println(packet.toPacket());
		} catch (Throwable ex) {
			log.error("Channel Write Packet Error -> " + packet.toPacket(), ex);
			try {
				if (!isConnect()) {
					Channels.offline(this);
				}
			} catch (Throwable e) {
			}
		}
	}

	@Override
	public boolean isConnect() {
		if (channel == null) {
			return false;
		}
		try {
			channel.sendUrgentData(0xFF);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	@Override
	public void close() {
		if (channel != null) {
			try {
				channel.close();
			} catch (Throwable e) {
				log.error("Channel Named " + getMID() + " close Error.", e);
			}
			channel = null;
		}
		if (reader != null) {
			try {
				reader.close();
			} catch (Throwable e) {
				log.error("Channel Reader " + getMID() + " close Error.", e);
			}
			reader = null;
		}
		if (writer != null) {
			try {
				writer.flush();
				writer.close();
			} catch (Throwable e) {
				log.error("Channel Writer " + getMID() + " close Error.", e);
			}
			writer = null;
		}
	}

	@Override
	public String toString() {
		InetAddress address = getPeerAddress();
		if (address != null) {
			return address.getHostAddress();
		}
		return super.toString();
	}
}