package test;

import java.net.Socket;
import java.util.Arrays;
import java.util.Date;

import me.mos.ti.Server;
import me.mos.ti.packet.Acknowledge;
import me.mos.ti.packet.InIQ;
import me.mos.ti.packet.InMessage;
import me.mos.ti.packet.InPresence;
import me.mos.ti.packet.InRegister;
import me.mos.ti.packet.InSubscribe;
import me.mos.ti.packet.OutIQ;
import me.mos.ti.packet.OutMessage;
import me.mos.ti.packet.OutPresence;
import me.mos.ti.packet.OutRegister;
import me.mos.ti.packet.OutSubscribe;
import me.mos.ti.packet.SubUsr;
import me.mos.ti.srv.Channel;
import me.mos.ti.srv.Channels;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午3:17:13
 */
public class Client {

	private static final String PASSWD = "123456";

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("wjz", Server.DEFAULT_PORT);
		socket.setKeepAlive(true);
		socket.setSoTimeout(30000);
		Channel channel = Channels.newChannel(socket);
		channel.write(newInPresence());
//		channel.write(newInSubscribe());
//		channel.write(newInRegister());
//		channel.write(newInIQ());
//		channel.write(newInMessage());
		while(true) {
			String response = channel.read();
			if (StringUtils.isBlank(response)) {
				continue;
			}
			System.err.println("Response : " + response);
		}
	}
	
	public static InMessage newInMessage() {
		InMessage inMessage = new InMessage();
		inMessage.setMid(1);
		inMessage.setTid(4);
		inMessage.setBody("你好啊");
		inMessage.setGmt_created(new Date().getTime());
		return inMessage;
	}
	
	public static InPresence newInPresence() {
		InPresence inPresence = new InPresence();
		inPresence.setMid(1);
		inPresence.setPasswd(PASSWD);
		return inPresence;
	}
	
	public static InIQ newInIQ() {
		InIQ inIQ = new InIQ();
		inIQ.setMid(123);
		return inIQ;
	}
	
	public static InRegister newInRegister() {
		InRegister inRegister = new InRegister();
		inRegister.setNick("大飞哥儿1");
		inRegister.setParty_id("3");
		inRegister.setPasswd(PASSWD);
		inRegister.setAvatar("头像地址");
		inRegister.setEmail("email");
		inRegister.setPhone("固话");
		inRegister.setQq("QQ");
		inRegister.setTelephone("手机号码");
		inRegister.setWeixin("微信");
		return inRegister;
	}
	
	public static void main1(String[] args) {
		InRegister inRegister = newInRegister();
		System.out.println(inRegister);
		OutRegister outRegister = inRegister.toOutPacket();
		outRegister.setGmt_created(new Date().getTime());
		outRegister.setMid(123);
		System.out.println(outRegister);
		InIQ inIQ = newInIQ();
		System.out.println(inIQ);
		OutIQ outIQ = inIQ.toOutPacket();
		outIQ.online();
		System.out.println(outIQ);
		InPresence inPresence = newInPresence();
		System.out.println(inPresence);
		OutPresence outPresence = inPresence.toOutPacket();
		outPresence.ok();
		System.out.println(outPresence);
		InSubscribe inSubscribe = newInSubscribe();
		System.out.println(inSubscribe);
		OutSubscribe outSubscribe = inSubscribe.toOutPacket();
		SubUsr subUsr = new SubUsr();
		subUsr.setAvatar("好友头像");
		subUsr.setNick("好友昵称");
		subUsr.setParty_id("好友第三方绑定账号");
		subUsr.setSmid(456);
		SubUsr subUsr1 = new SubUsr();
		subUsr1.setAvatar("好友头像");
		subUsr1.setNick("好友昵称");
		subUsr1.setParty_id("好友第三方绑定账号");
		subUsr1.setSmid(456);
		outSubscribe.setSubUsrs(Arrays.asList(subUsr, subUsr1));
		System.out.println(outSubscribe);
		InMessage inMessage = newInMessage();
		System.out.println(inMessage);
		System.out.println(new Acknowledge(1).ok());
		OutMessage outMessage = inMessage.toOutPacket();
		outMessage.setAvatar("发送消息的人的头像");
		outMessage.setNick("发送消息的人的昵称");
		outMessage.setParty_id("发送消息的人的第三方绑定账号");
		System.out.println(outMessage);
	}
	
	public static InSubscribe newInSubscribe() {
		InSubscribe inSubscribe = new InSubscribe();
		inSubscribe.setMid(1);
		inSubscribe.setSmid(2);
		byte sub = 3;
		inSubscribe.setAct(sub);
		return inSubscribe;
	}
}