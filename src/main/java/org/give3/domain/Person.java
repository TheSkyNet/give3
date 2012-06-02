
package org.give3.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;


/**
 *
 * The tablename "users" and column names "username/password/enabled" are conventions for spring security.
 *
 * @author Jason Young
 */
@Entity
@Table( name="users", uniqueConstraints={@UniqueConstraint(columnNames={"username"})})
public class Person implements Serializable {

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator = "increment")
   @GenericGenerator(name = "increment", strategy = "increment")
   private Long id = -1L;
    
    @Basic
    @NotNull
    @Email
    @Size(min=1, message="Name must be at least 1 character")
    private String username = null;

    @Temporal(value=TemporalType.TIMESTAMP)
    @NotNull
    private Date registration = new Date();
    
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date lastLogin = null;
    
    /**
     * This should be encrypted before being stored in the database.
     */
    @Basic
    @NotNull
    private String password = null;

    @Basic
    private boolean enabled = false;

    @Basic
    @NotNull
    @Min(value=0)
    @NumberFormat(style = Style.NUMBER)
    private Integer balance = new Integer(0);
    
    @Basic
    private String firstName = "";
    
    @Basic
    private String lastName = "";
    
    public Person()  {
        
    }
    
    public Person(long key, String uname, String pword) {
        username = uname;
        password=pword;
    //    id = key;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

   /**
    * @return the balance
    */
   public Integer getBalance() {
      return balance;
   }

   /**
    * @param balance the balance to set
    */
   public void setBalance(Integer balance) {
      this.balance = balance;
   }

   /**
    * @param enabled the enabled to set
    */
   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   /**
    * @return the registration
    */
   public Date getRegistration() {
      return registration;
   }

   /**
    * @param registration the registration to set
    */
   public void setRegistration(Date registration) {
      this.registration = registration;
   }

   /**
    * @return the lastLogin
    */
   public Date getLastLogin() {
      return lastLogin;
   }

   /**
    * @param lastLogin the lastLogin to set
    */
   public void setLastLogin(Date lastLogin) {
      this.lastLogin = lastLogin;
   }

   /**
    * @return the firstName
    */
   public String getFirstName() {
      return firstName;
   }

   /**
    * @param firstName the firstName to set
    */
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   /**
    * @return the lastName
    */
   public String getLastName() {
      return lastName;
   }

   /**
    * @param lastName the lastName to set
    */
   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

}
