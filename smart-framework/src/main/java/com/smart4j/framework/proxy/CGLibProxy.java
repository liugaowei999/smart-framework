package com.smart4j.framework.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * cglib 方式实现动态代理 ( 通过动态生成一个继承目标类的一个子类为代理类 来实现代理功能 )
 * CGLIB原理：动态生成一个要代理类的子类，子类重写要代理的类的所有不是final的方法。
 * -------------在子类中采用方法拦截的技术拦截所有父类方法的调用，顺势织入横切逻辑。
 * -------------它比使用java反射的JDK动态代理要快。
 * 
 * @author liugaowei
 *
 */
public class CGLibProxy implements MethodInterceptor {

	// 实现单例， 饿汉模式
	private static CGLibProxy instance = new CGLibProxy();

	public static CGLibProxy getInstance() {
		return instance;
	}

	/**
	 * 根据Class 类型，创建动态代理实例对象并返回。
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<T> clazz) {
		return (T) Enhancer.create(clazz, this);
	}

	/**
	 * 注意：CGLIB 提供的是方法级别的拦截， 参数 MethodProxy 提供方法级别的代理
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		before();
		Object result = proxy.invokeSuper(obj, args);// 调用代理类obj的父类上的方法（proxy指定了哪一个方法)
		after();
		return result;
	}

	private void before() {
		System.out.println("============= before ===============");
	}

	private void after() {
		System.out.println("============= after ===============");
	}

}
