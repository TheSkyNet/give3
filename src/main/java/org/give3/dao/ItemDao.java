package org.give3.dao;

import java.util.List;

import org.give3.domain.Item;
import org.give3.domain.Person;
import org.hibernate.SessionFactory;

public interface ItemDao {
   

   public Item getById(Long id);
   
   public void createItem(Item account);

   public SessionFactory getSessionFactory();

   public List<Item> getPage(int start, int pageSize);
   
   void updateItem(Item item);

   public void createOrder(Person user, Item firstItem);
}
