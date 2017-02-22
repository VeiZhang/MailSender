package com.excellence.mailsenderlibrary.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by ZhangWei on 2016/6/1.
 */
public class UserAuthenticator extends Authenticator
{
	private String mUserName;
	private String mPassword;

	public UserAuthenticator(String username, String password)
	{
		mUserName = username;
		mPassword = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(mUserName, mPassword);
	}
}
