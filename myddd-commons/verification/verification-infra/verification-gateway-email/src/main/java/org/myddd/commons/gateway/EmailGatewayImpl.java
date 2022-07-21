package org.myddd.commons.gateway;

import com.google.common.base.Strings;
import org.myddd.commons.sms.InvalidEmailConfigException;
import org.myddd.commons.sms.SendEmailErrorException;
import org.myddd.commons.sms.application.EmailGateway;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Named
public class EmailGatewayImpl implements EmailGateway {

    @Value("${mail.smtp.host}")
    private String smtpHost;

    @Value("${mail.smtp.port}")
    private String smtpPort;

    @Value("${mail.smtp.starttls}")
    private boolean starttls;

    @Value("${mail.smtp.username}")
    private String username;

    @Value("${mail.smtp.from}")
    private String from;

    @Value("${mail.smtp.password}")
    private String password;

    @Value("${sms.content}")
    private String smsContent;

    @Override
    public void sendSmsToEmail(String email, String code) {
        var msg = String.format(smsContent,code);
        sendEmail(email, msg);
    }

    private void sendEmail(String email, String msg) {
        try {
            var session  = Session.getInstance(emailProperties(), new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("【低代码平台】验证码");

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        }catch (Exception e){
            e.printStackTrace();
            throw new SendEmailErrorException(e.getLocalizedMessage());
        }
    }

    private Properties emailProperties(){
        checkEmailConfig();
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
//        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", smtpHost);
        prop.put("mail.smtp.port", smtpPort);
        return prop;
    }

    private void checkEmailConfig(){
        if(Strings.isNullOrEmpty(smtpHost) || Strings.isNullOrEmpty(smtpPort) || Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(from)){
            throw new InvalidEmailConfigException();
        }
    }
}
