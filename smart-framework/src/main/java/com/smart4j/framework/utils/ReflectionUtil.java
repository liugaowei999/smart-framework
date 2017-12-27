package com.smart4j.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类
 * 
 * 对Java反射API进行二次封装， 对外提供更好用的工具方法。
 * 包括：调用默认构造函数实例化对象
 * 		 执行指定对象的指定方法
 * 		 修改指定对象的属性值
 * 
 * @author liugaowei
 *
 */
public final class ReflectionUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

	/**
	 * 根据Class类对象， 使用无参数的默认构造函数进行实例化，返回Object实例化对象
	 */
	public static Object getInstance(Class<?> cls) {
		Object instance;
		try {
			instance = cls.newInstance();
		} catch (Exception e) {
			LOGGER.error("new instance failure", e);
			throw new RuntimeException(e);
		}
		return instance;
	}

	/**
	 * 通过反射调用指定对象的指定方法
	 */
	public static Object invokeMethod(Object obj, Method method, Object... args) {
		Object result;

		try {
			// 设置private方法的可见性，否则无法调用
			method.setAccessible(true);

			result = method.invoke(obj, args);
		} catch (Exception e) {
			LOGGER.error("method invoke failure", e);
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * 通过反射修改指定对象的属性值
	 */
	public static void setField(Object obj, Field field, Object value) {
		try {
			// 设置属性的读写权限，防止private私有属性写操作失败。
			field.setAccessible(true);

			field.set(obj, value);
		} catch (Exception e) {
			LOGGER.error("set field failure", e);
			throw new RuntimeException(e);
		}
	}
}
