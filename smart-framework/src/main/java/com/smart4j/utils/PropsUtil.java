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

}
