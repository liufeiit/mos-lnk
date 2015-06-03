package me.mos.ti.srv.handler;

import me.mos.ti.packet.InRegister;
import me.mos.ti.packet.OutRegister;
import me.mos.ti.srv.ServerProcessor;

/**
 * Register消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:22:47
 */
public class RegisterHandler extends AbstractPacketHandler<InRegister, OutRegister> {
	
	protected RegisterHandler(ServerProcessor processor) {
		super(processor);
	}

	@Override
	public OutRegister process(InRegister packet) throws Throwable {
		OutRegister resp = new OutRegister();

		return resp;
	}
}