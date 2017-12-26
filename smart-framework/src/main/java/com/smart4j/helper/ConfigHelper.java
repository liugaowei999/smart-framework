package com.smart4j.helper;

import java.util.Properties;

import com.smart4j.framework.ConfigConstant;
import com.smart4j.utils.PropsUtil;

/**
 * 读取配置文件助手类
 * 框架配置文件名称固定为： ConfigConstant.CONFIG_FILE
 * 
 * @author liugaowei
 *
 */
public final class ConfigHelper {

	private static final Properties CONFIG_PROPS = PropsUtil.LoadProps(ConfigConstant.CONFIG_FILE);

	/**
	 * 获取JDBC驱动
	 */
	public static String getJdbcDriver() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
	}

	/**
	 * 获取JDBC 地址
	 */
	public static String getJdbcUrl() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
	}

	/**
	 * 获取JDBC用户名
	 */
	public static String getJdbcUserName() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
	}

	/**
	 * 获取JDBC密码
	 */
	public static String getJdbcPassword() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
	}

	/**
	 * 获取应用基础包名
	 */
	public static String getAppBasePackage() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
	}

	/**
	 * 获取应用 JSP 路径
	 */
	public static String getAppJspPath() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/WEB_INF/view/");
	}

	/**
	 * 获取应用静态资源路径
	 */
	public static String getAppAssertPath() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSERT_PATH, "/asset/");
	}
}
