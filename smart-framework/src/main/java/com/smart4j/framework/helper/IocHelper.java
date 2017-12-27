package com.smart4j.framework.helper;

import java.lang.reflect.Field;
import java.util.Map;

import com.smart4j.framework.annotation.Inject;
import com.smart4j.framework.utils.ArrayUtil;
import com.smart4j.framework.utils.CollectionUtil;
import com.smart4j.framework.utils.ReflectionUtil;

/**
 * 依赖注入 -- 助手类
 * 对通过@Inject注解的属性，通过框架自动实例化。
 * 实例化的过程不是开发者通过new来实例化对象， 而是通过框架自身来实例化，像这类实例化过程
 * 称为IoC（Inversion of Control）控制反转。也称为DI（Dependency Injection）依赖注入。
 * 
 * @author liugaowei
 *
 */
public final class IocHelper {

	static {
		// 获取Bean实例的整个容器 （获取所有的Bean类与Bean实例的映射关系）
		Map<Class<?>, Object> beanMapContainer = BeanHelper.getBeanMap();

		// 判断容器是否为空
		if (CollectionUtil.isNotEmpty(beanMapContainer)) {
			// 遍历容器 beanMapContainer
			for (Map.Entry<Class<?>, Object> beanEntry : beanMapContainer.entrySet()) {
				// Bean 类
				Class<?> beanClass = beanEntry.getKey();
				// Bean 实例
				Object beanInstance = beanEntry.getValue();

				// 获取Bean类的所有成员变量， 判断是否有@Inject注解
				Field[] beanFields = beanClass.getDeclaredFields();

				// 如果Bean类有成员变量， 则进行遍历，进行依赖注入
				if (!ArrayUtil.isNotEmpty(beanFields)) {
					// 遍历 beanFields数组
					for (Field field : beanFields) {
						// 判断当前Field是否含有@Inject注解
						if (field.isAnnotationPresent(Inject.class)) {
							// 在容器中查找Field类型的Bean对象
							Class<?> beanFieldClass = field.getType();
							Object beanFieldInstance = beanMapContainer.get(beanFieldClass);
							if (beanFieldInstance != null) {
								// 通过反射工具类，初始化BeanField的值
								ReflectionUtil.setField(beanInstance, field, beanFieldInstance);
							}
						}
					}
				}
			}
		}
	}
}
