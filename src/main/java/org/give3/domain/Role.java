
package org.give3.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

// TODO implement GrantedAuthority
@Entity
@Table( name="authorities", uniqueConstraints=@UniqueConstraint(columnNames={"username", "authority"}))
public class Role implements  Serializable {

   private static final long serialVersionUID = 1L;

   public enum APPLICATION_ROLE {
        ROLE_USER,
        ROLE_ADMIN
    };

    @Id
    @NotNull
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id = -1;

    @ManyToOne
    @NotNull
    @JoinColumn(name="username", referencedColumnName="username")
    private User user;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private APPLICATION_ROLE authority = APPLICATION_ROLE.ROLE_USER;

    public Role() {
        
    }

    public void setAuthority(APPLICATION_ROLE authority) {
        this.authority = authority;
    }

    public APPLICATION_ROLE getAuthority() {
       return authority;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   /**
    * @return the user
    */
   public User getUser() {
      return user;
   }

   /**
    * @param user the user to set
    */
   public void setUser(User user) {
      this.user = user;
   }

}
