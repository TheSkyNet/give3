package org.give3.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.give3.dao.PersonDao;
import org.give3.domain.User;
import org.give3.domain.PurchaseOrder;
import org.give3.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sun.mail.iap.BadCommandException;

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
       String recipient = userDao.getUser(username).getUsername();
       String subject = "password reset for give3.org";
       String body = "Your password has been changed to: " + newPassword +
                   ". For security reasons, you should log in and update your password.";
       emailService.send(new String[] {recipient}, subject, body);

       Map<String, Object> modelMap = new HashMap<String, Object>();
       return new ModelAndView("redirect:/login", modelMap);
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/account/password/update", method = RequestMethod.GET)
    public ModelAndView updatePasswordRequest() throws Exception {

       Map<String, Object> modelMap = new HashMap<String, Object>();
       return new ModelAndView("updatePassword", modelMap);
    }
    
    @RequestMapping(value = "/account/register", method = RequestMethod.GET)
    public ModelAndView getAccountRegister(@RequestParam(value = "username", required = true) String username, 
                                           @RequestParam(value = "key",      required = true) String key) throws Exception {

       User user = userDao.getUser(username);
       
       // TODO handle if user does not exist
       // TODO handle is user has already registered (check lastLogin != null)
       
       if(! user.getPassword().equals(key)) {
          throw new BadCommandException();
       }
       
       user.setEnabled(true);
       user.setRegistration(new Date());
       userDao.updatePerson(user);
       
       Map<String, Object> modelMap = new HashMap<String, Object>();
       return new ModelAndView("redirect:/", modelMap);
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/account/password/update", method = RequestMethod.POST)
    public ModelAndView updatePassword(Principal principal, 
          @RequestParam(value = "oldPassword", required = true) String oldPassword,
          @RequestParam(value = "newPassword", required = true) String newPassword) throws Exception 
    {

       userDao.updatePassword(principal.getName(), oldPassword, newPassword);
       
       Map<String, Object> modelMap = new HashMap<String, Object>();
       
       return new ModelAndView("redirect:/account", modelMap);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ModelAndView getUserAccount(Principal principal) throws Exception {

       Set<PurchaseOrder> orders = new HashSet<PurchaseOrder>();
       User user = userDao.getUserSerializable(principal.getName());
       Map<String, Object> modelMap = new HashMap<String, Object>();
       modelMap.put("user", user);
       modelMap.put("orders", orders);
       return new ModelAndView("account", modelMap);
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ModelAndView fullfillOrder(Model model, Principal principal, @RequestParam(value = "emailAddress", required = true) String emailAddress) {
       
       User user = userDao.getUser(principal.getName());
       user.setUsername(emailAddress);
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
