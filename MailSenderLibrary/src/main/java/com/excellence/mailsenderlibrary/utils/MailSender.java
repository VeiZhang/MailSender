package com.excellence.mailsenderlibrary.utils;

import android.os.Looper;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.excellence.mailsenderlibrary.bean.MailSenderInfo;

/**
 * Created by ZhangWei on 2016/6/1.
 */

/**
 * 需要权限
 * {@link android.Manifest.permission.INTERNET}
 * {@link android.Manifest.permission.READ_EXTERNAL_STORAGE}
 * {@link android.Manifest.permission.WRITE_EXTERNAL_STORAGE}
 *
 */
public class MailSender
{
	private MailSenderInfo mailSenderInfo = null;

	public MailSender()
	{
		// default receive mail
		mailSenderInfo = new MailSenderInfo();

		// default smtp server
		mailSenderInfo.setHostAddress("smtp.gmail.com");
		// default smtp port
		mailSenderInfo.setHostPort("465");
		// default socketfactory port
		mailSenderInfo.setSocketFactoryPort("465");

		// debug mode on or off - default off
		mailSenderInfo.setDebuggable(true);
		// smtp authentication - default on
		mailSenderInfo.setValidated(true);

		mailSenderInfo.setMultipart(new MimeMultipart("mixed"));

		mailSenderInfo.setSupportSSL(true);
		mailSenderInfo.setSupportTLS(false);

		// There is something wrong with MailCap, javamail can not find a
		// handler for the multipart/mixed part, so this bit needs to be added.
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mc);
	}

	public MailSender(String userAddress, String password)
	{
		this();

		String[] temp = userAddress.split("@");
		String domainName = temp[1];

		if (domainName.equals("hotmail.com") || domainName.equals("outlook.com"))
		{
			mailSenderInfo.setSupportSSL(false);
			mailSenderInfo.setSupportTLS(true);
			mailSenderInfo.setHostAddress("smtp.live.com");
			mailSenderInfo.setHostPort("587");
			mailSenderInfo.setSocketFactoryPort("587");
		}
		else if (domainName.equals("tahoo.com"))
		{
			mailSenderInfo.setSupportSSL(true);
			mailSenderInfo.setHostAddress("smtp.mail.yahoo.com");
			mailSenderInfo.setHostPort("465");
			mailSenderInfo.setSocketFactoryPort("465");
		}
		else if (domainName.equals("sina.com"))
		{
			mailSenderInfo.setValidated(true);
			mailSenderInfo.setSupportSSL(false);
			mailSenderInfo.setHostAddress("smtp.sina.com");
			mailSenderInfo.setHostPort("25");
			mailSenderInfo.setSocketFactoryPort("25");
		}
		else if (domainName.equals("gmail.com"))
		{
			// use default
		}
		else
		{
			mailSenderInfo.setSupportSSL(false);
			mailSenderInfo.setHostAddress("smtp." + domainName);
			mailSenderInfo.setHostPort("25");
			mailSenderInfo.setSocketFactoryPort("25");
		}
		mailSenderInfo.setUserName(userAddress);
		mailSenderInfo.setPassword(password);
	}

	private Properties setProperties()
	{
		Properties properties = new Properties();
		properties.put("mail.smtp.host", mailSenderInfo.getHostAddress());
		properties.put("mail.smtp.port", mailSenderInfo.getHostPort());
		properties.put("mail.debug", mailSenderInfo.isDebuggable());
		properties.put("mail.smtp.auth", String.valueOf(mailSenderInfo.isValidated()));
		properties.put("mail.smtp.starttls.enable", mailSenderInfo.isSupportTLS());
		if (mailSenderInfo.isSupportSSL())
		{
			properties.put("mail.smtp.socketFactory.port", mailSenderInfo.getSocketFactoryPort());
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
		}
		return properties;
	}

	public synchronized boolean send() throws Exception
	{
		throwIfOnMainThread();
		Properties properties = setProperties();
		UserAuthenticator userAuth = null;
		if (!mailSenderInfo.getUserName().isEmpty() && !mailSenderInfo.getPassword().isEmpty() && mailSenderInfo.getToAddress().length > 0 && !mailSenderInfo.getFromAddress().isEmpty()
				&& !mailSenderInfo.getEmailSubject().isEmpty())
		{
			if (mailSenderInfo.isValidated())
				userAuth = new UserAuthenticator(mailSenderInfo.getUserName(), mailSenderInfo.getPassword());

			Session session = Session.getInstance(properties, userAuth);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(mailSenderInfo.getFromAddress()));

			InternetAddress[] addressTo = new InternetAddress[mailSenderInfo.getToAddress().length];
			for (int i = 0; i < mailSenderInfo.getToAddress().length; i++)
			{
				addressTo[i] = new InternetAddress(mailSenderInfo.getToAddress()[i]);
			}
			msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);

			// set title
			msg.setSubject(mailSenderInfo.getEmailSubject());
			msg.setSentDate(new Date());

			// set message body
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(mailSenderInfo.getEmailBody());
			mailSenderInfo.getMultipart().addBodyPart(messageBodyPart);

			// put parts in message
			msg.setContent(mailSenderInfo.getMultipart());

			// send email
			Transport.send(msg);
			return true;
		}
		else
			return false;
	}

	private void throwIfOnMainThread()
	{
		if (Looper.getMainLooper() == Looper.myLooper())
			throw new IllegalStateException("MailSender must be not invoked from the main thread.");
	}

	public void addAttachment(String path, String fileName) throws Exception
	{
		BodyPart messageBodyPart = new MimeBodyPart();
		File file = new File(path, fileName);
		FileDataSource source = new FileDataSource(file);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(fileName);
		mailSenderInfo.getMultipart().addBodyPart(messageBodyPart);
	}

	public void removeAttachment(String fileName) throws Exception
	{
		BodyPart messageBodyPart = new MimeBodyPart();
		FileDataSource source = new FileDataSource(fileName);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(fileName);

		mailSenderInfo.getMultipart().removeBodyPart(messageBodyPart);
	}

	public void removeAll() throws Exception
	{
		int count = mailSenderInfo.getMultipart().getCount();

		for (int loop = 0; loop < count; loop++)
			mailSenderInfo.getMultipart().removeBodyPart(0);
	}

	public MailSenderInfo getMailSenderInfo()
	{
		return mailSenderInfo;
	}

	public void setTo(String[] toArr)
	{
		mailSenderInfo.setToAddress(toArr);
	}

	public void setFrom(String fromArr)
	{
		mailSenderInfo.setFromAddress(fromArr);
	}

	public void setSubject(String subject)
	{
		mailSenderInfo.setEmailSubject(subject);
	}

	public void setBody(String body)
	{
		mailSenderInfo.setEmailBody(body);
	}
}
