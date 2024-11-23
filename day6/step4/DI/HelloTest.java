package com.ureca.step4.DI;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloTest {

	public static void main(String[] args) {
//		MessageBean msg = new MessageBeanImp();
//		msg.sayHello(); //null, null
		
		//DI를 통해 데이터 출력되게 만들기 - Setter 주입
		//applicationContext.xml 생성 후 bean에 클래스 추가
		ApplicationContext ctx = new ClassPathXmlApplicationContext("com/ureca/step4/DI/applicationContext.xml");
		MessageBean msg = ctx.getBean("msgBean", MessageBean.class);
		msg.sayHello();
		
		
		//파일 출력
		
	}
}
