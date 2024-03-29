package org.give3.controllers;


import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.give3.dao.PersonDao;
import org.give3.domain.User;
import org.give3.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("person")
public class Register {

    @Autowired
    private PersonDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Register() {
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView getNewUserPage() throws Exception {

       // person is bound to the form components, see @SessionAttribute above
       Map<String, Object> modelMap = new HashMap<String, Object>();
       modelMap.put("person", new User());
       return new ModelAndView("register", modelMap);
    }

    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createNewUser(final @Valid @ModelAttribute User user,
                                      final BindingResult result,
                                      final SessionStatus status)
    {
       
       String plainPassword = user.getPassword();
       user.setPassword(passwordEncoder.encode(plainPassword));
       
        final Map<String, Object> modelMap = new HashMap<String, Object>();
    
        String viewname = "register";

        if (userDao.isUserPresent(user))
        {
            result.rejectValue("username", "", "user already exists");
            modelMap.put("person", new User());
        }

        if (! result.hasErrors())
        {
            // TODO don't commit an object directly from the user, use a "safeCopy()" (which doesn't copy the PK) instead
           user.getRoles().add(new Role(user, Role.APPLICATION_ROLE.ROLE_USER));
           userDao.createNewUser(user);
           status.setComplete();
           modelMap.put("username", user.getUsername());
           modelMap.put("password", plainPassword); // unencoded password is used to log in on the next page
           viewname = "registerComplete";
        }

       return new ModelAndView(viewname, modelMap);
    }

    public PersonDao getUserDao() {
        return userDao;
    }

    public void setUserDao(PersonDao userDao) {
        this.userDao = userDao;
    }
}
