package com.vino.crawler4social.crawler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.vino.javamail.JavaMailUtils;
import com.vino.javamail.MailException;

public class MessageNotice {
	private JavaMailUtils mailUtils;
	private String host;
	private String username;
	private String password;
	private String fromMail;
	private String toMail;

	public MessageNotice() {

	}

	public void prepareMail() {
		Properties prop = new Properties();

		try (InputStream in = MessageNotice.class
				.getResourceAsStream("/mail.properties")) {
			prop.load(in);
			host = prop.getProperty("host");
			password = prop.getProperty("password");
			username = prop.getProperty("username");
			fromMail = username;
			toMail = prop.getProperty("toMail");
			mailUtils = new JavaMailUtils();
			mailUtils.setHost(host);
			mailUtils.setAccount(username, password);
			
		} catch (IOException e) {
			System.out.println("º”‘ÿmail.properties≥ˆ¥Ì");
			e.printStackTrace();
		}

	}

	public void send(String subject, String content) {
		prepareMail();
		try {
			mailUtils.send(fromMail, toMail, subject, content);
		} catch (MailException e) {
			System.out.println("∑¢ÀÕ ß∞‹");
			e.printStackTrace();
		}
	}

}
