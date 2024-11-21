package com.ureca.step2;

public class MessageBeanEN implements MessageBean{

	@Override
	public void sayHello(String name) {
		System.out.println("Hi "+name);
	}
}
