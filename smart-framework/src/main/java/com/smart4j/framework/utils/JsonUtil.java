package com.smart4j.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 处理JSON和POJO之间的转换， 基于jackson实现
 * @author liugaowei
 *
 */
public final class JsonUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * 将POJO 转换为 JSON
	 */
	public static <T> String toJson(T obj) {
		String json;
		try {
			json = OBJECT_MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			LOGGER.error("convert POJO to JSON failure", e);
			throw new RuntimeException(e);
		}
		return json;
	}

	/**
	 * 将 JSON 转换为 POJO
	 */
	public static <T> T toObject(String json, Class<T> cls) {
		T pojo;
		try {
			pojo = OBJECT_MAPPER.readValue(json, cls);
		} catch (Exception e) {
			LOGGER.error("convert JSON to POJO failure", e);
			throw new RuntimeException(e);
		}
		return pojo;
	}
}
