
package org.one2many.domain;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
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
    private String username = "";

    /**
     * This should already be the md5 hash.
     */
    @Basic
    @NotNull
    @Size(max=32, min=32, message="Password must be a 32-length hex MD5 hash")
    private String password = "";

    @Basic
    private String email = "";

    /**
     * Postgres was having trouble with Boolean (even though it created the schema!)
     * but boolean seems to work fine
     */
    @Basic
    private boolean enabled = true;

    @OneToMany(mappedBy="person", cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<Role> roles = new HashSet<Role>();

    @OneToMany(mappedBy="user")
    private Set<PurchaseOrder> orders = new HashSet<PurchaseOrder>();

    @Basic
    @NotNull
    @NumberFormat(style = Style.NUMBER)
    private Integer balance = new Integer(1);
    
    public Person()
    {
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    /**
     *
     * Password is always stored as a 32-digit hexadecimal md5 hash.
     * If you need to calculate it on the server, code like this will do it:
     *
     * public static String computeHashMD5(String pass) throws NoSuchAlgorithmException {
     *    MessageDigest m = MessageDigest.getInstance("MD5");
     *    byte[] data = pass.getBytes();
     *    m.update(data,0,data.length);
     *    BigInteger i = new BigInteger(1,m.digest());
     *    return String.format("%1$032X", i);
     * }
     *
     * @param password
     */
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
