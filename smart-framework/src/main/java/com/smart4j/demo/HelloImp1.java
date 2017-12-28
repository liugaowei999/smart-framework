package com.smart4j.demo;

public class HelloImp1 implements Hello {

	@Override
	public String sayHello() {
		String result = "HelloImp1 ----- Hello world!";
		System.out.println(result);
		return result;
	}
}
