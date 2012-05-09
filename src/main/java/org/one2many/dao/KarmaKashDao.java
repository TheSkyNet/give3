
package org.one2many.dao;

import java.util.List;

import org.one2many.domain.KarmaKash;

/**
 *
 * @author jay
 */
public interface KarmaKashDao {

    KarmaKash getKarmaKash(String code);
    List<KarmaKash> generateBatch(int size);
    void update(KarmaKash k);
   void saveBatch(List<KarmaKash> list);

}
