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
//		WriteFuture future = session.write(response);//session中必须加入这个代码，才会激活encode方法  
//		future.addListener(IoFutureListener.CLOSE);//这个的作用是发送完毕后关闭连接，加了就是短连接，不然是长连接  
//		IoFutureListener里面有个operationComplete(IoFuture future)方法，当流发送完成之后才调用这个方法
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