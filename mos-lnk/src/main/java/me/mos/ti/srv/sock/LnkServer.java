package me.mos.ti.srv.sock;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.ThreadPoolExecutor;

import me.mos.ti.channel.ChannelActiveMonitor;
import me.mos.ti.channel.Channels;
import me.mos.ti.channel.SockChannel;
import me.mos.ti.etc.Profile;
import me.mos.ti.executor.LnkExecutor;
import me.mos.ti.parser.JsonPacketParser;
import me.mos.ti.parser.PacketParser;
import me.mos.ti.srv.Server;
import me.mos.ti.srv.process.DefaultServerProcessor;
import me.mos.ti.srv.process.ServerProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月1日 下午2:16:40
 */
final class LnkServer implements Server {

	private final static Logger log = LoggerFactory.getLogger(LnkServer.class);

	private int port = DEFAULT_PORT;

	/**
	 * 输入连接指示（对连接的请求）的最大队列长度被设置为 backlog 参数。如果队列满时收到连接指示，则拒绝该连接。
	 */
	private int backlog = DEFAULT_BACKLOG;

	/**
	 * 读取超时(单位:秒)，默认30s
	 */
	private int readTimeout = DEFAULT_READ_TIMEOUT;

	private String charset = DEFAULT_CHARSET;

	/**
	 * Socket服务器
	 */
	private ServerSocket server;

	private ThreadPoolExecutor threadPoolExecutor;

	private Profile profile;
	
	private ServerProcessor processor;
	
	private PacketParser parser;

	LnkServer() {
		super();
		try {
			profile = Profile.newInstance();
			setPort(profile.getPort());
			setReadTimeout(profile.getReadTimeout());
			setCharset(profile.getCharset());
			setBacklog(profile.getBacklog());
			setProcessor(new DefaultServerProcessor());
			setParser(new JsonPacketParser());
			log.error("Config LnkServer Success.");
		} catch (Exception e) {
			log.error("Create Server Profile from XML Error.", e);
		}
	}

	@Override
	public void start() {
		try {
			log.error("LnkServer starting on port {}", port);
			threadPoolExecutor = new LnkExecutor(profile);
			server = new ServerSocket(port, backlog);
			server.setReuseAddress(true);
			Thread masterWorker = new Thread() {
				@Override
				public void run() {
					while (true) {
						try {
							Socket socket = server.accept();
							socket.setSoTimeout(readTimeout * 1000); // 毫秒
							socket.setKeepAlive(true);
							SockChannel channel = Channels.newChannel(socket, Charset.forName(charset));
							threadPoolExecutor.execute(new ServerHandler(channel, processor, parser));
							log.error(channel + " Connection to LnkServer.");
						} catch (Throwable t) {
							if (server.isClosed()) {
								log.error("LnkServer is closed, port={}", port);
								break;
							}
							log.error("Process Channel Error.", t);
							if (t instanceof InterruptedException) {
								throw new IllegalStateException(t);
							}
						}
					}
				}
			};
			masterWorker.setDaemon(true);
			masterWorker.start();
			threadPoolExecutor.execute(new ChannelActiveMonitor());
			log.error("LnkServer[Sock] started success on port {}.", port);
		} catch (Exception e) {
			log.error("Start LnkServer Failed.", e);
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void stop() {
		log.info("Stoping LnkServer, port={}", port);
		if (threadPoolExecutor != null) {
			try {
				threadPoolExecutor.shutdownNow();
			} catch (Exception e) {
			}
			threadPoolExecutor = null;
		}
		if (server != null) {
			try {
				server.close();
			} catch (Exception e) {
			}
			server = null;
		}
		log.info("Stoped LnkServer, port={}", port);
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}
	
	public void setProcessor(ServerProcessor processor) {
		this.processor = processor;
	}
	
	public void setParser(PacketParser parser) {
		this.parser = parser;
	}
}