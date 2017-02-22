package com.excellence.mailsenderlibrary.bean;

import javax.mail.Multipart;

/**
 * Created by ZhangWei on 2016/6/1.
 */
public class MailSenderInfo
{
	private String userName;
	private String password;

	private String[] toAddress;
	private String fromAddress;

	private String hostAddress;
	private String hostPort;
	private String socketFactoryPort;

	private String emailSubject;
	private String emailBody;

	private boolean isValidated;

	private boolean debuggable;

	private Multipart multipart;

	private boolean isSupportSSL;
	private boolean isSupportTLS;

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String[] getToAddress()
	{
		return toAddress;
	}

	public void setToAddress(String[] toAddress)
	{
		this.toAddress = toAddress;
	}

	public String getFromAddress()
	{
		return fromAddress;
	}

	public void setFromAddress(String fromAddress)
	{
		this.fromAddress = fromAddress;
	}

	public String getHostAddress()
	{
		return hostAddress;
	}

	public void setHostAddress(String hostAddress)
	{
		this.hostAddress = hostAddress;
	}

	public String getHostPort()
	{
		return hostPort;
	}

	public void setHostPort(String hostPort)
	{
		this.hostPort = hostPort;
	}

	public String getSocketFactoryPort()
	{
		return socketFactoryPort;
	}

	public void setSocketFactoryPort(String socketFactoryPort)
	{
		this.socketFactoryPort = socketFactoryPort;
	}

	public String getEmailSubject()
	{
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject)
	{
		this.emailSubject = emailSubject;
	}

	public String getEmailBody()
	{
		return emailBody;
	}

	public void setEmailBody(String emailBody)
	{
		this.emailBody = emailBody;
	}

	public boolean isValidated()
	{
		return isValidated;
	}

	public void setValidated(boolean validated)
	{
		isValidated = validated;
	}

	public boolean isDebuggable()
	{
		return debuggable;
	}

	public void setDebuggable(boolean debuggable)
	{
		this.debuggable = debuggable;
	}

	public Multipart getMultipart()
	{
		return multipart;
	}

	public void setMultipart(Multipart multipart)
	{
		this.multipart = multipart;
	}

	public boolean isSupportSSL()
	{
		return isSupportSSL;
	}

	public void setSupportSSL(boolean supportSSL)
	{
		isSupportSSL = supportSSL;
	}

	public boolean isSupportTLS()
	{
		return isSupportTLS;
	}

	public void setSupportTLS(boolean supportTLS)
	{
		isSupportTLS = supportTLS;
	}
}
