package com.smart4j.framework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.smart4j.framework.annotation.Aspect;

public final class AopHelper {

	/**
	 * 获取Aspect注解中 设置的注解类
	 * 
	 * @param aspect
	 * @return
	 * @throws Exception
	 */
	private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
		Set<Class<?>> targetClassSet = new HashSet<Class<?>>();

		Class<? extends Annotation> annotation = aspect.value();
		if (annotation != null && !annotation.equals(Aspect.class)) {
			targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
		}
		return targetClassSet;
	}
}
