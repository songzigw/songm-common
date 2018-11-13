package cn.songm.common.mq;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RabbitMQProducer {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AmqpTemplate amqpTemplate;

	public void sendDataToQueue(Object object) {
		try {
			amqpTemplate.convertAndSend(object);
			log.info("消息发送成功");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void main(String[] args) {
		Map<String, Object> msg = new HashMap<>();
		msg.put("serverName", "123456");
		msg.put("serverIp", "192.168.31.123");
		msg.put("sendTime", System.currentTimeMillis());
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("app-common-rabbit-producer.xml");
		context.start();
		
		RabbitMQProducer pro = (RabbitMQProducer)context.getBean("rabbitMQProducer");
		pro.sendDataToQueue(msg);
		
		context.stop();
		context.close();
	}
}
