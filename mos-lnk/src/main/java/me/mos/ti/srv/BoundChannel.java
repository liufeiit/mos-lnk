package me.mos.ti.srv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import me.mos.ti.packet.OutPacket;

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
			this.reader = new BufferedReader(new InputStreamReader(channel.getInputStream()));
			this.writer = new PrintWriter(channel.getOutputStream());
		} catch (IOException e) {
			log.error("Channel Binding Error.", e);
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String getMID() {
		return this.mid;
	}

	@Override
	public BufferedReader getReader() {
		return reader;
	}

	@Override
	public InetAddress getPeerAddress() {
		if (channel == null || !channel.isConnected()) {
			return null;
		}
		return channel.getInetAddress();
	}

	@Override
	public <O extends OutPacket> void write(O packet) {
		try {
			writer.write(packet.toXML());
			writer.flush();
		} catch (Exception e) {
			log.error("Channel Write Packet Error.", e);
		}
	}

	@Override
	public boolean isConnected() {
		if (channel == null) {
			return false;
		}
		return channel.isConnected();
	}

	@Override
	public void close() {
		if (channel != null) {
			try {
				channel.close();
			} catch (IOException e) {
				log.error("Channel Named " + getMID() + " close Error.", e);
			}
			channel = null;
		}
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				log.error("Channel Reader " + getMID() + " close Error.", e);
			}
			reader = null;
		}
		if (writer != null) {
			try {
				writer.flush();
				writer.close();
			} catch (Exception e) {
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