package me.mos.ti.srv;

import me.mos.ti.etc.Profile;

/**
 * 服务器启动入口.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月4日 下午4:06:17
 */
public class ServerStarter {

	public static void main(String[] args) {
		Profile profile = Profile.newInstance();
		if (profile.isMinaSrv()) {
			me.mos.ti.srv.mina.LnkServerStarter.main(args);
			System.err.println("Mina Lnk Server Started Success!!!");
			return;
		}
		if (profile.isSockSrv()) {
			me.mos.ti.srv.sock.LnkServerStarter.main(args);
			System.err.println("Sock Lnk Server Started Success!!!");
			return;
		}
		System.err.println("Non Lnk Server to Started!!!");
	}
}