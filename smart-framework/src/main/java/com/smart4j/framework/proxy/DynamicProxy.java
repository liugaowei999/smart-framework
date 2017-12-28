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
public class DynamicProxy implements InvocationHandler {

	private Object target;

	public DynamicProxy(Object target) {
		this.target = target;
	}

	/**
	 * 根据传入的实例对象， 创建与该对象相同类型的动态代理对象
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProxy() {
		return (T) Proxy.newProxyInstance(this.target.getClass().getClassLoader(),
				this.target.getClass().getInterfaces(), this);
	}

	/**
	 * 目前无用，暂时保留
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<T> clazz) {
		// 判断target要代理的对象是否是 clazz类型的实例对象， 如果是 则创建clazz类型的代理对象来代理target
		if (clazz.isInstance(target)) {
			return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
		}
		return null;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		before();
		Object result = method.invoke(this.target, args);
		after();
		return null;
	}

	private void before() {
		System.out.println("============= JDKbefore ===============");
	}

	private void after() {
		System.out.println("============= JDKafter ===============");
	}

}
