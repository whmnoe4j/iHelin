package me.ianhe.common.mail;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author iHelin
 * @create 2017-03-14 22:32
 */
public class MimeMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JavaMailSender javaMailSender;

    private String to;
    private String from;
    private String subject;
    private String content;

    public void setMailSender(JavaMailSender mailSender) {
        this.javaMailSender = mailSender;
    }

    public void sendMail(String from, String to, String subject, String content) throws MessagingException {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, CharEncoding.UTF_8);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, Boolean.TRUE);
        javaMailSender.send(mailMessage);
    }

}
