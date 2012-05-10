package org.give3.controllers;

import org.give3.dao.KarmaKashDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/generate")
public class GenerateController {

   @Autowired
   private KarmaKashDao dao;
   
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @RequestMapping(method=RequestMethod.GET) 
   public String generateBatch() {
      dao.saveBatch(dao.generateBatch(10));
      return "redirect:/";
   }

   /**
    * @return the dao
    */
   public KarmaKashDao getDao() {
      return dao;
   }

   /**
    * @param dao the dao to set
    */
   public void setDao(KarmaKashDao dao) {
      this.dao = dao;
   }
}
