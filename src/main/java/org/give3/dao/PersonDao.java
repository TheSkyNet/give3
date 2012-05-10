
package org.give3.dao;

import java.util.List;

import org.give3.domain.KarmaKash;
import org.give3.domain.Person;

/**
 *
 * @author jay
 */
public interface PersonDao {

    /**
     *
     * @param user to create in the database
     */
    void createNewUser(Person user);

    boolean isUserPresent(Person user);

    Person getUser(String username);
    
    Person getUserSerializable(String username);

    void deleteUser(Person user);

    List<Person> getUsers();

    void updatePerson(Person user);

}
