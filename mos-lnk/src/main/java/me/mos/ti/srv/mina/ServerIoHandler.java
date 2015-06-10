package me.mos.ti.srv.mina;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lnk服务通道事件处理句柄.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年6月10日 下午4:12:34
 */
final class ServerIoHandler implements IoHandler {

	private static final Logger log = LoggerFactory.getLogger(ServerIoHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		if (log.isWarnEnabled()) {
			log.warn("EXCEPTION, please implement " + getClass().getName() + ".exceptionCaught() for proper handling:", cause);
		}
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		session.close(true);
	}
}