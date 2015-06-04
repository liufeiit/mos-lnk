package me.mos.ti.srv;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import me.mos.ti.Server;
import me.mos.ti.etc.Profile;

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

	private int port = DEFAULT_PORT;

	private int corePoolSize = DEFAULT_CORE_POOL_SIZE;

	/**
	 * 最大线程数目
	 */
	private int maximumPoolSize = DEFAULT_MAXIMUM_POOL_SIZE;

	/**
	 * 缓冲队列大小
	 */
	private int queueSize = DEFAULT_QUEUE_SIZE;
	
	/**
	 * 读取超时(单位:秒)，默认30s
	 */
	private int readTimeout = DEFAULT_READ_TIMEOUT;

	/**
	 * Socket服务器
	 */
	private ServerSocket server;

	private ThreadPoolExecutor threadPoolExecutor;
	
	private Profile profile;

	public LnkServer() {
		super();
		try {
			profile = Profile.newInstance();
			setCorePoolSize(profile.getCorePoolSize());
			setMaximumPoolSize(profile.getMaximumPoolSize());
			setPort(profile.getPort());
			setQueueSize(profile.getQueueSize());
			setReadTimeout(profile.getReadTimeout());
		} catch (IOException e) {
			log.error("Create Server Profile from XML Error.", e);
		}
	}

	@Override
	public void start(final ServerProcessor processor) {
		try {
			threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, DEFAULT_KEEPALIVETIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize), DEFAULT_THREAD_FACTORY,
					DEFAULT_REJECTED_EXECUTION_HANDLER);
			server = new ServerSocket(port);
			Thread masterWorker = new Thread() {
				@Override
				public void run() {
					while (true) {
						try {
							Socket socket = server.accept();
							socket.setSoTimeout(readTimeout * 1000); // 毫秒
							socket.setKeepAlive(true);
							Channel channel = Channels.newChannel(socket);
							threadPoolExecutor.execute(new ServerHandler(channel, processor));
							log.error(channel + " Connection to LnkServer.");
						} catch (Throwable t) {
							if (server.isClosed()) {
								log.info("LnkServer is closed, port={}", port);
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
			threadPoolExecutor.execute(new Channels());
			log.info("LnkServer Started Success on {}.", port);
			System.err.println("LnkServer Started Success on " + port + ".");
		} catch (Exception e) {
			log.error("Start LnkServer Failed.", e);
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void stop() {
		log.info("Stop LnkServer, port={}", port);
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
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}
	
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory() {
		public Thread newThread(Runnable runnable) {
			final UncaughtExceptionHandler eh = Thread.getDefaultUncaughtExceptionHandler();
			Thread t = new Thread(Thread.currentThread().getThreadGroup(), runnable);
			t.setName(LNK_SERVER_WORKER + System.currentTimeMillis());
			if (t.getPriority() != Thread.NORM_PRIORITY) {
				t.setPriority(Thread.NORM_PRIORITY);
			}
			t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				public void uncaughtException(Thread t, Throwable e) {
					log.error("LnkServer Worker UncaughtExceptionHandler " + t.getName() + " Error.", e);
					eh.uncaughtException(t, e);
				}
			});
			return t;
		}
	};

	private static final RejectedExecutionHandler DEFAULT_REJECTED_EXECUTION_HANDLER = new ThreadPoolExecutor.CallerRunsPolicy() {
		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
			super.rejectedExecution(r, e);
			log.error("Thread Resource and Cache Queue Resource work out...");
		}
	};
}