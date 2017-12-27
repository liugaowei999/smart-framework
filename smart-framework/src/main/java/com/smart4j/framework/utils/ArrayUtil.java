package com.smart4j.framework.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 * 
 * @author liugw
 *
 */
public final class ArrayUtil {

	/**
	 * 判断Object[]是否为空
	 * 
	 * @param Object[]
	 * @return
	 */
	public static boolean isEmpty(Object[] objects) {
		return ArrayUtils.isEmpty(objects);
	}

	/**
	 * 判读Object[]是否为非空
	 * 
	 * @param Object[]
	 * @return
	 */
	public static boolean isNotEmpty(Object[] objects) {
		return !isEmpty(objects);
	}
}
