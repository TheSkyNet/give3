package org.give3.controllers;

import java.util.HashMap;
import java.util.Map;

import org.give3.dao.PersonDao;
import org.give3.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Users {

    @Autowired
    private PersonDao userDao;

    public Users() {
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView getUsersPage() throws Exception {

       Map<String, Object> modelMap = new HashMap<String, Object>();
       modelMap.put("users", userDao.getUsers());
       return new ModelAndView("users", modelMap);
    }

    public PersonDao getUserDao() {
        return userDao;
    }

    public void setUserDao(PersonDao userDao) {
        this.userDao = userDao;
    }
}
