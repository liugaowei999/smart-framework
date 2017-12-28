package com.smart4j.demo;

public class HelloImp2 implements Hello {

	@Override
	public String sayHello() {
		String result = "HelloImp2 --- Hello world!";
		System.out.println(result);
		return result;
	}
}
