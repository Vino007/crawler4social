package com.vino.javamail;
import java.util.Properties;

import javax.mail.Message;

import javax.mail.Session;

import javax.mail.Transport;

import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;
/**
 * SMTP�����ʼ�
 * @author Joker
 *
 */
public class JavaMailUtils {

	String host = "";
	String user = "";
	String password = "";

	public void setHost(String host) {
		this.host = host;
	}

	public void setAccount(String user, String password) {
		this.user = user;
		this.password = password;
	}
/**
 * 
 * @param from ����������
 * @param to �ռ�������
 * @param subject ����
 * @param content ����
 * @throws MailException 
 */
	public void send(String from, String to, String subject, String content) throws MailException {
		//���÷��͵�����
		Properties props = new Properties();
		props.put("mail.smtp.host", host); // ָ��SMTP������
		props.put("mail.smtp.auth", "true"); // ָ���Ƿ���ҪSMTP��֤
		
		try {

			Session mailSession = Session.getDefaultInstance(props);//��ȡsession
			mailSession.setDebug(false);//��������ʼ�ʱ����������������
			
			Message message = new MimeMessage(mailSession);//��ȡmessage
			message.setFrom(new InternetAddress(from)); // ������
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to)); // �ռ���
			message.setSubject(subject); // �ʼ�����
			message.setText(content); // �ʼ�����		
			message.saveChanges();
			
			Transport transport = mailSession.getTransport("smtp");	//��ȡtransport�����ǽ����ʼ����ȡstore	
			transport.connect(host, user, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

		} catch (Exception e) {
			throw new MailException();
		}
	}
	

}