package org.one2many.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.one2many.domain.KarmaKash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 */
public class KarmaKashDaoDefault implements KarmaKashDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Random random = new Random();
    
    /**
     *  this is a detached query
     *  http://docs.jboss.org/hibernate/core/3.3/reference/en/html/querycriteria.html#querycriteria-detachedqueries
     */
    private DetachedCriteria getAll = DetachedCriteria.forClass(KarmaKash.class);
    
    public KarmaKashDaoDefault() {

    }

    @Transactional
   @Override
   public KarmaKash getKarmaKash(String code) {
      Session session = sessionFactory.getCurrentSession();
      Criteria byId = session.createCriteria(KarmaKash.class).add(Restrictions.eq(KarmaKash.CODE, code));
      KarmaKash k = (KarmaKash) byId.uniqueResult();
      return k;
   }
   
   /**
    * 
    * @return 16-digit random number as a string
    */
   public String generateCode() {
      StringBuffer buffer = new StringBuffer();
      for(int i=0; i <16; i++) {
         buffer.append(Math.abs(random.nextInt() % 10));
      }
      return buffer.toString();
   }

   @Transactional
   @Override
   public void saveBatch(List<KarmaKash> list) {
      Session session = sessionFactory.getCurrentSession();
      for(KarmaKash k : list) {
         session.save(k);
      }
      session.flush();
   }
   
   @Override
   public List<KarmaKash> generateBatch(int size) {
      Date createdOn = new Date();
      List<KarmaKash> list = new ArrayList<KarmaKash>(size);
      for(int i=0; i < size; i++) {
         KarmaKash k = new KarmaKash();
         k.setCode(generateCode());
         k.setValue(1);
         k.setCreatedOn(createdOn);
         list.add(k);
      } 
      return list;
   }

   @Transactional
   @Override
   public void update(KarmaKash k) {
       Session session = sessionFactory.getCurrentSession();
       session.merge(k);
       session.flush();
   }

   public void setSessionFactory(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
  }
   
}
