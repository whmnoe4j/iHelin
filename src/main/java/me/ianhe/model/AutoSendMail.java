package me.ianhe.model;

import me.ianhe.utils.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoSendMail implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String MAIL_REG = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}" +
            "\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

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
        Thread.currentThread().setName("mail-send-thread");
        logger.info("Send mail address {},User is {}: ", toAddress, toName);
        Pattern pattern = Pattern.compile(MAIL_REG);
        Matcher matcher = pattern.matcher(toAddress);
        if (matcher.matches()) {
            if (!MailUtil.sendMail(toAddress, toName, title, content)) {
                logger.error("邮件发送失败！目标地址为{}", toAddress);
            }
        } else {
            logger.warn("邮箱地址不合法，已取消发送!");
        }
    }

}
