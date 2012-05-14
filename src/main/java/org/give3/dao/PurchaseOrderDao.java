package org.give3.dao;

import java.util.List;

import org.give3.domain.PurchaseOrder;
import org.give3.domain.PurchaseOrder.STATUS;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;


public class PurchaseOrderDao {
   
   private SessionFactory sessionFactory;
   
   /**
    *  this is a detached query
    *  http://docs.jboss.org/hibernate/core/3.3/reference/en/html/querycriteria.html#querycriteria-detachedqueries
    */
   private DetachedCriteria getAll = DetachedCriteria.forClass(PurchaseOrder.class);

   private HibernateUnProxifier<PurchaseOrder> orderListUnproxifier = new HibernateUnProxifier<PurchaseOrder>();

   /**
    * @return paged set of unfulfilled orders.
    */
   @Transactional
   @SuppressWarnings("unchecked")
   public List<PurchaseOrder> getPage(int start, int pageSize) {
      Session session = sessionFactory.getCurrentSession();
      
      List<PurchaseOrder> orders = (List<PurchaseOrder>) getAll.getExecutableCriteria(session)
                                                .add(Restrictions.eq("status", STATUS.UNFULFILLED))
                                                .setFirstResult(start)
                                                .setMaxResults(pageSize)
                                                .list();
      return orders;
   }
   
   @Transactional
   public void fulfillOrder(Long orderId) {
      Session session = sessionFactory.getCurrentSession();
      
      PurchaseOrder order = (PurchaseOrder)getAll.getExecutableCriteria(session)
                           .add(Restrictions.eq("id", orderId))
                           .uniqueResult();
      
      order.setStatus(STATUS.FULFILLED);
      session.update(order);

   }
   
   
   public SessionFactory getSessionFactory() {
      return sessionFactory;
   }

   public void setSessionFactory(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }
   
}
