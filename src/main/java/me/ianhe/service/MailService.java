package me.ianhe.service;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author iHelin
 * @since 2017/11/14 17:10
 */
@Service
public class MailService {

    @Autowired
    private Global global;
    private Logger logger = LoggerFactory.getLogger(getClass());

    private JavaMailSenderImpl mailSender;

    @PostConstruct
    public void init() {
        mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(global.getValue("mail.smtp.host"));
        mailSender.setUsername(global.getValue("mail.user"));
        mailSender.setPassword(global.getValue("mail.password"));
        mailSender.setJavaMailProperties(global.getProperties());
        mailSender.setDefaultEncoding(CharEncoding.UTF_8);
    }

    public void sendTextMail(String toAddressStr, String subject, String content) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(mailSender.getUsername());
        smm.setTo(toAddressStr);
        smm.setSubject(subject);
        smm.setText(content);
        mailSender.send(smm);
    }

    /**
     * 发送富文本邮件
     *
     * @throws MessagingException
     */
    public void sendHTMLMail(String toAddressStr, String subject, String content) {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(msg, true);
            helper.setFrom(mailSender.getUsername());
            helper.setTo(toAddressStr);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(msg);
        } catch (MessagingException e) {
            logger.error("发送邮件失败", e);
        }
    }

}
