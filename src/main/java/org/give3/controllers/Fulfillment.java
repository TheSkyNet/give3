package org.give3.controllers;

import org.give3.dao.ItemDao;
import org.give3.dao.PersonDao;
import org.give3.dao.PurchaseOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/fulfillment")
public class Fulfillment {

   @Autowired
   private PurchaseOrderDao orderDao;
   
   @Autowired
   private ItemDao itemDao;

   @Autowired
   private PersonDao personDao;
   
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @RequestMapping(value="fulfill/{orderId}", method=RequestMethod.POST) 
   public ModelAndView fullfillOrder(Model model, @PathVariable Long orderId) {
      
      orderDao.fulfillOrder(orderId);
      
      return new ModelAndView("redirect:/fulfillment", model.asMap());
   }

   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @RequestMapping(method=RequestMethod.GET) 
   public ModelAndView getPage(Model model) {
      
      model.addAttribute("orders", orderDao.getPage(0, 10));

      return new ModelAndView("/fulfillment", model.asMap());
   }

   /**
    * @return the dao
    */
   public ItemDao getItemDao() {
      return itemDao;
   }

   /**
    * @param dao the dao to set
    */
   public void setItemDao(ItemDao dao) {
      this.itemDao = dao;
   }

   /**
    * @return the personDao
    */
   public PersonDao getPersonDao() {
      return personDao;
   }

   /**
    * @param personDao the personDao to set
    */
   public void setPersonDao(PersonDao personDao) {
      this.personDao = personDao;
   }

   public PurchaseOrderDao getOrderDao() {
      return orderDao;
   }

   public void setOrderDao(PurchaseOrderDao orderDao) {
      this.orderDao = orderDao;
   }
}
