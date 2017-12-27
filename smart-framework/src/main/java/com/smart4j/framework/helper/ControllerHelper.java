package com.smart4j.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.smart4j.framework.annotation.Action;
import com.smart4j.framework.beans.Handler;
import com.smart4j.framework.beans.Request;
import com.smart4j.framework.utils.ArrayUtil;
import com.smart4j.framework.utils.CollectionUtil;

/**
 * 控制器助手类
 * 
 * @author liugaowei
 *
 */
public final class ControllerHelper {

	/**
	 * 保存请求与处理器的映射关系
	 */
	private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

	static {
		// 从容器中获取所有的Controller 类
		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();

		if (CollectionUtil.isNotEmpty(controllerClassSet)) {
			// 遍历Crontroller类
			for (Class<?> controllerClass : controllerClassSet) {
				// 获取当前Crontroller类中定义的方法
				Method[] methods = controllerClass.getDeclaredMethods();

				if (ArrayUtil.isNotEmpty(methods)) {
					// 遍历当前Controller类中的方法
					for (Method method : methods) {
						// 判断当前method是否带有@Action注解
						if (method.isAnnotationPresent(Action.class)) {
							// 从Action注解中获取URL
							Action action = method.getAnnotation(Action.class);

							String actionURLValue = action.value();

							// 验证URL映射规则
							if (actionURLValue.matches("\\w+:/\\w*")) {
								String[] array = actionURLValue.split(":");
								if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
									// 获取请求方法
									String requestMethod = array[0];
									// 获取请求路径
									String requestPath = array[1];

									Request request = new Request(requestMethod, requestPath);
									Handler handler = new Handler(controllerClass, method);
									ACTION_MAP.put(request, handler);
								}
							}
						} // end if (method.isAnnotationPresent(Action.class))
					} // end for
				}
			} // end for
		}
	}// end static

	/**
	 * 获取Handler
	 */
	public static Handler getHandler(String requestMethod, String requestPath) {
		Request request = new Request(requestMethod, requestPath);
		return ACTION_MAP.get(request);
	}
}
