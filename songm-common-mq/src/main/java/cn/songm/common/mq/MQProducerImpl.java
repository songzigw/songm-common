package cn.songm.common.mq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component("mqProducer")
public class MQProducerImpl implements MQProducer {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Override
	public void sendDataToQueue(Object object) {
		System.out.println("--" + amqpTemplate);
		try {
			amqpTemplate.convertAndSend(object);
			System.out.println("------------消息发送成功");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void main(String[] args) {
		Map<String, Object> msg = new HashMap<>();
		msg.put("serverName", "123456");
		msg.put("serverIp", "192.168.31.123");
		msg.put("sendTime", System.currentTimeMillis());
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("app-mq-producer.xml");
		context.start();
		
		MQProducer mqp = (MQProducer)context.getBean("mqProducer");
		mqp.sendDataToQueue(msg);
		
		context.stop();
		context.close();
	}
	
}
