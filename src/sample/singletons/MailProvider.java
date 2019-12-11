package sample.singletons;

import sample.models.UserInfo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailProvider {
    private static MailProvider single_instance = null;


    private MailProvider() {
    }

    // static method to create instance of Singleton class
    public static MailProvider getInstance() {
        if (single_instance == null)
            single_instance = new MailProvider();

        return single_instance;
    }

    public boolean sendMailUsingTransportLayer(String receiverEmail, String title, String content) {
        final String username = UserSession.getInstance().getUserInfo().getEmail();
        final String password = UserSession.getInstance().getUserInfo().getPassword();


        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            return sendMail(receiverEmail, title, content, username, session);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean sendMail(String receiverEmail, String title, String content, String username, Session session) throws MessagingException {
        Message message = new MimeMessage(session);


        message.setFrom(new InternetAddress(username));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(receiverEmail)
        );
        message.setSubject(title);
        message.setText(content);
        Transport.send(message);
        System.out.println("Done");
        return true;
    }

    public boolean sendMailUsingSsl(String receiverEmail, String title, String content) {
        final String username = UserSession.getInstance().getUserInfo().getEmail();
        final String password = UserSession.getInstance().getUserInfo().getPassword();

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            return sendMail(receiverEmail, title, content, username, session);
        } catch (MessagingException e) {
            e.printStackTrace();
            return true;

        }
    }
}
