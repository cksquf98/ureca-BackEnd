package com.ureca.step2;

public class MessageBeanVT implements MessageBean{

	@Override
	public void sayHello(String name) {
		System.out.println("신짜오 "+name);
	}
}
