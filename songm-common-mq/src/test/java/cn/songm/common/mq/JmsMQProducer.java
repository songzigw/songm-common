package cn.songm.common.mq;

import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JmsMQProducer {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Value("${jms.queue.name}")
    private String queueName;

    private static final Logger LOG = LoggerFactory
            .getLogger(JmsMQProducer.class);

    public void sendMessage() {
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public Message createMessage(javax.jms.Session s)
                    throws JMSException {
                return s.createTextMessage("Hello world!");
            }
        });
        LOG.info("发送成功");
    }
    
    public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("app-common-jms-producer.xml");
		context.start();
		
		JmsMQProducer pro = (JmsMQProducer)context.getBean("jmsMQProducer");
		pro.sendMessage();
		
		context.stop();
		context.close();
	}
}
