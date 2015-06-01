package me.mos.ti.srv;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.OutPacket;

import org.apache.commons.lang3.StringUtils;
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
public class BoundChannel implements Channel {
	
	private static final Logger log = LoggerFactory.getLogger(BoundChannel.class);
	
	private String mid;
	private Socket channel;

	public BoundChannel(Socket channel) {
		super();
		this.channel = channel;
	}

	@Override
	public String getMID() {
		return this.mid;
	}

	@Override
	public InetAddress getPeerAddress() {
		if(channel == null || !channel.isConnected()) {
			return null;
		}
		return channel.getInetAddress();
	}

	@Override
	public <I extends InPacket> I read(int timeout) {
		return null;
	}

	@Override
	public <O extends OutPacket> void write(O packet) {
	}

	@Override
	public boolean isConnected() {
		if(channel == null) {
			return false;
		}
		return channel.isConnected() && StringUtils.isNotBlank(mid);
	}

	@Override
	public void close() {
		if(channel == null) {
			return;
		}
		try {
			channel.close();
		} catch (IOException e) {
			log.error("Channel Named " + getMID() + " close Error.", e);
		}
		channel = null;
	}

	@Override
	public String toString() {
		InetAddress address = getPeerAddress();
		if(address != null) {
			return address.getHostAddress();
		}
		return super.toString();
	}
}