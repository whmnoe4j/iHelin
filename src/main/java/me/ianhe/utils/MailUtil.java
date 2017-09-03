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

    static class MailSenderInfo {
        // 发送邮件的服务器的IP和端口
        private String mailServerHost;
        private String mailServerPort;
        // 邮件发送者的地址
        private String fromAddress;
        // 邮件接收者的地址
        private String toAddress;
        // 登录邮件发送服务器的用户名和密码
        private String username;
        private String password;
        // 是否需要身份验证
        private boolean validate;
        // 邮件主题
        private String subject;
        // 邮件的文本内容
        private String content;

        private String fileName;
        private InputStream attachStream;
        private String attchType;

        private String fromPersonalName;
        private String toPersonalName;

        /**
         * 获得邮件会话属性
         */
        public Properties getProperties() {
            Properties p = new Properties();
            p.put("mail.smtp.host", this.mailServerHost);
            p.put("mail.smtp.port", this.mailServerPort);
            p.put("mail.smtp.auth", validate ? "true" : "false");
            return p;
        }
    }

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

    static class SimpleMailSender {
        /**
         * 以文本格式发送邮件
         *
         * @param mailInfo 待发送的邮件的信息
         */
        public boolean sendTextMail(MailSenderInfo mailInfo) {
            // 判断是否需要身份认证
            MyAuthenticator authenticator = null;
            if (mailInfo.validate) {
                // 如果需要身份认证，则创建一个密码验证器
                authenticator = new MyAuthenticator(mailInfo.username, mailInfo.password);
            }
            // 根据邮件会话属性和密码验证器构造一个发送邮件的session
            Session sendMailSession = Session.getDefaultInstance(mailInfo.getProperties(), authenticator);
            try {
                // 根据session创建一个邮件消息
                Message mailMessage = new MimeMessage(sendMailSession);
                // 创建邮件发送者地址
                Address from = new InternetAddress(mailInfo.fromAddress, mailInfo.fromPersonalName);
                // 设置邮件消息的发送者
                mailMessage.setFrom(from);

                // 创建邮件的接收者地址，并设置到邮件消息中
                // Message.RecipientType.TO属性表示接收者的类型为TO
                List<String> addrList = Lists.newArrayList();
                for (String addrStr : mailInfo.toAddress.split(";")) {
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
                    Address address = new InternetAddress(addrStr, mailInfo.toPersonalName);
                    mailMessage.setRecipient(Message.RecipientType.TO, address);
                } else {
                    Address[] addrArr = new Address[receSize];
                    int inx = 0;
                    for (String addrStr : addrList)
                        addrArr[inx++] = new InternetAddress(addrStr, addrStr);
                    mailMessage.setRecipients(Message.RecipientType.TO, addrArr);
                }

                // 设置邮件消息的主题
                mailMessage.setSubject(mailInfo.subject);
                // 设置邮件消息发送的时间
                mailMessage.setSentDate(new Date());
                // 设置邮件消息的主要内容
                String mailContent = mailInfo.content;
                mailMessage.setText(mailContent);
                // 发送邮件
                Transport.send(mailMessage);
                return true;
            } catch (Exception ex) {
                LOGGER.warn("Error while send mail to: " + mailInfo.toAddress, ex);
                return false;
            }
        }

        /**
         * 以HTML格式发送邮件
         *
         * @param mailInfo 待发送的邮件信息
         */
        boolean sendHtmlMail(MailSenderInfo mailInfo) {
            // 判断是否需要身份认证
            MyAuthenticator authenticator = null;
            Properties pro = mailInfo.getProperties();
            // 如果需要身份认证，则创建一个密码验证器
            if (mailInfo.validate) {
                authenticator = new MyAuthenticator(mailInfo.username, mailInfo.password);
            }
            // 根据邮件会话属性和密码验证器构造一个发送邮件的session
            Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
            try {
                // 根据session创建一个邮件消息
                Message mailMessage = new MimeMessage(sendMailSession);
                // 创建邮件发送者地址
                Address fromAddress = new InternetAddress(mailInfo.fromAddress, mailInfo.fromPersonalName);
                // 设置邮件消息的发送者
                mailMessage.setFrom(fromAddress);

                // 创建邮件的接收者地址，并设置到邮件消息中
                // Message.RecipientType.TO属性表示接收者的类型为TO

                String[] addresses = mailInfo.toAddress.split(";");
                int length = addresses.length;
                if (length < 1) {
                    LOGGER.warn("没有收件邮箱地址。");
                    return false;
                } else {
                    if (length == 1) {
                        String addressStr = addresses[0].trim();
                        Address toAddress = new InternetAddress(addressStr, mailInfo.toPersonalName);
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
                mailMessage.setSubject(mailInfo.subject);
                // 设置邮件消息发送的时间
                mailMessage.setSentDate(new Date());
                // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
                Multipart mainPart = new MimeMultipart();

                // 创建一个包含HTML内容的MimeBodyPart
                BodyPart html = new MimeBodyPart();
                // 设置HTML内容
                html.setContent(mailInfo.content, MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
                mainPart.addBodyPart(html);
                // 将MiniMultipart对象设置为邮件内容
                mailMessage.setContent(mainPart);

                if (mailInfo.attachStream != null && StringUtils.isNotBlank(mailInfo.attchType)) {
                    ByteArrayDataSource byteArrDataSrc = new ByteArrayDataSource(mailInfo.attachStream,
                            mailInfo.attchType);
                    MimeBodyPart mimeBodyPart = new MimeBodyPart();
                    mimeBodyPart.setDataHandler(new DataHandler(byteArrDataSrc));
                    mimeBodyPart.setFileName(MimeUtility.encodeWord(mailInfo.fileName == null ? "附件" : mailInfo.fileName));
                    mainPart.addBodyPart(mimeBodyPart);
                }
                // 发送邮件
                Transport.send(mailMessage);
                LOGGER.info("Successfully sent mail to: " + mailInfo.toAddress);
                return true;
            } catch (Exception e) {
                LOGGER.warn("Error while send mail to: " + mailInfo.toAddress, e);
                return false;
            }
        }
    }

    /**
     * 发送html邮件
     *
     * @author iHelin
     * @since 2017/9/3 11:44
     */
    public static boolean sendMail(String toAddress, String toPersonalName, String subject, String content) {
        return sendMail(toAddress, toPersonalName, subject, content, null, null, null);
    }

    /**
     * 发送html邮件
     *
     * @author iHelin
     * @since 2017/9/3 11:44
     */
    public static boolean sendMail(String toAddress, String toPersonalName, String subject, String content, String fileName,
                                   InputStream attachStream, String type) {
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.mailServerHost = Global.get("mail.server");
        mailInfo.mailServerPort = Global.get("mail.port");
        mailInfo.validate = true;
        mailInfo.username = Global.get("mail.user");
        mailInfo.password = Global.get("mail.password");
        mailInfo.fromAddress = Global.get("mail.fromAddress");
        mailInfo.fromPersonalName = Global.get("mail.fromName");
        mailInfo.toPersonalName = toPersonalName;
        mailInfo.toAddress = toAddress;
        mailInfo.subject = subject;
        mailInfo.content = content;
        mailInfo.attachStream = attachStream;
        mailInfo.fileName = fileName;
        mailInfo.attchType = type;
        SimpleMailSender sms = new SimpleMailSender();
        return sms.sendHtmlMail(mailInfo);
    }

    private MailUtil() {
        //工具类不允许实例化
    }

}
