package org.give3.dao;

import org.give3.dao.PersonDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

// ContextConfiguration gets files starting with "/" from the classpath, 
// if there's no leading "/" and it's just a file name, it gets it from the same package

// TODO use transactional workflow tests too

@ContextConfiguration(locations = { "/spring/applicationContext-dao.xml", "/spring/applicationContext-persistence-test.xml","/spring/applicationContext-security.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class PersonRepositoryTest {

//   @Autowired
//   private PersonRepository personRepo;

   @Autowired
   private PersonDao personDao;
   
   @Before
   public void setup() {
//      Person p = new Person();
//      p.setEmail("j@y.com");
//      personRepo.save(p);
   }
   
   @Test
   public void find() {
//      List<Person> p = personRepo.findByEmail("j@y.com");
//      Assert.assertNotNull(p);
   }

}
