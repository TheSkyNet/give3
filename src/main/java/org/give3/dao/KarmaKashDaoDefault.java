package org.give3.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.give3.domain.KarmaKash;
import org.give3.security.RandomGenerator;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 */
public class KarmaKashDaoDefault implements KarmaKashDao {

    @Autowired
    private SessionFactory sessionFactory;

    private RandomGenerator random = new RandomGenerator();
    
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
         k.setCode(random.generateCode16());
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
