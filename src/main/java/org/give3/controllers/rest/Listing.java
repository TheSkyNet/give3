package org.give3.controllers.rest;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.give3.dao.PersonDao;
import org.give3.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Listing {

    public static final String APPLICATION_JSON = MediaType.APPLICATION_JSON.toString();
   
    @Autowired
    private PersonDao userDao;

    
    // TODO use ResponseBody to return a json object
    // currently responding with content type: text/html;charset=ISO-8859-1
    // maybe need to fix content type in response?
    
    // http://springinpractice.com/2012/02/22/supporting-xml-and-json-web-service-endpoints-in-spring-3-1-using-responsebody/
    // http://stackoverflow.com/questions/3340050/springs-json-not-being-resolved-with-appropriate-response
    
    // TODO this method gets in an infinite loop since role and person point to each other, I thought JsonBackReference was supposed to fix that?
    // try later version of jackson that handles circular references http://www.cowtowncoder.com/blog/archives/2012/03/entry_466.html
    // or change data structures to not use circular references in the first place
    
    @RequestMapping(value = "/rest/person", method = RequestMethod.GET)
    public @ResponseBody User listingPerson() {
       
       User person = userDao.getUserSerializable("jay");
       return person;
    }
    
   // TODO this method gets in an infinite loop since role and person point to each other, I thought JsonBackReference was supposed to fix that?
   @RequestMapping(value = "/rest/listing", method = RequestMethod.GET)
   @PreAuthorize("isAuthenticated()")
   public ModelAndView listingJson(final Principal loggedInUser, final HttpServletResponse response) {

      JsonView view = new JsonView();
      User person = userDao.getUserSerializable(loggedInUser.getName());
      return view.render(person, response);
   }

   public class JsonView {

      public ModelAndView render(Object model, HttpServletResponse response)
      {
          MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();

          MediaType jsonMimeType = MediaType.APPLICATION_JSON;

          try {
              jsonConverter.write(model, jsonMimeType, new ServletServerHttpResponse(response));
          } catch (HttpMessageNotWritableException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          }

          return null;
      }
  }

}
