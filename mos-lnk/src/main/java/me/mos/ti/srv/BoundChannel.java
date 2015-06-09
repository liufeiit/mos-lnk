package me.mos.ti.srv;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

import me.mos.ti.packet.Packet;

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

	private static final int READ_BUFFER_SIZE = 2048;

	private String mid;
	
	private final String inEncoding;

	private final String outEncoding;

	private Socket channel;

//	private BufferedReader in;

//	private PrintWriter out;
	
	private InputStream in;
	
	private OutputStream out;

	public BoundChannel(Socket channel, String inEncoding, String outEncoding) {
		super();
		this.channel = channel;
		this.inEncoding = inEncoding;
		this.outEncoding = outEncoding;
		try {
//			this.in = new BufferedReader(new InputStreamReader(channel.getInputStream(), Charsets.UTF_8));
//			this.out = new PrintWriter(channel.getOutputStream(), true);
			this.in = channel.getInputStream();
			this.out = channel.getOutputStream();
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
//			return reader.readLine();
//			byte[] buffer = new byte[10000];
//			in.read(buffer);
//			return new String(buffer, Charsets.GB2312);
			
			ByteArrayOutputStream req = new ByteArrayOutputStream();
			byte[] b = new byte[READ_BUFFER_SIZE];
			int size;
			while ((size = in.read(b)) != -1) {
				req.write(b, 0, size);
				if (size < b.length) {
					break;
				}
			}
			return new String(req.toByteArray(), inEncoding);
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
//			out.println(packet.toPacket());
			out.write(packet.toPacket().getBytes(outEncoding));
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
		if (in != null) {
			try {
				in.close();
			} catch (Throwable e) {
				log.error("Channel Reader " + getMID() + " close Error.", e);
			}
			in = null;
		}
		if (out != null) {
			try {
				out.flush();
				out.close();
			} catch (Throwable e) {
				log.error("Channel Writer " + getMID() + " close Error.", e);
			}
			out = null;
		}
	}

	@Override
	public String toString() {
		InetAddress address = getPeerAddress();
		if (address != null) {
			return address.getHostAddress();
		}
		if (channel != null) {
			return channel.toString();
		}
		return super.toString();
	}
}