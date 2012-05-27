package org.give3.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;


public class HashEncoderSHA2 implements PasswordEncoder {

   public static String SHA256 = "SHA-256";
   
   public HashEncoderSHA2() {
      
   }

   /**
    * 
    * @param input UTF8 string
    * @return hex encoded version of the bytes resulting from a SHA2 encryption.
    * 
    */
   // TODO use Java 7 multi-catch
   // TODO stack traces should be output in logs
   @Override
   public String encode(CharSequence rawPassword) {
      String output ="";
      try {
         MessageDigest digest = MessageDigest.getInstance(SHA256);
         byte[] hash = digest.digest(rawPassword.toString().getBytes("UTF-8"));
         output = Hex.encodeHexString(hash);
      }
      catch(NoSuchAlgorithmException nsae) {
         nsae.printStackTrace();
      }
      catch(UnsupportedEncodingException uee) {
         uee.printStackTrace();
      }
      return output.toUpperCase();
   }

   @Override
   public boolean matches(CharSequence rawPassword, String encodedPassword) {
      return encodedPassword.equalsIgnoreCase(encode(rawPassword));
   }
}
