package com.amazing;

public class HelloWorldMain {

	public static void main(String[] args) {

		// 使用接口的实现类
		HelloWorld helloWordImpl = new HelloWorldImpl();
		String string = helloWordImpl.hi();
		System.out.println(string);

		// 使用接口匿名内部类
		HelloWorld helloWorld = new HelloWorld() {

			@Override
			public String hi() {
				return "xiete";
			}
		};
		System.out.println(helloWorld.hi());
	}
}
