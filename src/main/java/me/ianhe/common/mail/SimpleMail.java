package me.ianhe.common.mail;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author iHelin
 * @create 2017-03-14 22:09
 */
public class SimpleMail {

    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String from, String to, String subject, String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }

}
