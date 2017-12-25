package com.smart4j.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart4j.framework.ConfigConstant;
import com.smart4j.utils.PropsUtil;

public final class DatabaseHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

	private static final String DRIVER;
	private static final String URL;
	private static final String USERNAME;
	private static final String PASSWORD;

	static {
		Properties conf = PropsUtil.LoadProps("config.properties");
		DRIVER = conf.getProperty(ConfigConstant.JDBC_DRIVER);
		URL = conf.getProperty(ConfigConstant.JDBC_URL);
		USERNAME = conf.getProperty(ConfigConstant.JDBC_USERNAME);
		PASSWORD = conf.getProperty(ConfigConstant.JDBC_PASSWORD);

		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error("can not load jdbc driver", e);
		}
	}

	/**
	 * 获取数据库连接
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			LOGGER.error("get connection failure");
		}
		return connection;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("close connection failure");
			}
		}
	}
}
