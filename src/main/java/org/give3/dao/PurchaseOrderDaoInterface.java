package org.give3.dao;

import java.util.List;

import org.give3.domain.PurchaseOrder;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public interface PurchaseOrderDaoInterface {

   /**
    * @return paged set of unfulfilled orders.
    */
   @Transactional
   @SuppressWarnings("unchecked")
   public List<PurchaseOrder> getPage(int start, int pageSize);

   @Transactional
   public void fulfillOrder(Long orderId);

   public SessionFactory getSessionFactory();

   public void setSessionFactory(SessionFactory sessionFactory);

}