package org.one2many.dao;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

public class HibernateUnProxifier<T> {
   
   @SuppressWarnings("unchecked")
   public <T> T unproxy(T entity) {
      if (entity == null) {
          throw new 
             NullPointerException("Entity passed for initialization is null");
      }

      Hibernate.initialize(entity);
      if (entity instanceof HibernateProxy) {
          entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
                  .getImplementation();
      }
      return entity;
  }
   
}
