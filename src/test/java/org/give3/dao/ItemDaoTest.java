package org.give3.dao;

import static org.junit.Assert.assertEquals;

import org.give3.domain.Item;
import org.give3.domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
   
   @Autowired
   private PersonDao personDao;
   
   @Before
   public void setup() {
      Item item = new Item();
      item.setName("new item");
      item.setDescription("description here");
      item.setValue(8);
      dao.createItem(item);
      personDao.createNewUser(new Person("jay", "baba327d241746ee0829e7e88117d4d5", 1000000));
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
   public void placeOrder() throws Exception {
      
      Item firstItem = dao.getPage(0, 1).iterator().next();
      Person user = personDao.getUser("jay");
      assertEquals(0, user.getOrders().size());
      
      dao.createOrder(user, firstItem);
      flushAndClear();
      
      user = personDao.getUser("jay");
      assertEquals(1, user.getOrders().size());
      
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
