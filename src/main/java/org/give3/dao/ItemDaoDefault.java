package org.give3.dao;

import java.util.Collections;
import java.util.List;

import org.give3.domain.Item;
import org.give3.domain.User;
import org.give3.domain.PurchaseOrder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;


public class ItemDaoDefault implements ItemDao {
   
   private SessionFactory sessionFactory;
   
   /**
    *  this is a detached query
    *  http://docs.jboss.org/hibernate/core/3.3/reference/en/html/querycriteria.html#querycriteria-detachedqueries
    *  
    *  any methods you call on it are maintained for the next call, so if you call .add(Restrictions...) on it
    *  inside each method call, the restrictions will be repeatedly (and uselessly) added each time.
    */
   private DetachedCriteria getAvailable = DetachedCriteria.forClass(Item.class).add(Restrictions.isNull("purchaseOrder"));

   private HibernateUnProxifier<Item> accountUnproxifier = new HibernateUnProxifier<Item>();

   /**
    * @return paged set of unpurchased items.
    */
   @Override
   @Transactional
   @SuppressWarnings("unchecked")
   public List<Item> getPage(int start, int pageSize) {
      Session session = sessionFactory.getCurrentSession();
      List<Item> items = (List<Item>) getAvailable.getExecutableCriteria(session)
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
   }
   
   @Transactional
   @Override
   public void updateItem(Item item) {
       Session session = sessionFactory.getCurrentSession();
       session.merge(item);
   }
   
   @Transactional
   @Override
   public PurchaseOrder createOrder(String userId, Long itemId) throws OrderFailedException {
      
      // TODO handle if item doesn't exist
      
      // TODO handle race conditions, would you use locks?
      
      // TODO re-use existing findBy code, handle session closing conditions
      
      Session session = sessionFactory.getCurrentSession();
      
      Item item = (Item) session.createCriteria(Item.class)
                                 .add(Restrictions.eq(Item.ID, itemId))
                                 .uniqueResult();
      
      User user = (User) session.createQuery("select p from User p where p.username=:name")
                                    .setString("name", userId)
                                    .uniqueResult();

      // TODO would the validation API accomplish either of these checks?
      
      if(item.getPurchaseOrder() != null) {
         throw new OrderFailedException("Item was already purchased");
      }
      
      if(user.getBalance() < item.getValue()) {
         throw new OrderFailedException("Insufficient funds");
      }

      PurchaseOrder order = new PurchaseOrder();
      order.setItems(Collections.singleton(item));
      order.setUser(user);
      user.setBalance(user.getBalance() - item.getValue());

      item.setPurchaseOrder(order);
      session.save(order);
      session.merge(item);
      session.merge(user);
      
      return order;
   }
   
   /**
    * http://stackoverflow.com/questions/2160259/count-in-hibernate-criteria
    */
   @Override
   @Transactional
   public long getCount() {
      Session session = sessionFactory.getCurrentSession();
      return (Long)session.createCriteria(Item.class)
                              .add(Restrictions.isNull("purchaseOrder"))
                              .setProjection(Projections.rowCount())
                              .uniqueResult();
   }
   
   @Override
   public SessionFactory getSessionFactory() {
      return sessionFactory;
   }

   public void setSessionFactory(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }
   
}
