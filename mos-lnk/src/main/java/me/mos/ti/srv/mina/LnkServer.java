package me.mos.ti.srv.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import me.mos.ti.srv.Server;
import me.mos.ti.srv.processor.ServerProcessor;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.compression.CompressionFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年6月10日 下午4:15:22
 */
public class LnkServer implements Server {

	private static final Logger log = LoggerFactory.getLogger(LnkServer.class);

	private int port = DEFAULT_PORT;
	private int idleTime = 1800;
	private int bufferSize = 1024;

	private SocketAcceptor acceptor;

	@Override
	public void start(ServerProcessor processor) {
		acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() * 2);
		TextLineCodecFactory lineCodec = new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue());
		lineCodec.setDecoderMaxLineLength(2 * 1024 * 1024);
		lineCodec.setEncoderMaxLineLength(2 * 1024 * 1024);
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(lineCodec));
		acceptor.getFilterChain().addLast("compression", new CompressionFilter());
		acceptor.getFilterChain().addLast("exceutor", new ExecutorFilter(Executors.newCachedThreadPool()));
		LoggingFilter filter = new LoggingFilter();
		filter.setExceptionCaughtLogLevel(LogLevel.DEBUG);
		filter.setMessageReceivedLogLevel(LogLevel.DEBUG);
		filter.setMessageSentLogLevel(LogLevel.DEBUG);
		filter.setSessionClosedLogLevel(LogLevel.DEBUG);
		filter.setSessionCreatedLogLevel(LogLevel.DEBUG);
		filter.setSessionIdleLogLevel(LogLevel.DEBUG);
		filter.setSessionOpenedLogLevel(LogLevel.DEBUG);
		acceptor.getFilterChain().addLast("logger", filter);
		acceptor.setHandler(new ServerIoHandler());
		acceptor.setReuseAddress(true);
		acceptor.setBacklog(10240);
		acceptor.getSessionConfig().setReuseAddress(true);
		acceptor.getSessionConfig().setReadBufferSize(bufferSize);
		acceptor.getSessionConfig().setReceiveBufferSize(bufferSize);
		acceptor.getSessionConfig().setTcpNoDelay(true);
		acceptor.getSessionConfig().setSoLinger(-1);
		// 单位秒
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, idleTime);
		try {
			acceptor.bind(new InetSocketAddress(port));
		} catch (Throwable e) {
			log.error("LnkServer Starting Error.", e);
		}

		acceptor.setReuseAddress(true);// 设置的是主服务监听的端口可以重用

		acceptor.getSessionConfig().setReuseAddress(true);// 设置每一个非主监听连接的端口可以重用
		acceptor.getSessionConfig().setReceiveBufferSize(1024);// 设置输入缓冲区的大小
		acceptor.getSessionConfig().setSendBufferSize(10240);// 设置输出缓冲区的大小
		// 设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出
		acceptor.getSessionConfig().setTcpNoDelay(true);
		// 设置主服务监听端口的监听队列的最大值为100，如果当前已经有100个连接，再新的连接来将被服务器拒绝
		acceptor.setBacklog(100);
		acceptor.setDefaultLocalAddress(new InetSocketAddress(port));
		// 加入处理器（Handler）到Acceptor
		acceptor.setHandler(new ServerIoHandler());
//		acceptor.bind();
	}

	@Override
	public void stop() {
		acceptor.dispose();
	}
}