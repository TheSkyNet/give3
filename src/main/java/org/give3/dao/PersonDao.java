
package org.give3.dao;

import java.util.List;
import java.util.Set;

import org.give3.domain.User;
import org.give3.domain.PurchaseOrder;

/**
 *
 * @author jay
 */
public interface PersonDao {

    /**
     *
     * @param user to create in the database
     */
    void createNewUser(User user);

    boolean isUserPresent(User user);

    User getUser(String username);
    
    User getUserSerializable(String username);

    void deleteUser(User user);

    List<User> getUsers();

    void updatePerson(User user);

   Set<PurchaseOrder> getOrders(String username);

   void updatePassword(String username, String oldRawPassword, String newRawPassword);

   String resetPassword(String username);

}
