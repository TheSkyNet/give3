package org.give3.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.validation.ConstraintViolationException;

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

@ContextConfiguration(locations = { "/spring/applicationContext-dao.xml", "/spring/applicationContext-persistence-test.xml","/spring/applicationContext-security.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class ItemDaoTest {

   @Autowired
   private ItemDao dao;
   
   @Autowired
   private PersonDao personDao;
   
   private Person user = new Person("j@y.com", "baba327d241746ee0829e7e88117d4d5", 1000000);
   
   @Before
   public void setup() {
      Item item = new Item();
      item.setName("new item");
      item.setDescription("description here");
      item.setValue(8);
      dao.createItem(item);

      user.setBalance(10);
      personDao.createNewUser(user);
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
   public void count() throws Exception {

      assertEquals(1, dao.getCount());

   }
   
   @Test
   @Transactional
   public void placeOrder() throws Exception {
      
      // TODO this doesn't work if you use the "user" defined at the class level
      Person p = new Person("j@y2.com", "baba327d241746ee0829e7e88117d4d5", 10);
      personDao.createNewUser(p);
      
      assertEquals(0, p.getOrders().size());
      
      dao.createOrder(p.getUsername(), dao.getPage(0, 1).iterator().next().getId());
      flushAndClear();

      assertEquals(1, p.getOrders().size());
      assertEquals(2, p.getBalance().intValue());
   }
   
   @Test(expected=OrderFailedException.class)
   @Transactional
   public void placeOrderExceedBalance() throws Exception {

      user.setBalance(1);
      personDao.updatePerson(user);
      dao.createOrder(user.getUsername(), dao.getPage(0, 1).iterator().next().getId());

   }
   
   @Test(expected=OrderFailedException.class)
   @Transactional
   public void placeOrderItemAlreadyPurchased() throws Exception {
      
      Long itemId = dao.getPage(0, 1).iterator().next().getId();

      dao.createOrder(user.getUsername(), itemId);
      dao.createOrder(user.getUsername(), itemId);

   }
   
   @Test
   @Transactional
   public void listUnfulfilledItems() throws Exception {
      
      // there's one item in stock
      List<Item> page = dao.getPage(0, 1);
      assertEquals(1, page.size());
      
      // someone buys it
      dao.createOrder(user.getUsername(), dao.getPage(0, 1).iterator().next().getId());
      flushAndClear();
      
      // the item doesn't appear in listings
      page = dao.getPage(0, 1);
      assertEquals(0, page.size());
      
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
