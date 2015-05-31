package me.mos.ti.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午5:03:34
 */
public interface ConnectionProvider {

	boolean isPooled();
	
	Connection getConnection() throws SQLException;
	
	void start();
	
	void restart();
	
	void destroy();
}