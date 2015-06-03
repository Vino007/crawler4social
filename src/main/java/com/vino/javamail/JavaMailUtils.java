package com.vino.javamail;
import java.util.Properties;

import javax.mail.Message;

import javax.mail.Session;

import javax.mail.Transport;

import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;
/**
 * SMTP发送邮件
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
 * @param from 发件人邮箱
 * @param to 收件人邮箱
 * @param subject 主题
 * @param content 内容
 * @throws MailException 
 */
	public void send(String from, String to, String subject, String content) throws MailException {
		//设置发送的属性
		Properties props = new Properties();
		props.put("mail.smtp.host", host); // 指定SMTP服务器
		props.put("mail.smtp.auth", "true"); // 指定是否需要SMTP验证
		
		try {

			Session mailSession = Session.getDefaultInstance(props);//获取session
			mailSession.setDebug(false);//输出发送邮件时与服务器的连接情况
			
			Message message = new MimeMessage(mailSession);//获取message
			message.setFrom(new InternetAddress(from)); // 发件人
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to)); // 收件人
			message.setSubject(subject); // 邮件主题
			message.setText(content); // 邮件内容		
			message.saveChanges();
			
			Transport transport = mailSession.getTransport("smtp");	//获取transport，若是接受邮件则获取store	
			transport.connect(host, user, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

		} catch (Exception e) {
			throw new MailException();
		}
	}
	

}