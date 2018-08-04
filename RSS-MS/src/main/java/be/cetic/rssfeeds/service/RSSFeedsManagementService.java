/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cetic.rssfeeds.service;

import be.cetic.rssfeeds.domain.CamelRoute;
import be.cetic.rssfeeds.entities.RSSFeed;
import java.util.List;

/**
 *
 * @author cetic
 */
public interface RSSFeedsManagementService {

    public List<RSSFeed> getAllRSSFeeds();

    public void saveRSSFeed(RSSFeed rssFeed);

    public void removeRSSFeedById(String id);
    
    public List<CamelRoute> getRunningRoutes();

    public void stopRouteByID(String id);

    public void startAllRssRoutes();

    public void stopAllRssRoutes();

    public void startRouteByID(String id);
}
