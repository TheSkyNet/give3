package org.give3.email;

import javax.mail.MessagingException;


public interface EmailService {
   void send(String recipients[], String subject, String message) throws MessagingException;
}
