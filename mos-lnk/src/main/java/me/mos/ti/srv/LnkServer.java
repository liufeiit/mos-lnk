package me.mos.ti.srv;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.mos.ti.Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月1日 下午2:16:40
 */
public class LnkServer implements Server {

	private final static Logger log = LoggerFactory.getLogger(LnkServer.class);

	public final static int DEFAULT_PORT = 9099;

	private final static int DEFAULT_POOLSIZE = 50;

	private final static int DEFAULT_READ_TIMEOUT = 15;

	/**
	 * 端口号
	 */
	private int port = DEFAULT_PORT;

	/**
	 * 处理线程数
	 */
	private int poolSize = DEFAULT_POOLSIZE;

	/**
	 * 读取超时(单位:秒)，默认15s
	 */
	private int readTimeout = DEFAULT_READ_TIMEOUT;

	/**
	 * Socket服务器
	 */
	private ServerSocket server;

	/**
	 * 线程
	 */
	private ExecutorService executorService;

	public LnkServer() {
		this(DEFAULT_PORT, DEFAULT_POOLSIZE, DEFAULT_READ_TIMEOUT);
	}

	public LnkServer(int port) {
		this(port, DEFAULT_POOLSIZE, DEFAULT_READ_TIMEOUT);
	}

	public LnkServer(int port, int poolSize) {
		this(port, poolSize, DEFAULT_READ_TIMEOUT);
	}

	public LnkServer(int port, int poolSize, int readTimeout) {
		super();
		this.port = port;
		this.poolSize = poolSize;
		this.readTimeout = readTimeout;
	}

	@Override
	public void start() {
		try {
			// executorService = new ThreadPoolExecutor(poolSize, 200, 1000L,
			// TimeUnit.MINUTES);
			executorService = Executors.newFixedThreadPool(poolSize);
			server = new ServerSocket(port);
			Thread workThread = new Thread() {
				@Override
				public void run() {
					while (true) {
						try {
							// executorService.execute(new
							// Handler(server.accept(), processor));
						} catch (Throwable t) {
							if (server.isClosed()) {
								log.info("LnkServer is closed, port={}", port);
								break;
							}
							log.error("Process data error", t);
							if (t instanceof InterruptedException) {
								throw new RuntimeException(t);
							}
						}
					}
				}
			};
			workThread.setDaemon(true);
			workThread.start();
			log.info("LnkServer Started Success, port={}", port);
		} catch (Exception e) {
			log.error("Start LnkServer Failed.", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void stop() {
		log.info("Stop LnkServer, port={}", port);
		if (executorService != null) {
			try {
				executorService.shutdownNow();
			} catch (Exception e) {
			}
		}
		if (server != null) {
			try {
				server.close();
			} catch (Exception e) {
			}
		}
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
}