package org.give3.security;

import junit.framework.Assert;

import org.junit.Test;

public class HashEncoderTest {
   

   @Test
   public void sha2() throws Exception {
      String computed = new HashEncoderSHA2().toHash("jay");
      String jaySha2 = "BFEF4ADC39F01B033FE749BB5F28F10B581FEF319D34445D21A7BC63FE732FA3";
      Assert.assertEquals(jaySha2, computed);
   }
   
   @Test
   public void md5() throws Exception {
      
      String computed = new HashEncoderMD5().toHash("jay");
      String jayMD5 = "BABA327D241746EE0829E7E88117D4D5";
      Assert.assertEquals(jayMD5, computed);
   }
}
