package com.ureca.step4.DI;

public class MessageBeanImp implements MessageBean {

	// private 필드 : 자식도 접근 못함 => getter, setter를 통해 접근
	private String greeting;
	private String name;
	private FileOutput fo;

	// 기본생성자
	public MessageBeanImp() { }

	// 오버로딩 생성자
	public MessageBeanImp(String greeting) {
		this.greeting = greeting;
	}

	// 오버로딩 생성자
	public MessageBeanImp(String greeting, String name) {
		this.greeting = greeting;
		this.name = name;
	}

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 인터페이스에 선언된 메서드 오버라이딩
	@Override
	public void sayHello() {
		String str = greeting + ", " + name;
		System.out.println(str);
		fo.output(str); //파일 출력
	}
	
	//FileOutput 생성자 추가
	public void setFo(FileOutput fo) {
		this.fo = fo;
	}
	
}
