package cn.songm.common.mail;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class MailMessage {

	private Message message;
	private Multipart multipart;

	public MailMessage(Message message) {
		this.message = message;
		multipart = new MimeMultipart();
	}
	
	/**
	 * 设置收件地址
	 * 
	 * @param to
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void setTo(String to) throws AddressException, MessagingException {
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
	}

	/**
	 * 设置收件地址
	 * 
	 * @param tos
	 * @throws MessagingException
	 */
	public void setTo(List<String> tos) throws MessagingException {
		Address[] to = new Address[tos.size()];
		for (int i = 0; i < tos.size(); i++) {
			to[i] = new InternetAddress(tos.get(i));
		}
		message.setRecipients(RecipientType.TO, to);
	}

	/**
	 * 设置抄送地址
	 * 
	 * @param tos
	 * @throws MessagingException
	 */
	public void setCc(List<String> ccs) throws MessagingException {
		Address[] cc = new Address[ccs.size()];
		for (int i = 0; i < ccs.size(); i++) {
			cc[i] = new InternetAddress(ccs.get(i));
		}
		message.setRecipients(RecipientType.CC, cc);
	}

	/**
	 * 设置邮件标题
	 * 
	 * @param subject
	 * @throws MessagingException
	 */
	public void setSubject(String subject) throws MessagingException {
		message.setSubject(subject);
	}

	/**
	 * 发送邮件
	 * 
	 * @throws MessagingException
	 */
	public void sendMail() throws MessagingException {
		// 设置邮件内容
		message.setContent(multipart);
		// 发送邮件
		Transport.send(message);
	}

	/**
	 * 在邮件内容中增加文本
	 * 
	 * @param content
	 * @throws MessagingException
	 */
	public void addContent(String content) throws MessagingException {
		BodyPart bodyPart = new MimeBodyPart();
		bodyPart.setText(content);
		multipart.addBodyPart(bodyPart);
	}

	public void addAttach(File attach, String header) throws MessagingException {
		BodyPart bodyPart = new MimeBodyPart();
		DataSource dataSource = new FileDataSource(attach);
		bodyPart.setDataHandler(new DataHandler(dataSource));
		bodyPart.setFileName(attach.getName());
		if (header != null) {
			bodyPart.setHeader("Content-ID", "<" + header + ">");
		}
		multipart.addBodyPart(bodyPart);
	}

	public void addAttach(File attach) throws MessagingException {
		addAttach(attach, null);
	}

	public void addHtml(String html) throws MessagingException {
		BodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent(html, "text/html;charset=utf8");
		multipart.addBodyPart(bodyPart);
	}

	public void addImage(File image) throws MessagingException {
		String header = UUID.randomUUID().toString();
		String img = "<img src=\"cid:" + header + "\">";
		addHtml(img);
		addAttach(image, header);
	}
}
