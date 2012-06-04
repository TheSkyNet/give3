
package org.give3.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
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


    @Id
    @Basic
    @NotNull
    @Size(max=50, min=1, message="Name must be between 1 and 50 characters")
    @Email
    private String username = "";

    /**
     * This should be stored as a salted hash.
     */
    @Basic
    @NotNull
    private String password = "";

    @Basic
    private boolean enabled = true;

    @OneToMany(mappedBy="person", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<Role> roles = new HashSet<Role>();

    @OneToMany(mappedBy="user")
    private Set<PurchaseOrder> orders = new HashSet<PurchaseOrder>();

    @Basic
    @NotNull
    @Min(value=0)
    @NumberFormat(style = Style.NUMBER)
    private Integer balance = new Integer(0);
    
    public Person()  {
        
    }
    
    public Person(String uname, String pword, int newbalance) {
        username = uname;
        password=pword;
        balance = newbalance;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
    * @return the orders
    */
   public Set<PurchaseOrder> getOrders() {
      return orders;
   }

   /**
    * @param orders the orders to set
    */
   public void setOrders(Set<PurchaseOrder> orders) {
      this.orders = orders;
   }
}
