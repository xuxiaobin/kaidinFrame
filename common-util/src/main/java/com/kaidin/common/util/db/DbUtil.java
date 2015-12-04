package com.kaidin.common.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 如果自己使用jdbc连接数据库，可以使用的辅助类
 * @author xuxiaobin	kaidin@foxmail.com
 *
 */
public class DbUtil {
	private static String JDBC_DRIVER_CLASS_NAME;
	private static String URL;
	private static String USER_NAME;
	private static String PASSWORD;
	
	
	/**
	 * 设置数据库连接属性
	 * @param jdbcDriverClassName
	 * @param url
	 * @param userName
	 * @param password
	 */
	public static void setProperty(String jdbcDriverClassName, String url, String userName, String password) {
		JDBC_DRIVER_CLASS_NAME = jdbcDriverClassName;
		URL = url;
		USER_NAME = userName;
		PASSWORD = password;
	}
	
	/**
	 * 获取数据库连接
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection result = null;
		
		Class.forName(JDBC_DRIVER_CLASS_NAME).newInstance();
		result = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		result.setAutoCommit(false);
		
		return result;
	}
	
	/**
	 * 扫尾工作，关闭连接等
	 * @param conn
	 * @param ps
	 * @param rs
	 * @throws SQLException 
	 */
	public static void close(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
		SQLException error = null;
		
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				error = e;
			}
		}

		if (ps != null) {
			try {
				ps.close();
				ps = null;
			} catch (SQLException e) {
				error = e;
			}
		}

		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				error = e;
			}
		}
		
		if (null != error) {
			throw error;
		}
	}
}
