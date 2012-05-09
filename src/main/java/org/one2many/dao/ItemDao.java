package org.one2many.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.one2many.domain.Item;

public interface ItemDao {
   

   public Item getById(Long id);
   
   public void createItem(Item account);

   public SessionFactory getSessionFactory();

   public List<Item> getPage(int start, int pageSize);
   
   void updateItem(Item item);
}
