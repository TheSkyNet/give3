package org.give3.dao;

import java.util.List;
import java.util.Set;

import org.give3.domain.User;
import org.give3.domain.PurchaseOrder;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


public class DefaultPersonDao implements PersonDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private HibernateUnProxifier<User> personUnproxifier = new HibernateUnProxifier<User>();
    
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

       if( ! passwordEncoder.matches(oldRawPassword, user.getPassword()) ) {
          throw new BadCredentialsException("Failed to authenticate the existing password when updating password for user " + username);
       }
       
       user.setPassword(passwordEncoder.encode(newRawPassword));
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
       // TODO extract random string generation to security package
       
       String newRawPassword = new KarmaKashDaoDefault().generateCode(7);
       user.setPassword(passwordEncoder.encode(newRawPassword));
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
    
    @Transactional
    @Override
    public Set<PurchaseOrder> getOrders(String username) {
       User user = getUser(username);
       HibernateUnProxifier<List<PurchaseOrder>> orderUnproxifier = new HibernateUnProxifier<List<PurchaseOrder>>();
       Set<PurchaseOrder> orders = orderUnproxifier.unproxy(user.getOrders());
       return orders;
    }

    @Transactional
    @Override
    public User getUserSerializable(String username) {
       Session session = sessionFactory.getCurrentSession();
       User user = (User) session.createCriteria(User.class)
             .add(Restrictions.eq("username", username))
             .setFetchMode("roles", FetchMode.JOIN)
             .setFetchMode("orders", FetchMode.JOIN)
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
       session.flush();
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

   /**
    * @return the passwordEncoder
    */
   public PasswordEncoder getPasswordEncoder() {
      return passwordEncoder;
   }

   /**
    * @param passwordEncoder the passwordEncoder to set
    */
   public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
      this.passwordEncoder = passwordEncoder;
   }
}
