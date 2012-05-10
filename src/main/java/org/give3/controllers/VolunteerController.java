package org.give3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/volunteer")
public class VolunteerController {

   @RequestMapping(method=RequestMethod.GET) 
   public void form() {

   }

}
