
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

import org.codehaus.jackson.annotate.JsonBackReference;




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

    @ManyToOne()
    @JoinColumn(name="username")
    @NotNull
    @JsonBackReference
    private Person person;

    @Enumerated(EnumType.STRING)
    @NotNull
    private APPLICATION_ROLE authority = APPLICATION_ROLE.ROLE_USER;

    public Role()
    {
        
    }

    public Role(Person p, APPLICATION_ROLE a)
    {
        person = p;
        authority = a;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
