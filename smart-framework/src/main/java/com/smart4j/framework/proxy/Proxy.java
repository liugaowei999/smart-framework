package com.smart4j.framework.proxy;

public interface Proxy {
	/**
	 * 执行链式代理
	 * 多个代理通过链表连在一起， 依次执行
	 * 
	 * @param proxyChain
	 * @return
	 */
	Object doProxy(ProxyChain proxyChain) throws Throwable;
}
