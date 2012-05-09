package org.one2many.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.one2many.dao.KarmaKashDao;
import org.one2many.domain.KarmaKash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


@ContextConfiguration(locations = { "/spring/applicationContext-dao.xml", "/spring/applicationContext-persistence-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class KarmaKashDaoTest {

   @Autowired
   private KarmaKashDao dao;


   
   @Before
   public void setup() {
      

   }
   
   @Test
   public void generateBatch() throws Exception {
      //dao.generateBatch(10);
      List<KarmaKash> list = dao.generateBatch(10);
      Assert.assertEquals(16, list.get(0).getCode().length());
   }



}
