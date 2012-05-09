package org.one2many.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.one2many.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

// ContextConfiguration gets files starting with "/" from the classpath, 
// if there's no leading "/" and it's just a file name, it gets it from the same package

// TODO use transactional workflow tests too

@ContextConfiguration(locations = { "/spring/applicationContext-dao.xml", "/spring/applicationContext-persistence-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class ItemDaoTest {

   @Autowired
   private ItemDao dao;
   
   @Before
   public void setup() {
      Item item = new Item();
      item.setName("new item");
      item.setDescription("description here");
      dao.createItem(item);
      flushAndClear();
   }
   

   /**
    * simulate the end of a session by flushing (writes all the sql) and clearing (remove attached objects)
    * so you can simulate multiple calls to the dao from the service layer
    */
   private void flushAndClear() {
      dao.getSessionFactory().getCurrentSession().flush();
      dao.getSessionFactory().getCurrentSession().clear();
   }

   
   @Test
   @Transactional
   public void update() throws Exception {
      
      Item firstItem = dao.getPage(0, 10).iterator().next();
      firstItem.setDescription("new description");
      dao.updateItem(firstItem);
      flushAndClear();
      
      firstItem = dao.getPage(0, 10).iterator().next();
      assertEquals("new description", firstItem.getDescription());
      
   }



}
