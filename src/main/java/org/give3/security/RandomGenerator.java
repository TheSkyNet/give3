package org.give3.security;

import java.util.Random;

public class RandomGenerator {
   
   private Random random = new Random();
   
  /**
   * 
   * @return 16-digit random number as a string
   */
  public String generateCode16() {
     return generateCode(16);
  }

  /**
   * 
   * @return n-digit random number as a string
   */
  public String generateCode(int numDigits) {
     StringBuffer buffer = new StringBuffer();
     for(int i=0; i < numDigits; i++) {
        buffer.append(random.nextInt(10));
     }
     return buffer.toString();
  }
}
