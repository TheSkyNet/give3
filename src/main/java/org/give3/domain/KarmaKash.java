package org.give3.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@Entity
@Table(name = "karmakash")
public class KarmaKash {

   // should be the declared attribute primary key
   public static final String CODE = "code";
   
   @Id
   @Basic
   @NotNull
   @Size(max=16, min=16)
	private String code = "";

   /**
    * use this as the batch id
    */
   @Temporal(value=TemporalType.TIMESTAMP)
   private Date createdOn = new Date();

   @Temporal(value=TemporalType.TIMESTAMP)
   private Date redeemedOn = null;
   
   @ManyToOne()
   @JoinColumn(name="username")
   @JsonBackReference
   private Person redeemedBy;
   
   @Basic
	@NotNull
	@NumberFormat(style = Style.NUMBER)
	private Integer value = new Integer(1);

   public KarmaKash() {
      
   }

   /**
    * @return the createdOn
    */
   public Date getCreatedOn() {
      return createdOn;
   }

   /**
    * @param createdOn the createdOn to set
    */
   public void setCreatedOn(Date createdOn) {
      this.createdOn = createdOn;
   }

   /**
    * @return the redeemedOn
    */
   public Date getRedeemedOn() {
      return redeemedOn;
   }

   /**
    * @param redeemedOn the redeemedOn to set
    */
   public void setRedeemedOn(Date redeemedOn) {
      this.redeemedOn = redeemedOn;
   }

   /**
    * @return the redeemedBy
    */
   public Person getRedeemedBy() {
      return redeemedBy;
   }

   /**
    * @param redeemedBy the redeemedBy to set
    */
   public void setRedeemedBy(Person redeemedBy) {
      this.redeemedBy = redeemedBy;
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
    * @return the id
    */
   public String getCode() {
      return code;
   }

   /**
    * @param id the id to set
    */
   public void setCode(String code) {
      this.code = code;
   }

}