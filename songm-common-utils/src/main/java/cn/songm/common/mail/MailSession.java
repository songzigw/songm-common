package cn.songm.common.mail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.songm.common.utils.ConfigUtils;

public class MailSession {

	private static final String PROTOCOL = "mail.transport.protocol";
	private static final String HOST = "mail.smtp.host";
	private static final String PORT = "mail.smtp.port";
	private static final String FROM = "mail.smtp.from";
	private static final String ACCOUNT = "mail.smtp.account";
	private static final String PASSWORD = "mail.smtp.password";
	private static final String VALIDATE = "mail.smtp.auth";
	private static final String DEBUG = "mail.debug";

	private Properties config;
	private Properties props;
	private Session session;
	
	public MailSession(String configName) throws IOException {
		config = ConfigUtils.loadProps(configName);
		props = new Properties();
		props.put(PROTOCOL, config.getProperty(PASSWORD));
		props.put(HOST, config.getProperty(HOST));
		props.put(PORT, config.getProperty(PORT));
		props.put(VALIDATE, config.getProperty(VALIDATE));
		props.put(DEBUG, config.getProperty(DEBUG));
		session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(
						config.getProperty(ACCOUNT),
						config.getProperty(PASSWORD));
			}
		});
	}
	
	private Message _createMessage() throws AddressException, MessagingException {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(config.getProperty(FROM)));
		return message;
	}
	
	public MailMessage createMessage() throws AddressException, MessagingException {
		return new MailMessage(_createMessage());
	}
}
