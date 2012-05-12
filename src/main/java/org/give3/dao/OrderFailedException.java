package org.give3.dao;

public class OrderFailedException extends Exception {

   private static final long serialVersionUID = 1L;

   public OrderFailedException(String message) {
      super(message);
   }
}
