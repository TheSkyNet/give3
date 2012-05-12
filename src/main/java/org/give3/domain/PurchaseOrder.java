package org.give3.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "purchaseorder")
public class PurchaseOrder {

   public enum STATUS {
      UNFULFILLED,
      FULFILLED
  };

   // should be the declared attribute primary key
   public static final String ID = "id";
   
   @Id
   @GeneratedValue(generator = "increment")
   @GenericGenerator(name = "increment", strategy = "increment")
	private Long id = 0L;

   @Basic
	@NotNull
	@OneToMany(mappedBy="purchaseOrder", targetEntity=Item.class)
	private Set<Item> items = null;

   @ManyToOne()
   @JoinColumn(name="username")
   @NotNull
   @JsonBackReference
   private Person user = null;
   
   @Enumerated(EnumType.STRING)
   @NotNull
   private STATUS status = STATUS.UNFULFILLED;
   
   @Temporal(value=TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
   @NotNull
	private Date date = new Date();

   public PurchaseOrder() {
      
   }
   
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

   /**
    * @return the item
    */
   public Set<Item> getItems() {
      return items;
   }

   /**
    * @param item the item to set
    */
   public void setItems(Set<Item> item) {
      this.items = item;
   }

   /**
    * @return the user
    */
   public Person getUser() {
      return user;
   }

   /**
    * @param user the user to set
    */
   public void setUser(Person user) {
      this.user = user;
   }

   /**
    * @return the status
    */
   public STATUS getStatus() {
      return status;
   }

   /**
    * @param status the status to set
    */
   public void setStatus(STATUS status) {
      this.status = status;
   }

   /**
    * @return the date
    */
   public Date getDate() {
      return date;
   }

   /**
    * @param date the date to set
    */
   public void setDate(Date date) {
      this.date = date;
   }


}