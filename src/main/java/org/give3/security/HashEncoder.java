package org.give3.security;

public interface HashEncoder {
   public static String UTF8 = "UTF-8";
   String toHash(String input);
}
