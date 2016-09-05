package utils;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author made
 */
public class EmailAttachmentSender {
    private final String host;
    private final String port;
    private final String mailFrom;
    private final String subject;
    private final String message;
    private final String password;
    private final String mailTo;
    
    
    public EmailAttachmentSender(String mailTo, String message){
        this.host = "smtp.gmail.com";
        this.port = "587";
        this.mailFrom = "paololara1204@gmail.com";
        this.password = "pass";
        this.subject = "Consulta medica";
        this.message = message;
        this.mailTo = mailTo;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getPassword() {
        return password;
    }

    public String getMailTo() {
        return mailTo;
    }
    

    public boolean sendEmail(EmailAttachmentSender info) throws AddressException, MessagingException, IOException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", info.getHost());
        properties.put("mail.smtp.port", info.getPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", info.getMailFrom());
        properties.put("mail.password", info.getPassword());
        
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(info.getMailFrom(), info.getPassword());
            }
        };
        
        Session session = Session.getInstance(properties, auth);
        Message msg = new MimeMessage(session);
        
        msg.setFrom(new InternetAddress(info.getMailFrom()));
        InternetAddress[] toAddresses = { new InternetAddress(info.getMailTo()) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(info.getSubject());
        msg.setSentDate(new Date());
        
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(info.getMessage(), "text/html");
        
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        
        msg.setContent(multipart);
        Transport.send(msg);
        return true;
    }
}
