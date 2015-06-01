package me.mos.ti.srv;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.mos.ti.packet.InPacket;
import me.mos.ti.packet.OutPacket;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月1日 下午5:51:34
 */
public class ChannelSession implements Channel {
	
	private static final Logger log = LoggerFactory.getLogger(ChannelSession.class);
	
	private Socket channel;

	public ChannelSession(Socket channel) {
		super();
		this.channel = channel;
	}

	@Override
	public InetAddress getPeerAddress() {
		if(!channel.isConnected()) {
			return null;
		}
		return channel.getInetAddress();
	}

	@Override
	public <T extends InPacket> T read(long timeout) {
		return null;
	}

	@Override
	public <T extends OutPacket> void write(T packet) {
		
	}

	@Override
	public boolean isConnected() {
		if(channel == null) {
			return false;
		}
		return channel.isConnected();
	}

	@Override
	public void close() {
		if(channel == null) {
			return;
		}
		try {
			channel.close();
		} catch (IOException e) {
			log.error("", e);
		}
	}
}