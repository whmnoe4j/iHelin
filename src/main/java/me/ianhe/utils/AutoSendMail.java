package me.ianhe.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoSendMail extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(AutoSendMail.class);

	private String toAddress;
	private String toName;
	private String title;
	private String content;

	public AutoSendMail(String toAddress, String toName, String title, String content) {
		this.toAddress = toAddress;
		this.toName = toName;
		this.title = title;
		this.content = content;
	}

	@Override
	public void run() {
		Thread.currentThread().setName("main-send");
		logger.info("Send mail address: " + toAddress);
		logger.info("Send User: " + toName);
		Pattern pattern = Pattern.compile(
				"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(toAddress);
		if (matcher.matches()) {
			if (!MailUtil.sendMail(toAddress, toName, title, content)) {
				logger.warn(toAddress + "邮件发送失败!");
			}
		} else {
			logger.warn("邮箱地址不合法，已取消发送!");
		}
	}

}
