# MailSender
```
邮件发送
```


使用JavaMail-Android：additional.jar、activation.jar、mail.jar


###### 示例
```
    MailSender mailSender = new MailSender("xx发送邮箱xx", "**密码**");
    mailSender.setFrom("xx发送邮箱xx");
    mailSender.setTo(new String[] { "xx接收邮箱1xx", "xx接收邮箱2xx" });
    mailSender.setSubject("xx标题xx");
    mailSender.setBody("xx邮件内容xx");
    mailSender.addAttachment("xx附件路径xx", "xx附件文件名xx");
    // 是否发送成功
    boolean result = mailSender.send();
```

使用说明
----
* 允许群发邮件
* 不能在主进程中使用
* 发送邮箱、密码，接收邮箱、标题、内容均不可为空

测试成功
-------
* 新浪邮箱