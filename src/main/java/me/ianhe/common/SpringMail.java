package me.ianhe.common;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author iHelin
 * @create 2017-03-14 22:32
 */
public class SpringMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JavaMailSenderImpl javaMailSender;

    private String to;//对方地址
    private String subject;//主题
    private String content;//内容

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.javaMailSender = mailSender;
    }

    public boolean sendMail(String to, String subject, String content) {
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, CharEncoding.UTF_8);
            mimeMessageHelper.setFrom(javaMailSender.getUsername());
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, Boolean.TRUE);
            javaMailSender.send(mailMessage);
            return true;
        } catch (MessagingException e) {
            logger.error("发送邮件失败！", e);
            return false;
        }
    }

}
