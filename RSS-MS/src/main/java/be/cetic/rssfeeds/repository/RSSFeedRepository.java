 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cetic.rssfeeds.repository;

import be.cetic.rssfeeds.entities.RSSFeed;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author cetic
 */
public interface RSSFeedRepository extends CrudRepository<RSSFeed, Long>{
    
     /**
     * @param isActive
     * @return All the inactive URIs
     */
    List<RSSFeed> findByActive(boolean isActive);
    
}
