package me.mos.ti.etc;

import java.io.FileInputStream;
import java.io.IOException;

import me.mos.ti.Server;
import me.mos.ti.utils.Charsets;
import me.mos.ti.utils.StreamUtils;
import me.mos.ti.xml.XStreamParser;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午7:14:01
 */
@XStreamAlias("server")
public class Profile {

	@XStreamAlias("port")
	@XStreamAsAttribute
	private int port = Server.DEFAULT_PORT;

	@XStreamAlias("core-pool-size")
	private int corePoolSize = Server.DEFAULT_CORE_POOL_SIZE;

	/**
	 * 最大线程数目
	 */
	@XStreamAlias("maximum-pool-size")
	private int maximumPoolSize = Server.DEFAULT_MAXIMUM_POOL_SIZE;

	/**
	 * 缓冲队列大小
	 */
	@XStreamAlias("queue-size")
	private int queueSize = Server.DEFAULT_QUEUE_SIZE;
	
	/**
	 * 读取超时(单位:秒)，默认30s
	 */
	@XStreamAlias("read-timeout")
	private int readTimeout = Server.DEFAULT_READ_TIMEOUT;

	public static Profile newInstance() throws IOException {
//		return XStreamParser.toObj(Profile.class, StreamUtils.copyToString(new FileInputStream("../etc/profile.xml"), Charsets.UTF_8));
		return XStreamParser.toObj(Profile.class, StreamUtils.copyToString(new FileInputStream("etc/profile.xml"), Charsets.UTF_8));
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
}