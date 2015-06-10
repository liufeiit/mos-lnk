package me.mos.ti.srv;

import me.mos.ti.srv.processor.ServerProcessor;

/**
 * Mos-Lnk服务器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午1:46:09
 */
public interface Server {

	int DEFAULT_PORT = 9099;

	int DEFAULT_CORE_POOL_SIZE = 50;

	int DEFAULT_MAXIMUM_POOL_SIZE = 1000;

	int DEFAULT_QUEUE_SIZE = 10000;

	int DEFAULT_READ_TIMEOUT = 30;
	
	int DEFAULT_BACKLOG = 5000;
	
	int DEFAULT_IDLETIME = 1800;
	
	int DEFAULT_OS_SOLINGER = -1;

	void start(ServerProcessor processor);
	
	void stop();
}