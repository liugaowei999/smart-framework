package com.smart4j.framework.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK方式实现动态代理
 * 
 * @author liugaowei
 *
 */
public class DynamicProxyByType implements InvocationHandler {
	/**
	 * 根据类型建立该类型的代理对象
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<T> clazz) {
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
	}

	@Override
	public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
		before();
		Object result = method.invoke(obj, args);
		after();
		return null;
	}

	private void before() {
		System.out.println("============= JDK before ===============");
	}

	private void after() {
		System.out.println("============= JDK after ===============");
	}

}
