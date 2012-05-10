
package org.give3.dao;

import java.util.List;

import org.give3.domain.KarmaKash;

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
