package org.give3.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.give3.domain.Item;
import org.give3.domain.Person;
import org.give3.domain.PurchaseOrder;
import org.give3.domain.Role;
import org.give3.domain.Role.APPLICATION_ROLE;
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
public class PurchaseOrderDaoTest {

   
   @Autowired
   private PurchaseOrderDao purchaseOrderDao;
   
   @Autowired
   private ItemDao itemDao;
   
   @Autowired
   private PersonDao personDao;
   
   @Before
   public void setup() {
      
      Item item = new Item();
      item.setName("new item");
      item.setDescription("description here");
      item.setValue(8);
      itemDao.createItem(item);
      
      item = new Item();
      item.setName("new item 2");
      item.setDescription("description here");
      item.setValue(10);
      itemDao.createItem(item);
      
      Person user = new Person(1L, "j@y.com", "baba327d241746ee0829e7e88117d4d5");
      user.setBalance(12);
//      user.getRoles().add(new Role(user, APPLICATION_ROLE.ROLE_ADMIN));
//      user.getRoles().add(new Role(user, APPLICATION_ROLE.ROLE_USER));
      personDao.createNewUser(user);
      
      user = new Person(2L, "c@give3.org", "baba327d241746ee0829e7e88117d4d5");
      user.setBalance(12);
      personDao.createNewUser(user);
      
      flushAndClear();
   }
   
   /**
    * simulate the end of a session by flushing (writes all the sql) and clearing (remove attached objects)
    * so you can simulate multiple calls to the dao from the service layer
    */
   private void flushAndClear() {
      purchaseOrderDao.getSessionFactory().getCurrentSession().flush();
      purchaseOrderDao.getSessionFactory().getCurrentSession().clear();
   }

   @Test
   @Transactional
   public void listUnfulfilledOrders() throws Exception {
      
      // there's one item in stock
      List<PurchaseOrder> page = purchaseOrderDao.getPage(0, 1);
      assertEquals(0, page.size());
      assertEquals(2, itemDao.getPage(0,  10).size());
      
      // someone buys it
      itemDao.createOrder("j@y.com", itemDao.getPage(0, 1).iterator().next().getId());
      flushAndClear();
      
      // there is now a purchase order you can fulfill
      page = purchaseOrderDao.getPage(0, 1);
      assertEquals(1, page.size());
      assertEquals(1, page.get(0).getItems().size());
      assertEquals(1, itemDao.getPage(0,  10).size());
      
   }

   @Test
   @Transactional
   public void fulfillOrder() throws Exception {
      List<PurchaseOrder> page;
      
      // there's one item in stock
      itemDao.createOrder("j@y.com", itemDao.getPage(0, 1).iterator().next().getId());
      page = purchaseOrderDao.getPage(0, 1);
      assertEquals(1, page.size());

      // there is now a purchase order you can fulfill
      long orderId = purchaseOrderDao.getPage(0, 1).iterator().next().getId();
      purchaseOrderDao.fulfillOrder(orderId);
      
      page = purchaseOrderDao.getPage(0, 1);
      assertEquals(0, page.size());
   }


}
