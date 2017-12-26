package com.smart4j.framework.helper;

import java.util.HashSet;
import java.util.Set;

import com.smart4j.framework.annotation.Controller;
import com.smart4j.framework.annotation.Service;
import com.smart4j.framework.utils.ClassUtil;

/**
 * 类操作助手类
 * 
 * @author Administrator
 *
 */
public final class ClassHelper {

	/**
	 * 定义类集合， 存放所加载的类
	 */
	private static final Set<Class<?>> CLASS_SET;

	static {
		String basePackage = ConfigHelper.getAppBasePackage();
		CLASS_SET = ClassUtil.getClassSet(basePackage);
	}

	/**
	 * 获取应用包名下所有的类
	 * 
	 * @return
	 */
	public static Set<Class<?>> getClassSet() {
		return CLASS_SET;
	}

	/**
	 * 获取应用包名下所有@Service的类
	 * 
	 * @return
	 */
	public static Set<Class<?>> getServiceClassSet() {
		Set<Class<?>> classSet = new HashSet<>();
		for (Class<?> cls : CLASS_SET) {
			if (cls.isAnnotationPresent(Service.class)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}

	/**
	 * 获取应用包名下所有@Controller的类
	 * 
	 * @return
	 */
	public static Set<Class<?>> getControllerClassSet() {
		Set<Class<?>> classSet = new HashSet<>();
		for (Class<?> cls : CLASS_SET) {
			if (cls.isAnnotationPresent(Controller.class)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}

	/**
	 * 获取应用包名下所有Bean类(包括@Service，@Controller )
	 * 
	 * @return
	 */
	public static Set<Class<?>> getBeanClassSet() {
		Set<Class<?>> classSet = new HashSet<>();
		classSet.addAll(getServiceClassSet());
		classSet.addAll(getControllerClassSet());
		return classSet;
	}

}
