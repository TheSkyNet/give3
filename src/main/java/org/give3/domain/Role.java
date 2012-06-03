
package org.give3.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;




/**
 * The tablename "authorities" and column names "username/authority" are conventions for spring security.
 * 
 */
// TODO use GrantedAuthority interface
@Entity
@Table( name="authorities", uniqueConstraints=@UniqueConstraint(columnNames={"username", "authority"}) )
public class Role implements Serializable {

    public enum APPLICATION_ROLE {
        ROLE_USER,
        ROLE_ADMIN
    };

    @Id
    @NotNull
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id = -1;

    // TODO if this doesn't work, see this https://hibernate.onjira.com/browse/HHH-3824
    @OneToOne()
    @NotNull
    @JoinColumn(name="username", referencedColumnName="username")
    private Person user;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private APPLICATION_ROLE authority = APPLICATION_ROLE.ROLE_USER;

    public Role() {
        
    }

    public APPLICATION_ROLE getAuthority() {
        return authority;
    }

    public void setAuthority(APPLICATION_ROLE authority) {
        this.authority = authority;
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
   public Person getUser() {
      return user;
   }

   /**
    * @param user the user to set
    */
   public void setUser(Person user) {
      this.user = user;
   }



}
