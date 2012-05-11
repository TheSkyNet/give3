package org.give3.dao;

import java.util.List;

import org.give3.domain.Item;
import org.give3.domain.Person;
import org.give3.domain.PurchaseOrder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;


public class ItemDaoDefault implements ItemDao {
   
   private SessionFactory sessionFactory;
   
   /**
    *  this is a detached query
    *  http://docs.jboss.org/hibernate/core/3.3/reference/en/html/querycriteria.html#querycriteria-detachedqueries
    */
   private DetachedCriteria getAll = DetachedCriteria.forClass(Item.class);

   private HibernateUnProxifier<Item> accountUnproxifier = new HibernateUnProxifier<Item>();

   @Override
   @Transactional
   @SuppressWarnings("unchecked")
   public List<Item> getPage(int start, int pageSize) {
      Session session = sessionFactory.getCurrentSession();
      List<Item> items = (List<Item>) getAll.getExecutableCriteria(session)
                                                .setFirstResult(start)
                                                .setMaxResults(pageSize)
                                                .list();
      return items;
   }
   
   @Override
   @Transactional
   public Item getById(Long id) {
      
      Session session = sessionFactory.getCurrentSession();
      Criteria byId = session.createCriteria(Item.class).add(Restrictions.eq(Item.ID, id));
      Item item = (Item) byId.uniqueResult();

      item = accountUnproxifier.unproxy(item);
      return item;
   }
   
   @Override
   @Transactional
   public void createItem(Item item) {
      Session session = sessionFactory.getCurrentSession();
      session.save(item);
      session.flush();
   }
   
   @Transactional
   @Override
   public void updateItem(Item item) {
       Session session = sessionFactory.getCurrentSession();
       session.merge(item);
       session.flush();
   }
   
   @Transactional
   @Override
   public void createOrder(Person user, Item item) {
      Session session = sessionFactory.getCurrentSession();
      PurchaseOrder order = new PurchaseOrder();
      order.setItem(item);
      order.setUser(user);
      session.save(order);
      session.flush();
   }
   
   @Override
   public SessionFactory getSessionFactory() {
      return sessionFactory;
   }

   public void setSessionFactory(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }
   
}
