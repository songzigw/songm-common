package cn.songm.common.mq;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerMain {
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("app-mq-consumer.xml");
	}
}
