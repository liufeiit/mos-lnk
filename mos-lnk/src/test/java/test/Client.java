package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import me.mos.ti.Server;
import me.mos.ti.packet.InIQ;
import me.mos.ti.packet.InPresence;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午3:17:13
 */
public class Client {

	public static void main(String[] args) throws Exception {
		long mid = 123L;
		Socket socket = new Socket("localhost", Server.DEFAULT_PORT);
		socket.setKeepAlive(true);
		socket.setSoTimeout(3000);
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter writer = new PrintWriter(socket.getOutputStream());
		while(true) {
			InPresence inPresence = new InPresence();
			inPresence.setMid(mid);
			inPresence.setPasswd("123456");
			writer.println(inPresence.toString());
			writer.flush();
			System.err.println("Resp : " + reader.readLine());
			InIQ inIQ = new InIQ();
			inIQ.setMid(mid);
			writer.println(inIQ.toString());
			writer.flush();
			System.err.println("Resp : " + reader.readLine());
		}
	}
}