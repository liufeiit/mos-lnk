package me.mos.ti.srv.handler;

import me.mos.ti.packet.InMessage;
import me.mos.ti.packet.OutMessage;
import me.mos.ti.srv.ServerProcessor;

/**
 * Message消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:20:27
 */
public class MessageHandler implements PacketHandler<InMessage, OutMessage> {
	
	private final ServerProcessor processor;

	public MessageHandler(ServerProcessor processor) {
		super();
		this.processor = processor;
	}

	@Override
	public OutMessage process(InMessage packet) throws Throwable {
		OutMessage resp = new OutMessage();
		
		return resp;
	}
}