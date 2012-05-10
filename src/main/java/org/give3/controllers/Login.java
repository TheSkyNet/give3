
package org.give3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// TODO the login form for the login page should be sent over https

// TODO when you go to a page with the user/pass fields already filled, should be able to hit submit
// and have it work (currently md5 encoding is only done on type/click in the password field)


/**
 * maps the root url to the main page.
 */
@Controller
public class Login {

   @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
   public String accessDenied() {
      return "accessDenied";
   }

    /**
     * This method returns the page with a form.
     *
     * @return view name.
     */
   @RequestMapping(value = "/login", method = RequestMethod.GET)
   public String loginPage() {
      return "login";
   }
}
