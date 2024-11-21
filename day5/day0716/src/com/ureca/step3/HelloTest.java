package com.ureca.step3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloTest {

	public static void main(String[] args) {
		//applicationContext.xml 문서 읽기
		ApplicationContext ctx = new ClassPathXmlApplicationContext("com/ureca/step3/applicationContext.xml");
		
		//XML문서에 정의된 Bean 가져오기 : ctx.getBean("xml에 정의된 id명")
//		MessageBean msg = ctx.getBean("msgBean"); 자 = 부 관계임 : 에러!! => 자식 캐스팅 필요
		
//		MessageBean msg = (MessageBean) ctx.getBean("msgBean");
//		MessageBean msg = ctx.getBean("msgBean", MessageBean.class); //타입 정의하면 자식 캐스팅도 필요 없음
		
		MessageBean msg = ctx.getBean(MessageBean.class); //클래스가 여러개가 아니라면 아이디 명시하지 않아도 됨. 클래스로 매핑
		msg.sayHello("Rain");
	}
}
