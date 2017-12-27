package com.smart4j.framework;

import com.smart4j.framework.helper.BeanHelper;
import com.smart4j.framework.helper.ClassHelper;
import com.smart4j.framework.helper.ControllerHelper;
import com.smart4j.framework.helper.IocHelper;
import com.smart4j.framework.utils.ClassUtil;

/**
 * 加载相应的Helper类
 * 此类的目的是集中管理 Helper类的加载过程
 * 
 * @author liugaowei
 *
 */
public final class HelperLoader {

	public static void init() {
		Class<?>[] helperClassList = { ClassHelper.class, BeanHelper.class, IocHelper.class, ControllerHelper.class };

		for (Class<?> cls : helperClassList) {
			// 加载列表中的Helper类， 并执行其静态代码块
			ClassUtil.loadClass(cls.getName(), true);
		}
	}
}
