package cn.songm.common.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RabbitMQConsumer implements MessageListener {

private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void onMessage(Message message) {
		log.info("receive: {}", message);
	}
	
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("app-common-rabbit-consumer.xml");
	}
}
