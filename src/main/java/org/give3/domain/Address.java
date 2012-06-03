package org.give3.domain;

import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

public class Address {

   @Id
   @GeneratedValue(generator = "increment")
   @GenericGenerator(name = "increment", strategy = "increment")
   private Long id = -1L;
   
   @Basic
   @NotNull
   private String street1;
   
   @Basic
   private String street2;
   
   @Basic
   private String city;
   
   @Basic
   private String state;
   
   @Basic
   private String zipCode;
   
   @OneToOne
   private User person;
   
   public Address() {
      
   }
}
