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
 
/*
	Esto es un solo archivo java, correspondiente al envio por mail
	Funciona solo con gmail
	En este script, en mailfrom y password se deben de poner el user y pass del doctor
	En mailTo va el destinatario
	El arrayFiles es un arreglo cuyas entradas son las rutas a los reportes PDF
	Agregar en las librerias mail.jar (API de GMAIL)	
	Al ejecutar, es mas que probable que bote error, debido a los permisos de Google 
		con el correo, cambiarlo accediendo a esta direccion:

*********
https://www.google.com/settings/security/lesssecureapps?rfn=27&rfnc=1&asae=2&anexp=lbe2-R1_C	
*********

*/

public class EmailAttachmentSender {
 
    public static void sendEmailWithAttachments(String toAddress, String[] attachFiles)
            throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", "sistema.kaixo@gmail.com");
        properties.put("mail.password", "mejoramientoestodo");
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sistema.kaixo@gmail.com", "mejoramientoestodo");
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress("sistema.kaixo@gmail.com"));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject("New email with attachments");
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("I have some attachments for you.", "text/html");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds attachments
        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
            }
        }
 
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
 
        // sends the e-mail
        Transport.send(msg);
 
    }
 
    /**
     * Test sending e-mail with attachments
     
    public static void main(String[] args) {
        // SMTP info
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "sistema.kaixo@gmail.com";
        String password = "mejoramientoestodo";
 
        // message info
        String mailTo = "paololara1204@gmail.com";
        String subject = "New email with attachments";
        String message = "I have some attachments for you.";
 
        // attachments
        String[] attachFiles = new String[1];
        attachFiles[0] = "/home/efmoran/Downloads/Kaixo_Viteri/Proy/facturas/09589704212016-09-06 19:30.pdf";
  
        try {
            sendEmailWithAttachments(mailTo, attachFiles);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }*/
}