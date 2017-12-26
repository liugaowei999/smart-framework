package com.smart4j.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart4j.framework.ConfigConstant;
import com.smart4j.utils.PropsUtil;

public final class DatabaseHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

	private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();
	private static final QueryRunner QUERY_RUNNER = new QueryRunner();

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
		Connection connection = CONNECTION_HOLDER.get();
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			} catch (SQLException e) {
				LOGGER.error("get connection failure");
			} finally {
				CONNECTION_HOLDER.set(connection);
			}
		}
		return connection;
	}

	/**
	 * 关闭数据库连接
	 * 
	 */
	public static void closeConnection() {
		Connection conn = CONNECTION_HOLDER.get();
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("close connection failure", e);
				throw new RuntimeException(e);
			} finally {
				CONNECTION_HOLDER.remove();
			}
		}
	}

	/**
	 * 查询实体列表（List）
	 * 
	 * @param entityClass
	 *            实体Class对象
	 * @param sql
	 *            ：要执行的sql语句
	 * @param params：条件参数
	 * @return：List<T> 实体列表
	 */
	public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
		List<T> entityList;
		try {
			Connection conn = getConnection();
			/**
			 * BeanHandler     : 返回Bean对象 
			 * BeanListHandler : 返回list对象 
			 * BeanMapHandler  : 返回Map对象
			 * ArrayHandler    : 返回Object[]对象 
			 * ArrayListHandler: 返回List对象 
			 * MapHandler      : 返回Map对象
			 * MapListHandler  : 返回List对象 
			 * ScalarHandler   : 返回某列的值
			 * ColumnListHandler: 返回某列的值列表
			 * keyedHandler     : 返回Map对象，需要指定列名
			 */
			entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
		} catch (SQLException e) {
			LOGGER.error("query entity list failure", e);
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
		return entityList;
	}

	public static <T> T queryEntiry(Class<T> entityClass, String sql, Object... params) {
		T entity;
		try {
			Connection conn = getConnection();
			entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
		} catch (SQLException e) {
			LOGGER.error("query entity failure", e);
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
		return entity;
	}

}
