package com.smart4j.framework.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 用来创建所有的代理对象
 * 
 * @author liugaowei
 *
 */
public class ProxyManager {

	@SuppressWarnings("unchecked")
	public static <T> T createProxy(final Class<T> targetClass, final List<Proxy> proxyList) {

		return (T) Enhancer.create(targetClass, new MethodInterceptor() {
			@Override
			public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams,
					MethodProxy methodProxy) throws Throwable {
				System.out.println("ProxyManager->createProxy->intercept" + targetClass.getName());
				System.out.println("ProxyManager->createProxy->intercept" + targetMethod.getName());

				return new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList)
						.doProxyChain();
			}
		});
	}
}
