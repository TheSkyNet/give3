package org.give3.dao;


import java.util.Date;
import java.util.List;

import org.give3.domain.Item;
import org.give3.domain.KarmaKash;
import org.give3.domain.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 */
public class DefaultPersonDao implements PersonDao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     *  this is a detached query
     *  http://docs.jboss.org/hibernate/core/3.3/reference/en/html/querycriteria.html#querycriteria-detachedqueries
     */
    private DetachedCriteria getAll = DetachedCriteria.forClass(Person.class);

    private HibernateUnProxifier<Person> personUnproxifier = new HibernateUnProxifier<Person>();
    
    public DefaultPersonDao() {

    }
    
    @Transactional
    @Override
    public boolean isUserPresent(Person user)
    {
        return getUser(user.getUsername()) != null;
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
    public Person getUserSerializable(String username) {
       Session session = sessionFactory.getCurrentSession();
       Person user = (Person) session.createQuery("select p from Person p where p.username=:name")
                                     .setString("name", username)
                                     .uniqueResult();

       Person serializableUser = null; 
       
       // it's null if there was no entity with the given id
       if (user != null) {
          
          serializableUser = personUnproxifier.unproxy(user);

//          // TODO should be in a copy constructor
//          serializableUser = new Person();
//          serializableUser.setUsername(user.getUsername());
//          serializableUser.setEmail(user.getEmail());
//          serializableUser.setEnabled(user.getEnabled());
//          serializableUser.setPassword(user.getPassword());
          
          
          // TODO roles should probably be eagerly loaded 
          
          // TODO return serializable roles (doesn't work with json serialization)
          
          // this collection is lazy loaded, so if you don't access the
          // collection from this method before returning
          // access of the collection from outside this method will crash because
          // the set can't be loaded from the db
          // (the session will already have been closed)
        //  serializableUser.getRoles().addAll(user.getRoles());
          
          // the collection is an unserializable PersistentSet,
          // so need to fix the serialization thing for hibernate objects

       }
       
       return serializableUser;
    }
    
    @Transactional
    @Override
    public Person getUser(String username) {
       Session session = sessionFactory.getCurrentSession();
       Person user = (Person) session.createQuery("select p from Person p where p.username=:name")
                                     .setString("name", username)
                                     .uniqueResult();
       return user;
    }

    @Transactional
    @Override
    public void deleteUser(Person user)
    {
       Session session = sessionFactory.getCurrentSession();
       Person attachedObject = (Person) session.createQuery("select p from Person p where p.username=:name")
                                     .setString("name", user.getUsername())
                                     .uniqueResult();

       // deletes are cascaded to authorities table
       session.delete(attachedObject);
       session.flush();
    }

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