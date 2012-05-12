package org.give3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/distribution")
public class DistributionController {

   @RequestMapping(method=RequestMethod.GET) 
   public void get() {

   }

}
