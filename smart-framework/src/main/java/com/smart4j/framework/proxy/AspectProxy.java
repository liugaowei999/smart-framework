package com.smart4j.framework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 切面代理 ---- 提供模板方法的抽象类
 * 
 * @author liugaowei
 *
 */
public abstract class AspectProxy implements Proxy {

	private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;

		Class<?> cls = proxyChain.getClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();

		begin();

		try {
			if (intercept(cls, method, params)) {
				before(cls, method, params);
				result = proxyChain.doProxyChain();
				after(cls, method, params);
			} else {
				result = proxyChain.doProxyChain();
			}
		} catch (Exception e) {
			logger.error("proxy failure", e);
			error(cls, method, params, e);
			throw e;
		} finally {
			end();
		}

		return result;
	}

	public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
		return true;
	}

	public void before(Class<?> cls, Method method, Object[] params) throws Throwable {

	}

	public void after(Class<?> cls, Method method, Object[] params) throws Throwable {

	}

	public void error(Class<?> cls, Method method, Object[] params, Throwable e) {

	}

	public void begin() {

	}

	public void end() {

	}

}
