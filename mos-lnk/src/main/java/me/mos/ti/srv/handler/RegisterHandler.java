package me.mos.ti.srv.handler;

import me.mos.ti.packet.InRegister;
import me.mos.ti.packet.OutPacket;
import me.mos.ti.packet.OutRegister;
import me.mos.ti.srv.Channel;
import me.mos.ti.srv.ServerProcessor;
import me.mos.ti.user.User;

import org.apache.commons.lang3.StringUtils;

/**
 * Register消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:22:47
 */
public class RegisterHandler extends AbstractPacketHandler<InRegister> {
	
	public RegisterHandler(ServerProcessor processor) {
		super(processor);
	}

	@Override
	public OutPacket process(Channel channel, InRegister packet) throws Throwable {
		OutRegister outRegister = packet.toOutPacket();
		User user = User.newInstance(packet);
		user.setAddress(StringUtils.EMPTY);
		user.setExtend(StringUtils.EMPTY);
		user.setIp(channel.toString());
		long mid = userProvider.save(user);
		user.online().setMid(mid);
		channel.setMID(mid);
		processor.online(channel);
		outRegister.setMid(mid);
		return outRegister;
	}
}