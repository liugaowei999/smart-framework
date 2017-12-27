package com.smart4j.framework.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编码与解码操作工具类
 * 
 * @author liugaowei
 *
 */
public final class CodecUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

	/**
	 * 将URL编码
	 * 
	 * @param source
	 * @return
	 */
	public static String encodeURL(String source) {
		String target;
		try {
			target = URLEncoder.encode(source, "UTF-8");
		} catch (Exception e) {
			LOGGER.error("encode URL failure", e);
			throw new RuntimeException(e);
		}
		return target;
	}

	/**
	 * 将URL解码
	 * 
	 * @param source
	 * @return
	 */
	public static String decodeURL(String source) {
		String target;
		try {
			target = URLDecoder.decode(source, "UTF-8");
		} catch (Exception e) {
			LOGGER.error("decode URL failure", e);
			throw new RuntimeException(e);
		}
		return target;
	}
}
