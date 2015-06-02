package test;

import java.io.PrintWriter;
import java.net.Socket;

import me.mos.ti.Server;
import me.mos.ti.packet.InIQ;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午3:17:13
 */
public class Client {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", Server.DEFAULT_PORT);
		PrintWriter writer = new PrintWriter(socket.getOutputStream());
		writer.write(new InIQ().toString());
		writer.flush();
		System.out.println("OK");
	}
}