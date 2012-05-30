package org.give3.dao;


import java.util.List;
import java.util.Set;

import org.give3.domain.Person;
import org.give3.domain.PurchaseOrder;
import org.give3.security.HashEncoderMD5;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 */
public class DefaultPersonDao implements PersonDao {

    @Autowired
    private SessionFactory sessionFactory;

    private HibernateUnProxifier<Person> personUnproxifier = new HibernateUnProxifier<Person>();
    
    public DefaultPersonDao() {

    }
    
    @Transactional
    @Override
    public boolean isUserPresent(Person user)
    {
        return getUser(user.getUsername()) != null;
    }

    @Transactional 
    @Override
    public void updatePassword(String username, String oldRawPassword, String newRawPassword) {
       
       PasswordEncoder encoder = new HashEncoderMD5();
       Person user = getUser(username);

       if( ! encoder.matches(oldRawPassword, user.getPassword()) ) {
          throw new BadCredentialsException("Failed to authenticate the existing password when pdating password for user " + username);
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
       
       PasswordEncoder encoder = new HashEncoderMD5();
       Person user = getUser(username);
       // TODO extract random string generation to security package
       
       String newRawPassword = new KarmaKashDaoDefault().generateCode(7);
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
    public void createNewUser(Person user)
    {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        session.flush();
    }
    
    @Transactional
    @Override
    public Set<PurchaseOrder> getOrders(String username) {
       Person user = getUser(username);
       
       HibernateUnProxifier<List<PurchaseOrder>> orderUnproxifier = new HibernateUnProxifier<List<PurchaseOrder>>();
       Set<PurchaseOrder> orders = orderUnproxifier.unproxy(user.getOrders());
       return orders;
    }

    // TODO return serializable roles (doesn't work with json serialization)

    @Transactional
    @Override
    public Person getUserSerializable(String username) {

       Person user = getUser(username);
       Person serializableUser = (user != null) ? personUnproxifier.unproxy(user) : null;
       return serializableUser;
    }
    
    @Transactional
    @Override
    public Person getUser(String username) {
       Session session = sessionFactory.getCurrentSession();
       Person user = (Person) session.createCriteria(Person.class).add(Restrictions.eq("username", username)).uniqueResult();
       return user;
    }

    @Transactional
    @Override
    public void deleteUser(Person user)
    {
       Session session = sessionFactory.getCurrentSession();
       Person attachedObject = getUser(user.getUsername());

       // deletes should be cascaded to authorities table
       session.delete(attachedObject);
       session.flush();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<Person> getUsers() {

       Session session = sessionFactory.getCurrentSession();
       List<Person> users = session.createQuery("select p from Person p").list();

       return users;

    }

    @Transactional
    @Override
    public void updatePerson(Person user) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(user);
        session.flush();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
