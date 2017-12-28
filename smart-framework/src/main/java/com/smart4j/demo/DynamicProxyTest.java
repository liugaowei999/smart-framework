package com.smart4j.demo;

import com.smart4j.framework.proxy.DynamicProxy;

public class DynamicProxyTest {

	public static void main(String[] args) {
		DynamicProxy dynamicProxy1 = new DynamicProxy(new HelloImp1());
		Hello hello1 = dynamicProxy1.getProxy();
		//		Hello hello1 = dynamicProxy.getProxy(HelloImp1.class);
		hello1.sayHello();
		//		hello1.sayHello();
		System.out.println("\n\n");

		DynamicProxy dynamicProxy2 = new DynamicProxy(new HelloImp2());
		Hello hello2 = dynamicProxy2.getProxy();
		hello2.sayHello();
	}

}
