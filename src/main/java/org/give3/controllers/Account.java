package org.give3.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.give3.dao.PersonDao;
import org.give3.domain.Person;
import org.give3.domain.PurchaseOrder;
import org.give3.email.EmailService;
import org.give3.security.HashEncoderMD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Account {

    @Autowired
    private PersonDao userDao;

    @Autowired
    private EmailService emailService;
    
    
    public Account() {
    }
    
    @RequestMapping(value = "/account/password/reset", method = RequestMethod.GET)
    public ModelAndView resetPasswordRequest() throws Exception {

       Map<String, Object> modelMap = new HashMap<String, Object>();
       return new ModelAndView("resetPassword", modelMap);
    }
    
    @RequestMapping(value = "/account/password/reset", method = RequestMethod.POST)
    public ModelAndView resetPasswordSubmit(@RequestParam(value = "username", required = true) String username) throws Exception {

       // TODO throw exception and handle it if user does not exist
       
       String newPassword = userDao.resetPassword(username);
       String recipient = userDao.getUser(username).getEmail();
       String subject = "password reset for give3.org";
       String body = "Your password has been changed to: " + newPassword +
                   ". For security reasons, you should log in and update your password.";
       emailService.send(new String[] {recipient}, subject, body);

       Map<String, Object> modelMap = new HashMap<String, Object>();
       return new ModelAndView("redirect:/", modelMap);
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/account/password/update", method = RequestMethod.POST)
    public ModelAndView updatePassword(Principal principal) throws Exception {

       userDao.updatePassword(principal.getName(), "", "");
       
       Map<String, Object> modelMap = new HashMap<String, Object>();
       
       return new ModelAndView("/account/password/update", modelMap);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ModelAndView getUserAccount(Principal principal) throws Exception {

       Set<PurchaseOrder> orders = userDao.getOrders(principal.getName());
       Person user = userDao.getUserSerializable(principal.getName());
       Map<String, Object> modelMap = new HashMap<String, Object>();
       modelMap.put("user", user);
       modelMap.put("orders", orders);
       return new ModelAndView("account", modelMap);
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ModelAndView fullfillOrder(Model model, Principal principal, @RequestParam(value = "emailAddress", required = true) String emailAddress) {
       
       Person user = userDao.getUser(principal.getName());
       user.setEmail(emailAddress);
       userDao.updatePerson(user);
       
       return new ModelAndView("redirect:/account", model.asMap());
    }

    public PersonDao getUserDao() {
        return userDao;
    }

    public void setUserDao(PersonDao userDao) {
        this.userDao = userDao;
    }

   public EmailService getEmailService() {
      return emailService;
   }

   public void setEmailService(EmailService emailService) {
      this.emailService = emailService;
   }
}
