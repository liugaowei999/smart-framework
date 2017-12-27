package com.smart4j.framework.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流操作工具类
 * 
 * @author liugaowei
 *
 */
public final class StreamUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

	/**
	 * 从输入流中获取字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String getString(InputStream is) {
		StringBuilder sBuilder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = reader.readLine()) != null) {
				sBuilder.append(line);
			}
		} catch (Exception e) {
			LOGGER.error("get string failure", e);
			throw new RuntimeException(e);
		}
		return sBuilder.toString();
	}
}
