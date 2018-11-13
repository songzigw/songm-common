package cn.songm.common.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 请求消息的处理
 * @author zhangsong
 *
 */
public class JmsMQConsumer  implements MessageListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
        try {
            String ms = msg.getText();
            log.info("Receive: {}", ms);
        } catch (JMSException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
		new ClassPathXmlApplicationContext("app-common-jms-consumer.xml");
	}
}

