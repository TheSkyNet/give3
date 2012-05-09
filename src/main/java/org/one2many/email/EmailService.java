package org.one2many.email;

import javax.mail.MessagingException;


public interface EmailService {
   void send(String recipients[], String subject, String message) throws MessagingException;
}
