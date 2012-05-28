package org.give3.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailServiceSMTP implements EmailService {

   private String username;
   private String password;
   private String host;
   private boolean debug;
   private Properties props = new Properties();
   
   
   public EmailServiceSMTP(String username, String password, String smtpHostName, boolean debug) {
      
      this.username = username;
      this.password = password;
      this.host = smtpHostName;
      this.debug = debug;
      
      //don't need to specify port if you're using default port 25
      props.put("mail.smtp.auth", "true");
      
      // TODO implement EmailServiceSSL
      // read up on the java mail api and examples rather than blindly copy/paste code from the internet
      // there are some common mistakes: http://www.oracle.com/technetwork/java/faq-135477.html#commonmistakes
      // will probably need these properties
      // props.put("mail.smtps.auth", "true");
      // props.put("mail.smtp.ssl.enable", "true");
      // mail.give3.org should be the pop server address, with default pop port 110 (try 995 for SSL) 
      // and smtp port 25 (ssl smtp port 465)
      // for SSL the mail server is using a self-signed cert which might be causing trouble.
      // IMAP should also be available
         
   }
   
   public void send(String recipients[], String subject, String message) throws MessagingException {
   
      Session session = Session.getInstance(props);

      session.setDebug(debug);
      
      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress(username));
      msg.setSubject(subject);
      msg.setContent(message, "text/plain");
      
      // TODO try Java7's try with resources feature
      Transport transport = session.getTransport("smtp");
      try {
         transport.connect(host, username, password);
         transport.sendMessage(msg, toAddress(recipients));
      }
      finally {
         transport.close();
      }
      
   }
   
   public InternetAddress[] toAddress(String[] addresses) throws AddressException {
      Address a;
      InternetAddress[] addressTo = new InternetAddress[addresses.length];
      for (int i = 0; i < addresses.length; i++) {
         addressTo[i] = new InternetAddress(addresses[i]);
      }
      return addressTo;
   }
}
