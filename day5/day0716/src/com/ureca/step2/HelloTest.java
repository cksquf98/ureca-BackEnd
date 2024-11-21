package com.ureca.step2;

public class HelloTest {

	public static void main(String[] args) {
		MessageBean msg = new MessageBeanKO();
		msg.sayHello("김찬별");
		
		msg = new MessageBeanEN();
		msg.sayHello("Spring");
		
		msg = new MessageBeanVT();
		msg.sayHello("베트남");
	}
}