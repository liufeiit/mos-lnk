package me.mos.ti.srv;

/**
 * Lnk服务通道处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 上午12:10:24
 */
public interface ServerProcessor {
	void process(Channel channel) throws Throwable;
}