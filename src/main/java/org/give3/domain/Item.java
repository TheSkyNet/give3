package org.give3.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@Entity
@Table(name = "item")
public class Item {

   // should be the declared attribute primary key
   public static final String ID = "id";
   
   @Id
   @GeneratedValue(generator = "increment")
   @GenericGenerator(name = "increment", strategy = "increment")
	private Long id = 0L;

   @Basic
	@NotNull
	@Size(min = 1, max = 25)
	private String name = "";

   @Basic
   @NotNull
   @Size(min = 1, max = 250)
   private String description = "";
   
   @ManyToOne
   private PurchaseOrder purchaseOrder = null;
   
   @Basic
	@NotNull
	@NumberFormat(style = Style.NUMBER)
	private Integer value = new Integer(1);

   @Temporal(value=TemporalType.TIMESTAMP)
	private Date postDate = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

   /**
    * @return the value
    */
   public Integer getValue() {
      return value;
   }

   /**
    * @param value the value to set
    */
   public void setValue(Integer value) {
      this.value = value;
   }

   /**
    * @return the postDate
    */
   public Date getPostDate() {
      return postDate;
   }

   /**
    * @param postDate the postDate to set
    */
   public void setPostDate(Date postDate) {
      this.postDate = postDate;
   }

   /**
    * @return the description
    */
   public String getDescription() {
      return description;
   }

   /**
    * @param description the description to set
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * @return the order
    */
   public PurchaseOrder getPurchaseOrder() {
      return purchaseOrder;
   }

   /**
    * @param order the order to set
    */
   public void setPurchaseOrder(PurchaseOrder order) {
      this.purchaseOrder = order;
   }


}