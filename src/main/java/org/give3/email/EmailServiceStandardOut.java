package org.give3.email;

import javax.mail.MessagingException;


public class EmailServiceStandardOut implements EmailService {
   
   public EmailServiceStandardOut() {
      
   }
   
   @Override
   public void send(String recipients[], String subject, String message) throws MessagingException {
      System.out.println("-----------------------------------");
      System.out.println("Sending email...");
      for(String r : recipients) {
         System.out.println("Recipent: " + r);
      }
      System.out.println("Subject: " + subject);
      System.out.println("Message: " + message);
      System.out.println("-----------------------------------");
   }
}
