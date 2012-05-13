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
      Person user = new Person("jay", "baba327d241746ee0829e7e88117d4d5", 1000000);
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
   public void placeOrder() throws Exception {
      
      Person user = personDao.getUser("jay");
      assertEquals(0, user.getOrders().size());
      
      dao.createOrder("jay", dao.getPage(0, 1).iterator().next().getId());
      flushAndClear();
      
      user = personDao.getUser("jay");
      assertEquals(1, user.getOrders().size());
      assertEquals(2, user.getBalance().intValue());
   }
   
   @Test(expected=OrderFailedException.class)
   @Transactional
   public void placeOrderExceedBalance() throws Exception {
      
      Person user = personDao.getUser("jay");
      user.setBalance(1);
      personDao.updatePerson(user);
      dao.createOrder("jay", dao.getPage(0, 1).iterator().next().getId());

   }
   
   @Test(expected=OrderFailedException.class)
   @Transactional
   public void placeOrderItemAlreadyPurchased() throws Exception {
      
      Long itemId = dao.getPage(0, 1).iterator().next().getId();
      Person user = personDao.getUser("jay");

      dao.createOrder("jay", itemId);
      dao.createOrder("jay",itemId);

   }
   
   @Test
   @Transactional
   public void listUnfulfilledItems() throws Exception {
      
      // there's one item in stock
      List<Item> page = dao.getPage(0, 1);
      assertEquals(1, page.size());
      
      // someone buys it
      dao.createOrder("jay", dao.getPage(0, 1).iterator().next().getId());
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
