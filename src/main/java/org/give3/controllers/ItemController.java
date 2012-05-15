package org.give3.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.give3.ajax.AjaxUtils;
import org.give3.dao.ItemDao;
import org.give3.dao.OrderFailedException;
import org.give3.dao.PersonDao;
import org.give3.domain.Item;
import org.give3.domain.Person;
import org.give3.domain.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/item")
@SessionAttributes("formBean")
public class ItemController {

   @Autowired
   private ItemDao dao;

   @Autowired
   private PersonDao personDao;
   
   // Invoked on every request
   @ModelAttribute
   public void ajaxAttribute(WebRequest request, Model model) {
      model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
   }

   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @RequestMapping(method=RequestMethod.POST) 
   public String processSubmit(@Valid Item formBean, 
                              BindingResult result, 
                              @ModelAttribute("ajaxRequest") boolean ajaxRequest, 
                              Model model, 
                              RedirectAttributes redirectAttrs, 
                              SessionStatus status) 
   {
      if (result.hasErrors()) {
         return null;
      }
      
      // Success response handling
      // save to DB and clear the "form" attribute from the session via SessionStatus.setCompleted().
      dao.createItem(formBean);
      status.setComplete();

      String message = "Form submitted successfully.";
      
      if (ajaxRequest) {
         // prepare model for rendering success message in this request
         model.addAttribute("message", message);
         return null;
      } else {
         // store a success message for rendering on the next request after redirect
         // redirect back to the form to render the success message along with newly bound values
         redirectAttrs.addFlashAttribute("message", message);
         return "redirect:/item";
      }
   }

   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @RequestMapping(method=RequestMethod.GET) 
   public void form() {

   }

   // TODO handle if item doesn't exist
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @RequestMapping(value="{id}", method=RequestMethod.GET) 
   public ModelAndView editForm(Model model, @PathVariable Long id) {
      
      Item item = dao.getById(id);

      model.addAttribute("item", item);
      
      return new ModelAndView("item", model.asMap());
      
   }


   // TODO put page controllers in a logical page structure
   
   // TODO can id and principle be automatically mapped to Item and Person, and those passed to the controller?
   
   @PreAuthorize("isAuthenticated()")
   @RequestMapping(value="buy/{id}", method=RequestMethod.GET) 
   public ModelAndView getCheckoutPage(Model model, @PathVariable Long id, Principal principal) {

      // TODO handle if you don't have enough in your account balance

      // TODO handle if item doesn't exist

      Item item = dao.getById(id);
      Person user = personDao.getUser(principal.getName());
      
      model.addAttribute("item", item);
      model.addAttribute("user", user);

      return new ModelAndView("checkout", model.asMap());
      
   }
   
   @PreAuthorize("isAuthenticated()")
   @RequestMapping(value="buy/{id}", method=RequestMethod.POST) 
   public ModelAndView buy(Model model, @PathVariable Long id, Principal principal) {

      // TODO handle if item doesn't exist
      
      try {
         PurchaseOrder order = dao.createOrder(principal.getName(), id);
         model.addAttribute("order", order);
      }
      catch(OrderFailedException ofe) {
         model.addAttribute("message", ofe.getMessage());
         return new ModelAndView("orderFailed", model.asMap());
      }

      return new ModelAndView("orderComplete", model.asMap());
   }
   
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @RequestMapping(value="{id}", method=RequestMethod.POST) 
   public String editFormPost(@PathVariable Long id,
                              @Valid Item formBean, 
                              BindingResult result, 
                              @ModelAttribute("ajaxRequest") boolean ajaxRequest, 
                              Model model, 
                              RedirectAttributes redirectAttrs, 
                              SessionStatus status)
   {
      
      
      if (result.hasErrors()) {
         return null;
      }
      
      // Success response handling
      // save to DB and clear the "form" attribute from the session via SessionStatus.setCompleted().
      formBean.setId(id);
      dao.updateItem(formBean);
      status.setComplete();

      String message = "Form submitted successfully.";
      
      if (ajaxRequest) {
         // prepare model for rendering success message in this request
         model.addAttribute("message", message);
         return null;
      } else {
         // store a success message for rendering on the next request after redirect
         // redirect back to the form to render the success message along with newly bound values
         redirectAttrs.addFlashAttribute("message", message);
         return "redirect:/item/listing";
      }
      
   }
   
   @RequestMapping(value="listing", method=RequestMethod.GET)
   public ModelAndView listingPaged(Model model,
                                    @RequestParam(value="start", required=false, defaultValue="0") Integer start,
                                    @RequestParam(value="size", required=false, defaultValue="5") Integer size) 
   {
      
      long total = dao.getCount();
      int prevStart = start - size;
      int nextStart = start + size;
      List<Item> items = dao.getPage(start, size);
      model.addAttribute("items", items);
      model.addAttribute("prevStart", prevStart);
      model.addAttribute("start", start);
      model.addAttribute("nextStart", nextStart);
      model.addAttribute("size", size);
      model.addAttribute("total", total);
      model.addAttribute("prevEnabled", prevStart >= 0);
      model.addAttribute("nextEnabled", nextStart < total);
      model.addAttribute("pageEnd", Math.min(nextStart, total));
      
      return new ModelAndView("listing", model.asMap());
   }
   
   @ModelAttribute("item")
   public Item createFormItem() {
      return new Item();
   }

   /**
    * @return the dao
    */
   public ItemDao getDao() {
      return dao;
   }

   /**
    * @param dao the dao to set
    */
   public void setDao(ItemDao dao) {
      this.dao = dao;
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
}
