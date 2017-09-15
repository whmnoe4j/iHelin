package me.ianhe.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class MailUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailUtil.class);

    // 发送邮件的服务器的IP和端口
    private static final String MAIL_SERVER_HOST = Global.get("mail.server");
    private static final String MAIL_SERVER_PORT = Global.get("mail.port");
    private static final String MAIL_USERNAME = Global.get("mail.user");
    private static final String MAIL_PASSWORD = Global.get("mail.password");
    private static final String MAIL_FROM_ADDRESS = Global.get("mail.fromAddress");
    private static final String MAIL_FROM_PERSONAL_NAME = Global.get("mail.fromName");

    static class MyAuthenticator extends Authenticator {
        String userName = null;
        String password = null;

        MyAuthenticator(String username, String password) {
            this.userName = username;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }
    }

    /**
     * 获得邮件会话属性
     *
     * @author iHelin
     * @since 2017/9/15 14:18
     */
    private static Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", MAIL_SERVER_HOST);
        p.put("mail.smtp.port", MAIL_SERVER_PORT);
        p.put("mail.smtp.auth", "true");
        return p;
    }

    /**
     * 发送html邮件
     *
     * @author iHelin
     * @since 2017/9/3 11:44
     */
    public static boolean sendHTMLMail(String toAddress, String toPersonalName, String subject, String content) {
        return sendHTMLMail(toAddress, toPersonalName, subject, content, null, null, null);
    }

    /**
     * 发送html邮件
     *
     * @author iHelin
     * @since 2017/9/3 11:44
     */
    public static boolean sendHTMLMail(String toAddressStr, String toPersonalName, String subject, String content, String fileName,
                                       InputStream attachStream, String attachType) {
        Properties pro = getProperties();
        // 如果需要身份认证，则创建一个密码验证器
        MyAuthenticator authenticator = new MyAuthenticator(MAIL_USERNAME, MAIL_PASSWORD);
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address fromAddress = new InternetAddress(MAIL_FROM_ADDRESS, MAIL_FROM_PERSONAL_NAME);
            // 设置邮件消息的发送者
            mailMessage.setFrom(fromAddress);

            // 创建邮件的接收者地址，并设置到邮件消息中
            // Message.RecipientType.TO属性表示接收者的类型为TO

            String[] addresses = toAddressStr.split(";");
            int length = addresses.length;
            if (length < 1) {
                LOGGER.warn("没有收件邮箱地址。");
                return false;
            } else {
                if (length == 1) {
                    String addressStr = addresses[0].trim();
                    Address toAddress = new InternetAddress(addressStr, toPersonalName);
                    mailMessage.setRecipient(Message.RecipientType.TO, toAddress);
                } else {
                    List<Address> addressList = new ArrayList<>(length);
                    for (String addressStr : addresses) {
                        addressStr = addressStr.trim();
                        addressList.add(new InternetAddress(addressStr, addressStr));
                    }
                    Address[] addressArr = new Address[length];
                    mailMessage.setRecipients(Message.RecipientType.TO, addressList.toArray(addressArr));
                }
            }

            // 设置邮件消息的主题
            mailMessage.setSubject(subject);
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();

            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(content, MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
            mainPart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);

            if (attachStream != null && StringUtils.isNotBlank(attachType)) {
                ByteArrayDataSource byteArrDataSrc = new ByteArrayDataSource(attachStream, attachType);
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setDataHandler(new DataHandler(byteArrDataSrc));
                mimeBodyPart.setFileName(MimeUtility.encodeWord(fileName == null ? "附件" : fileName));
                mainPart.addBodyPart(mimeBodyPart);
            }
            // 发送邮件
            Transport.send(mailMessage);
            LOGGER.info("Successfully sent mail to: " + toAddressStr);
            return true;
        } catch (Exception e) {
            LOGGER.warn("Error while send mail to: " + toAddressStr, e);
            return false;
        }
    }

    /**
     * 发送文本格式邮件
     *
     * @author iHelin
     * @since 2017/9/15 14:17
     */
    public static boolean sendTextMail(String toAddressStr, String toPersonalName, String subject, String content) {

        // 判断是否需要身份认证
        MyAuthenticator authenticator = new MyAuthenticator(MAIL_USERNAME, MAIL_PASSWORD);
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(getProperties(), authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(MAIL_FROM_ADDRESS, MAIL_FROM_PERSONAL_NAME);
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);

            // 创建邮件的接收者地址，并设置到邮件消息中
            // Message.RecipientType.TO属性表示接收者的类型为TO
            List<String> addrList = Lists.newArrayList();
            for (String addrStr : toAddressStr.split(";")) {
                if (StringUtils.isNotBlank(addrStr)) {
                    addrList.add(addrStr.trim());
                }
            }
            int receSize = addrList.size();
            if (receSize < 1) {
                LOGGER.warn("没有收件邮箱地址。");
                return false;
            }
            if (receSize == 1) {
                String addrStr = addrList.get(0);
                Address address = new InternetAddress(addrStr, toPersonalName);
                mailMessage.setRecipient(Message.RecipientType.TO, address);
            } else {
                Address[] addrArr = new Address[receSize];
                int inx = 0;
                for (String addrStr : addrList)
                    addrArr[inx++] = new InternetAddress(addrStr, addrStr);
                mailMessage.setRecipients(Message.RecipientType.TO, addrArr);
            }

            // 设置邮件消息的主题
            mailMessage.setSubject(subject);
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容
            String mailContent = content;
            mailMessage.setText(mailContent);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (Exception ex) {
            LOGGER.warn("Error while send mail to: " + toAddressStr, ex);
            return false;
        }
    }

    private MailUtil() {
        //工具类不允许实例化
    }

}
