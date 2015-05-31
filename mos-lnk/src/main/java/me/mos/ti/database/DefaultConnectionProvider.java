package me.mos.ti.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import me.mos.ti.etc.Database;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午5:04:49
 */
public class DefaultConnectionProvider implements ConnectionProvider {

	private DataSource dataSource;

	@Override
	public boolean isPooled() {
		return true;
	}

	@Override
	public Connection getConnection() throws SQLException {
		if (dataSource == null) {
			throw new SQLException("Please Start ConnectionProvider.");
		}
		return dataSource.createPool().getConnection();
	}

	@Override
	public void start() {
		Database database = null;
		try {
			database = Database.newInstance();
		} catch (IOException e) {
			throw new IllegalStateException("Create Database Instance Error.", e);
		}
		dataSource = new DataSource();
		dataSource.setDriverClassName(database.getDriverClassName());
		dataSource.setTestWhileIdle(database.isTestWhileIdle());
		dataSource.setValidationQuery(database.getValidationQuery());
		dataSource.setValidationInterval(database.getValidationInterval());
		dataSource.setMaxWait(database.getMaxWait());
		dataSource.setJdbcInterceptors(database.getJdbcInterceptors());
		dataSource.setInitialSize(database.getInitialSize());
		dataSource.setUrl(database.getUrl());
		dataSource.setUsername(database.getUsername());
		dataSource.setPassword(database.getPassword());
	}

	@Override
	public void restart() {
		destroy();
		start();
	}

	@Override
	public void destroy() {
		if (dataSource == null) {
			return;
		}
		dataSource.close();
		dataSource = null;
	}
}
