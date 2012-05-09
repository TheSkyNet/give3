package org.one2many.controllers;

import java.security.Principal;
import java.util.Date;

import javax.validation.Valid;

import org.one2many.ajax.AjaxUtils;
import org.one2many.dao.KarmaKashDao;
import org.one2many.dao.PersonDao;
import org.one2many.domain.KarmaKash;
import org.one2many.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/redeem")
@SessionAttributes("formBean")
public class RedeemController {

   @Autowired
   private PersonDao personDao;
   
   @Autowired
   private KarmaKashDao dao;
   
   // Invoked on every request
   @ModelAttribute
   public void ajaxAttribute(WebRequest request, Model model) {
      model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
   }

   @PreAuthorize("isAuthenticated()")
   @RequestMapping(method=RequestMethod.POST) 
   public String processSubmit(@Valid KarmaKash formBean, Principal principal, BindingResult result, 
                        @ModelAttribute("ajaxRequest") boolean ajaxRequest, 
                        Model model, RedirectAttributes redirectAttrs, SessionStatus status) {
      if (result.hasErrors()) {
         return null;
      }
      
      // Success response handling
      // save to DB and clear the "form" attribute from the session via SessionStatus.setCompleted().
      KarmaKash k = dao.getKarmaKash(formBean.getCode());
      Person user = personDao.getUser(principal.getName());
      k.setRedeemedBy(user);
      k.setRedeemedOn(new Date());
      user.setBalance(user.getBalance() + k.getValue());
      
      dao.update(k);
      personDao.updatePerson(user);
      
      status.setComplete();

      String message = "Your balance is now " + user.getBalance();
      
      if (ajaxRequest) {
         // prepare model for rendering success message in this request
         model.addAttribute("message", message);
         return null;
      } else {
         // store a success message for rendering on the next request after redirect
         // redirect back to the form to render the success message along with newly bound values
         redirectAttrs.addFlashAttribute("message", message);
         return "redirect:/redeem";
      }
   }

   @PreAuthorize("isAuthenticated()")
   @RequestMapping(method=RequestMethod.GET) 
   public void form() {

   }

   @ModelAttribute("karmakash")
   public KarmaKash createFormItem() {
      return new KarmaKash();
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
