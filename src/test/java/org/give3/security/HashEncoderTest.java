package org.give3.security;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class HashEncoderTest {
   
   @Test
   public void sha2() throws Exception {
      
      PasswordEncoder encoder = new HashEncoderSHA2();
      String computed = encoder.encode("jay");
      Assert.assertTrue(encoder.matches("jay", computed));
   }
   
   @Test
   public void md5() throws Exception {
      
      PasswordEncoder encoder = new HashEncoderMD5();
      String computed = encoder.encode("jay");
      Assert.assertTrue(encoder.matches("jay", computed));
   }
   
   @Test
   public void spring1() throws Exception {
      
      PasswordEncoder encoder = new StandardPasswordEncoder();
      String computed = encoder.encode("jay").toUpperCase();
      Assert.assertTrue(encoder.matches("jay", computed));

   }
}
