package org.one2many.register;


import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.one2many.dao.PersonDao;
import org.one2many.domain.Person;
import org.one2many.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("person")
public class Register {

    @Autowired
    private PersonDao userDao;

    public Register() {
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView getNewUserPage() throws Exception {

       // person is bound to the form components, see @SessionAttribute above
       Map<String, Object> modelMap = new HashMap<String, Object>();
       modelMap.put("person", new Person());
       return new ModelAndView("register", modelMap);
    }

    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createNewUser(final @Valid @ModelAttribute Person user,
                                      final BindingResult result,
                                      final SessionStatus status,
                                      final @RequestParam(value = "unencodedPassword", required = true) String password)
    {
       
        final Map<String, Object> modelMap = new HashMap<String, Object>();
    
        String viewname = "register";

        if (userDao.isUserPresent(user))
        {
            result.rejectValue("username", "", "user already exists");
            modelMap.put("person", new Person());
        }

        if (! result.hasErrors())
        {
            // TODO don't commit an object directly from the user, use a "safeCopy()" (which doesn't copy the PK) instead
           user.getRoles().add(new Role(user, Role.APPLICATION_ROLE.ROLE_USER));
           userDao.createNewUser(user);
           status.setComplete();
           modelMap.put("username", user.getUsername());
           modelMap.put("password", password); // unencoded password is used to log in on the next page
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
