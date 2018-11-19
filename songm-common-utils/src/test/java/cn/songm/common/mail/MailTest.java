package cn.songm.common.mail;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class MailTest {
	public static void main(String[] args) {
		try {
			MailSession session = new MailSession("mail.properties");
			MailMessage message = session.createMessage();
			message.setTo("songmzi@163.com");
			message.setSubject("你好吗");
			message.addContent("我很好");
			message.sendMail();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
