package com.smart4j.demo;

import com.smart4j.framework.proxy.CGLibProxy;

class Hello1 {

	public String sayHello() {
		String result = "Hello world!";
		System.out.println(result);
		return result;
	}

}

public class CGLibProxyTest {

	public static void main(String[] args) {
		//		CGLibProxy cgLibProxy = new CGLibProxy();
		//		Hello1 proxy = cgLibProxy.getProxy(Hello1.class);
		//		proxy.sayHello();

		Hello1 proxy1 = CGLibProxy.getInstance().getProxy(Hello1.class);
		proxy1.sayHello();

		Hello Hello1 = CGLibProxy.getInstance().getProxy(HelloImp1.class);
		Hello1.sayHello();

		Hello Hello2 = CGLibProxy.getInstance().getProxy(HelloImp2.class);
		Hello2.sayHello();
	}
}
