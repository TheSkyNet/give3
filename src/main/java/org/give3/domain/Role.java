
package org.give3.domain;

import java.io.Serializable;

import javax.persistence.Column;
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

import org.codehaus.jackson.annotate.JsonBackReference;
import org.springframework.security.core.GrantedAuthority;


/**
 * The tablename "authorities" and column names "username/authority" are conventions for spring security.
 * 
 */
@Entity
@Table( name="authorities", uniqueConstraints=@UniqueConstraint(columnNames={"username", "authority"}) )
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

    @ManyToOne()
    @JoinColumn(name="username")
    @NotNull
    @JsonBackReference
    private User person;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name="authority")
    private APPLICATION_ROLE role = APPLICATION_ROLE.ROLE_USER;

    public Role()
    {
        
    }

    public Role(User p, APPLICATION_ROLE a)
    {
        person = p;
        role = a;
    }

    public String getAuthority() {
        return role.toString();
    }

    public APPLICATION_ROLE getRole() {
       return role;
   }
    
    public void setRole(APPLICATION_ROLE authority) {
        this.role = authority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }

}
