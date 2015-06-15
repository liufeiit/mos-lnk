package me.mos.ti.srv.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.ThreadPoolExecutor;

import me.mos.ti.etc.Profile;
import me.mos.ti.parser.JsonPacketParser;
import me.mos.ti.parser.PacketParser;
import me.mos.ti.srv.Server;
import me.mos.ti.srv.channel.ChannelActiveMonitor;
import me.mos.ti.srv.executor.LnkExecutor;
import me.mos.ti.srv.process.DefaultServerProcessor;
import me.mos.ti.srv.process.ServerProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年6月15日 下午5:18:59
 */
class LnkServer implements Server {

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

	private String charset;

	private ThreadPoolExecutor threadPoolExecutor;

	private Profile profile;

	private ServerProcessor processor;

	private PacketParser parser;

	private ServerSocketChannel server;

	private Selector selector;

	private ByteBuffer readBuffer = ByteBuffer.allocate(8192);

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
			selector = SelectorProvider.provider().openSelector();
			server = ServerSocketChannel.open();
			server.configureBlocking(false);
			ServerSocket socket = server.socket();
			socket.bind(new InetSocketAddress(port));
			socket.setReuseAddress(true);
			socket.setReceiveBufferSize(1024 * 1024);
			server.register(selector, SelectionKey.OP_ACCEPT);
			Thread masterWorker = new Thread() {
				@Override
				public void run() {
					while (true) {
						try {
							selector.select();
							Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
							while (selectedKeys.hasNext()) {
								SelectionKey key = selectedKeys.next();
								selectedKeys.remove();
								if (!key.isValid()) {
									continue;
								}
								if (key.isAcceptable()) {
									accept(key);
								} else if (key.isReadable()) {
								} else if (key.isWritable()) {
								}
							}
						} catch (Throwable t) {
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
			log.error("LnkServer[Sel] started success on port {}.", port);
		} catch (Throwable e) {
			log.error("Start LnkServer Failed.", e);
			throw new IllegalStateException(e);
		}
	}

	private void accept(SelectionKey key) throws IOException {
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
		SocketChannel socketChannel = serverSocketChannel.accept();
		Socket socket = socketChannel.socket();
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_READ);
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