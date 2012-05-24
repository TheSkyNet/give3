package org.give3.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.give3.dao.PersonDao;
import org.give3.domain.Person;
import org.give3.domain.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Account {

    @Autowired
    private PersonDao userDao;

    public Account() {
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
}
