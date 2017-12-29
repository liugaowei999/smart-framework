package com.smart4j.framework.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切面注解
 * 
 * @author liugaowei
 *
 */
@Target(ElementType.TYPE) // TYPE --> 限定该注解只能应用在类上
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
	/**
	 * 注解
	 */
	Class<? extends Annotation> value();
}
