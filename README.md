# MailSender发送邮件

[![Download][icon_download]][download]

```
dependencies {
    compile 'com.excellence:mailsender:_latestVersion'
}
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

## 使用说明
* 允许群发邮件
* 不能在主进程中使用
* 发送邮箱、密码，接收邮箱、标题、内容均不可为空

## 测试成功
* 新浪邮箱

## 版本更新
|            版本          |                              描述                               |
|------------------------- | -------------------------------------------------------------- |
| [1.0.0][MailSenderV1.0.0] | 发送邮件  **2017-7-27** |

<!-- 网站链接 -->

[download]:https://bintray.com/veizhang/maven/mailsender/_latestVersion "Latest version"

<!-- 图片链接 -->

[icon_download]:https://api.bintray.com/packages/veizhang/maven/mailsender/images/download.svg

<!-- 版本 -->

[MailSenderV1.0.0]:https://bintray.com/veizhang/maven/mailsender/1.0.0