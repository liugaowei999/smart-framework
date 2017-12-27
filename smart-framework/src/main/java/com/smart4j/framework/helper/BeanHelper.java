package com.smart4j.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart4j.framework.utils.ReflectionUtil;

/**
 * 获取被smart框架管理的所有Bean类， 通过ReflectionUtil反射工具类进行实例化，
 * 将实例化后的Bean对象存储在smart框架的Bean容器中。
 * 容器：Map<Class<?>, Object>
 * 
 * Bean 实例化助手类
 * 
 * @author liugaowei
 *
 */
public final class BeanHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);

	/**
	 * 定义Bean容器。 建立Bean映射关系。（存放Bean类与Bean实例的映射关系）
	 */
	private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

	/**
	 * 静态代码块
	 * BeanHelper类加载阶段，获取所有Bean类，并进行时对象实例化
	 */
	static {
		Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
		for (Class<?> clazz : beanClassSet) {
			Object beanObject = ReflectionUtil.getInstance(clazz);
			BEAN_MAP.put(clazz, beanObject);
		}
	}

	/**
	 * 获取所有的Bean映射 --- 整个容器
	 */
	public static Map<Class<?>, Object> getBeanMap() {
		return BEAN_MAP;
	}

	/**
	 * 根据类型获取Bean实例
	 * 
	 * @param cls
	 * @return
	 */
	public static <T> T getBean(Class<T> cls) {
		if (!BEAN_MAP.containsKey(cls)) {
			LOGGER.error("cannot get bean by class:" + cls);
			throw new RuntimeException("cannot get bean by class:" + cls);
		}
		return (T) BEAN_MAP.get(cls);
	}

}
