package org.give3.controllers;

import javax.mail.MessagingException;

import org.give3.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/donate")
public class DonateController {

   @Autowired
   private EmailService emailService;
   
   @Autowired
   private String toAddress;
   
   // TODO  as of Java 7 there's a method that returns the value directly: System.lineSeparator()
   public final static String NEWLINE = System.getProperty("line.separator");
   
   @RequestMapping(method=RequestMethod.POST) 
   public String processSubmit(final @RequestParam(value = "donerName", required = true) String donerName, 
                               final @RequestParam(value = "emailAddress", required = true) String emailAddress,
                               final @RequestParam(value = "itemName", required = true) String itemName,
                               final @RequestParam(value = "itemDescription", required = true) String itemDescription,
                               final @RequestParam(value = "itemCondition", required = true) String itemCondition) 
   {

      String message = "";
      message += "Name: " + donerName +  NEWLINE;
      message += "Address: " + emailAddress + NEWLINE;
      message += "Item: " + itemName +  NEWLINE;
      message += "Description: " + itemDescription + NEWLINE;
      message += "Condition: " + itemCondition +  NEWLINE;
      
      try {
         emailService.send(new String[] {toAddress}, itemName, message);
      }
      catch(MessagingException me) {
         // TODO log error here
      }

      // TODO return success and failure messages
      
      return "redirect:/";

   }

   @RequestMapping(method=RequestMethod.GET) 
   public void form() {

   }

   /**
    * @return the toAddress
    */
   public String getToAddress() {
      return toAddress;
   }

   /**
    * @param toAddress the toAddress to set
    */
   public void setToAddress(String toAddress) {
      this.toAddress = toAddress;
   }

}
