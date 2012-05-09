package org.one2many.email;

import java.security.Security;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Gmail implements EmailService {

   private static final String SMTP_HOST_NAME = "smtp.gmail.com";
   private static final String SMTP_PORT = "465";
   private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

   private String username;
   private String password;
   private Properties props = new Properties();
   
   public Gmail(String username, String password) {
      
      this.username = username;
      this.password = password;
      
      props.put("mail.smtp.host", SMTP_HOST_NAME);
      props.put("mail.smtp.auth", "true");
      props.put("mail.debug", "true");
      props.put("mail.smtp.port", SMTP_PORT);
      props.put("mail.smtp.socketFactory.port", SMTP_PORT);
      props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
      props.put("mail.smtp.socketFactory.fallback", "false");
      
      // TODO test without adding provider
      // TODO resolve access restriction message
      Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
   }
   
   public void send(String recipients[], String subject, String message) throws MessagingException {
   
      Session session = Session.getDefaultInstance(props,
         new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
            }
      });
      
      // TODO a lot of messages are logged, might not neet debug true
      session.setDebug(true);
      
      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress(username));
      
      InternetAddress[] addressTo = new InternetAddress[recipients.length];
      for (int i = 0; i < recipients.length; i++) {
         addressTo[i] = new InternetAddress(recipients[i]);
      }
      msg.setRecipients(Message.RecipientType.TO, addressTo);
      
      // Setting the Subject and Content Type
      msg.setSubject(subject);
      msg.setContent(message, "text/plain");
      Transport.send(msg);
   }
}
