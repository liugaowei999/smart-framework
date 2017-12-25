package com.smart4j.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropsUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

	/**
	 * 加载配置文件配置项
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties LoadProps(String fileName) {
		Properties properties = null;
		InputStream inputStream = null;

		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			if (inputStream == null) {
				throw new FileNotFoundException(fileName + " file is not found!");
			}
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			// TODO: handle exception
			LOGGER.error("load properties file failure!", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					LOGGER.error("close input stream failure!", e);
				}
			}
		}

		return properties;
	}
	
	/**
	 * 获取字符型属性（默认值为空字符串）
	 * 
	 * @param props
	 * @param key
	 * @return
	 */
	public static String getString(Properties props, String key) {
		return getString(props, key, "");
	}

	/**
	 * 获取字符型属性（可指定默认值）
	 * 
	 * @param props
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Properties props, String key, String defaultValue) {
		// TODO Auto-generated method stub
		String value = defaultValue;
		if (props.containsKey(key)) {
			value = props.getProperty(key);
		}
		return value;
	}

	public static int getInt(Properties props, String key) {
		return getInt(props, key, 0);
	}

	/**
	 * 获取数值型属性（可指定默认值）
	 * 
	 * @param props
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(Properties props, String key, int defaultValue) {
		int value = defaultValue;
		if (props.containsKey(key)) {
			value = CastUtil.castInt(props.getProperty(key));
		}
		return value;
	}

	/**
	 * 获取布尔型属性（可指定默认值）
	 * 
	 * @param props
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(Properties props, String key, Boolean defaultValue) {
		boolean value = defaultValue;
		if (props.containsKey(key)) {
			value = CastUtil.castBoolean(props.getProperty(key));
		}
		return value;
	}

	/**
	 * 获取布尔型属性（默认值为false）
	 * 
	 * @param props
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Properties props, String key) {
		return getBoolean(props, key, false);
	}

}
