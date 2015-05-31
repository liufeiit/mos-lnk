package me.mos.ti.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午7:38:53
 */
public class SQLExecutor {

	private static final Logger log = LoggerFactory.getLogger(SQLExecutor.class);
	
	public static void executeSQLScript(Connection con, InputStream resource) throws IOException, SQLException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(resource));
			boolean done = false;
			while (!done) {
				StringBuilder command = new StringBuilder();
				while (true) {
					String line = in.readLine();
					if (line == null) {
						done = true;
						break;
					}
					// Ignore comments and blank lines.
					if (isSQLCommandPart(line)) {
						command.append(" ").append(line);
					}
					if (line.trim().endsWith(";")) {
						break;
					}
				}
				// Send command to database.
				if (!done && !command.toString().equals("")) {
					// Remove last semicolon when using Oracle or DB2 to prevent
					// "invalid character error"
					if (DbConnectionManager.getDatabaseType() == DbConnectionManager.DatabaseType.oracle || DbConnectionManager.getDatabaseType() == DbConnectionManager.DatabaseType.db2) {
						command.deleteCharAt(command.length() - 1);
					}
					PreparedStatement pstmt = null;
					try {
						String cmdString = command.toString();
						pstmt = con.prepareStatement(cmdString);
						pstmt.execute();
					} catch (SQLException e) {
						// Lets show what failed
						log.error("SchemaManager: Failed to execute SQL:\n" + command.toString());
						throw e;
					} finally {
						DbConnectionManager.closeStatement(pstmt);
					}
				}
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * Returns true if a line from a SQL schema is a valid command part.
	 * 
	 * @param line
	 *            the line of the schema.
	 * @return true if a valid command part.
	 */
	private static boolean isSQLCommandPart(String line) {
		line = line.trim();
		if (line.equals("")) {
			return false;
		}
		// Check to see if the line is a comment. Valid comment types:
		// "//" is HSQLDB
		// "--" is DB2 and Postgres
		// "#" is MySQL
		// "REM" is Oracle
		// "/*" is SQLServer
		return !(line.startsWith("//") || line.startsWith("--") || line.startsWith("#") || line.startsWith("REM") || line.startsWith("/*") || line.startsWith("*"));
	}
}