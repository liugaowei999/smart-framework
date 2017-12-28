package com.smart4j.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart4j.framework.beans.Param;

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
	public static Object invokeMethod(Object obj, Method method, Param param) {
		Object result;
		LOGGER.debug("methodName=" + method.getName() + ", method paramcount=" + method.getParameterCount());

		// 请求传递过来的参数值
		Map<String, Object> paramMap = param.getParamMap();
		Collection<Object> values = paramMap.values();
		Object[] requestParamValues = new Object[values.size()];
		values.toArray(requestParamValues);

		// 方法本身需要的参数定义信息
		Parameter[] parameters = method.getParameters();
		Object[] args = new Object[parameters.length];
		// 根据传递过来的参数值，构建需要传递给方法的参数数组列表，稍后作为invoke方法的参数传入
		for (int i = 0; i < parameters.length; i++) {
			args[i] = null;
			System.out.println("param name[" + i + "]=" + parameters[i].getName());
			//			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			//				// 不能使用==（==比较的是地址）
			//				// 反射获取参数，名称信息会丢失，待后续优化 --- 参数名称的获取，请参考Spring的LocalVariableTableParameterNameDiscoverer 类
			//				//if (parameters[i].getName().toLowerCase().equals(entry.getKey().toLowerCase())) {
			//				args[i] = entry.getValue();
			//				//}
			//			}

			if (i <= requestParamValues.length) {
				args[i] = requestParamValues[i];
			}
			if (args[i] == null) {
				LOGGER.error("the parameter :[" + parameters[i].getName() + "] is missed! when execute the method:["
						+ method.getName() + "]");
				throw new RuntimeException("argument mismatch!");
			}
		}

		try {
			// 设置private方法的可见性，否则无法调用
			method.setAccessible(true);
			result = method.invoke(obj, args);

			//			if (method.getParameterCount() == 0) {
			//				result = method.invoke(obj, null);
			//			} else {
			//				result = method.invoke(obj, args);
			//			}
			LOGGER.debug("result=" + result);

		} catch (Exception e) {
			LOGGER.error("method invoke failure! methodName=" + method.getName() + ", args.length=" + args.length, e);
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
