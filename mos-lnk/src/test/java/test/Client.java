package test;

import java.net.Socket;

import org.apache.commons.lang3.StringUtils;

import me.mos.ti.Server;
import me.mos.ti.packet.InRegister;
import me.mos.ti.srv.Channel;
import me.mos.ti.srv.Channels;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午3:17:13
 */
public class Client {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", Server.DEFAULT_PORT);
		socket.setKeepAlive(true);
		socket.setSoTimeout(30000);
		Channel channel = Channels.newChannel(socket);
		channel.write(newInRegister());
		while(true) {
			String response = channel.read();
			if (StringUtils.isBlank(response)) {
				continue;
			}
			System.err.println("Register Response : " + response);
		}
	}
	
	public static InRegister newInRegister() {
		InRegister inRegister = new InRegister();
		inRegister.setNick("大飞哥儿");
		inRegister.setParty_id("1");
		inRegister.setPasswd("liufei123");
		return inRegister;
	}
}