package org.give3.security;

import org.give3.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {

   @Autowired
   private PersonDao userDao;

   public AppUserDetailsService() {
      
   }
   
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      UserDetails user = userDao.getUser(username);
      if(user == null) {
         throw new UsernameNotFoundException("for " + username);
      }
      return user;
   }

}
