package com.excellence.mailsender;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.excellence.mailsenderlibrary.utils.MailSender;

public class MainActivity extends AppCompatActivity
{
	private static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				sendMail();
			}
		}).start();
	}

	private void sendMail()
	{
		try
		{
			MailSender mailSender = new MailSender("xx发送邮箱xx", "**密码**");
			mailSender.setFrom("xx发送邮箱xx");
			mailSender.setTo(new String[] { "xx接收邮箱1xx", "xx接收邮箱2xx" });
			mailSender.setSubject("xx标题xx");
			mailSender.setBody("xx邮件内容xx");
			mailSender.addAttachment("xx附件路径xx", "xx附件文件名xx");
			boolean result = mailSender.send();
			Log.e(TAG, "success to send exception mail:" + String.valueOf(result));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
