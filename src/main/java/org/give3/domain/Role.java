
package org.give3.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table( name="authorities", uniqueConstraints=@UniqueConstraint(columnNames={"user_id", "role"}))
public class Role implements GrantedAuthority, Serializable {

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
    private User user;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private APPLICATION_ROLE role = APPLICATION_ROLE.ROLE_USER;

    public Role() {
        
    }

    public Role(User u) {
       user = u;
    }
    
    public void setRole(APPLICATION_ROLE authority) {
        this.role = authority;
    }

    public APPLICATION_ROLE getRole() {
       return role;
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

   @Override
   public String getAuthority() {
      return role.toString();
   }

}
