

/**
 *  This class is provided to connect and clean up mySQL database
 * 
 */

package com.tbf;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class mySQL {
	
	/**
	 * This method connects to mySQL database.
	 * The method will output a Connection type.
	 * @param query
	 * @return
	 */

	public static Connection connectToSQL() {
		String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://cse.unl.edu/kmccain?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = "kmccain";
		String password = "KdmHRxOxl1";
		// 1.Load driver

		try {
			Class.forName(DRIVER_CLASS).getDeclaredConstructor().newInstance();

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		// 2.get connection
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {

			throw new RuntimeException(e);
		}
		
		
		return conn;
	}
	
	/**
	 * This method closes the Connection,PreparedStatement, and ResultSet types.
	 * @param comn 
	 * @param ps
	 * @param rs
	 */

	public static void clean(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {

			throw new RuntimeException(e);
		}
	}

	
}
