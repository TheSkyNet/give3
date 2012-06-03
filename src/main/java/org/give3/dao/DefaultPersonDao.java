package org.give3.dao;


import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.give3.domain.User;
import org.give3.domain.PurchaseOrder;
import org.give3.security.RandomGenerator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 */
public class DefaultPersonDao implements PersonDao {

    @Autowired
    private SessionFactory sessionFactory;

    private HibernateUnProxifier<User> personUnproxifier = new HibernateUnProxifier<User>();
    private PasswordEncoder encoder = new StandardPasswordEncoder();
    private RandomGenerator random = new RandomGenerator();
    
    public DefaultPersonDao() {

    }
    
    @Transactional
    @Override
    public boolean isUserPresent(User user)
    {
        return getUser(user.getUsername()) != null;
    }

    @Transactional 
    @Override
    public void updatePassword(String username, String oldRawPassword, String newRawPassword) {

       User user = getUser(username);

       if( ! encoder.matches(oldRawPassword, user.getPassword()) ) {
          throw new BadCredentialsException("Failed to authenticate the existing password when pdating password for " + username);
       }
       
       user.setPassword(encoder.encode(newRawPassword));
       updatePerson(user);
    }
    
    /**
     * 
     * @param username of the account to reset password
     * 
     * @return new un-encrypted password that is subsequently valid for this account. The original password is destroyed.
     */
    @Transactional 
    @Override
    public String resetPassword(String username) {
       
       User user = getUser(username);
       String newRawPassword = random.generateCode(7);
       user.setPassword(encoder.encode(newRawPassword));
       updatePerson(user);
       
       return  newRawPassword;
    }
    
    /**
     *
     * @param user to create in the database
     */
    @Transactional
    @Override
    public void createNewUser(User user)
    {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        session.flush();
    }
    
    @SuppressWarnings("unchecked")
   @Transactional
    @Override
    public List<PurchaseOrder> getOrders(String username) {
       Session session = sessionFactory.getCurrentSession();
       List<PurchaseOrder> orders = (List<PurchaseOrder>) session.createCriteria(PurchaseOrder.class)
             .add(Restrictions.eq("user", getUser(username)))
             .list();
       HibernateUnProxifier<List<PurchaseOrder>> orderUnproxifier = new HibernateUnProxifier<List<PurchaseOrder>>();
       orders = orderUnproxifier.unproxy(orders);
       return orders;
    }

    @Transactional
    @Override
    public User getUserSerializable(String username) {
       Session session = sessionFactory.getCurrentSession();
       User user = (User) session.createCriteria(User.class)
             .add(Restrictions.eq("username", username))
             .uniqueResult();
       User serializableUser = (user != null) ? personUnproxifier.unproxy(user) : null;
       return serializableUser;
    }
    
    @Transactional
    @Override
    public User getUser(String username) {
       Session session = sessionFactory.getCurrentSession();
       User user = (User) session.createCriteria(User.class).add(Restrictions.eq("username", username)).uniqueResult();
       return user;
    }

    @Transactional
    @Override
    public void deleteUser(User user)
    {
       Session session = sessionFactory.getCurrentSession();
       User attachedObject = getUser(user.getUsername());

       // deletes should be cascaded to authorities table
       session.delete(attachedObject);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<User> getUsers() {

       Session session = sessionFactory.getCurrentSession();
       List<User> users = session.createQuery("select p from Person p").list();

       return users;

    }

    @Transactional
    @Override
    public void updatePerson(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(user);
        session.flush();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
