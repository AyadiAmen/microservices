/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cetic.rssfeeds.service.impl;

import be.cetic.rssfeeds.domain.CamelRoute;
import be.cetic.rssfeeds.entities.Parameter;
import be.cetic.rssfeeds.entities.RSSFeed;
import be.cetic.rssfeeds.repository.ParameterRepository;
import be.cetic.rssfeeds.repository.RSSFeedRepository;
import be.cetic.rssfeeds.route.EventRouter;
import be.cetic.rssfeeds.service.RSSFeedsManagementService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cetic
 */
@Service
public class RSSFeedsManagementServiceImpl implements RSSFeedsManagementService {

    private RSSFeedRepository rSSFeedRepository;
    private ParameterRepository parameterRepository;

    @Autowired
    public RSSFeedsManagementServiceImpl(RSSFeedRepository rSSFeedRepository, ParameterRepository parameterRepository) {
        this.rSSFeedRepository = rSSFeedRepository;
        this.parameterRepository = parameterRepository;
    }

    @Transactional
    @Override
    public void saveRSSFeed(RSSFeed rssFeed) {
        // stores the rss feed 
        if (canSave(rssFeed)) {
            rssFeed.getParameters().forEach(parameterRepository::save);
            rssFeed = rSSFeedRepository.save(rssFeed);
            startEventRoute(rssFeed);
            
        } else {
            System.out.println(String.format("The rss %s can't be saved.", rssFeed));

        }
    }

    @Override
    public List<RSSFeed> getAllRSSFeeds() {
        final List<RSSFeed> result = new ArrayList<>();
        for (RSSFeed rSSFeed : rSSFeedRepository.findAll()) {
            result.add(rSSFeed);
            
        }
//        result.add(buildRSSFeed());
        return result;
    }

    private RSSFeed buildRSSFeed() {
        final RSSFeed rssFeed = new RSSFeed("http://trafiroutes.wallonie.be/trafiroutes/Evenements_FR.rss");
        List<Parameter> list = new ArrayList();
        Parameter parameter = new Parameter();
        parameter.setName("consumer.delay");
        parameter.setType("long");
        list.add(parameter);
        parameter = new Parameter();
        parameter.setName("splitEntries");
        parameter.setType("boolean");
        list.add(parameter);
        rssFeed.setParameters(list);
        return rssFeed;
    }

    /**
     * This method check if the rssFeed new entry can be saved or note in the
     * database. It checks the nullability of the bean, of its URI and the
     * duplication of the URI on the exsiting entities.
     *
     * @param rssFeed
     * @return false if the rssFeed given in parameter can be saved.
     */
    private boolean canSave(RSSFeed rssFeed) {
        if (rssFeed == null) {
            return false;
        }
        if (rssFeed.getURI() == null || rssFeed.getURI().isEmpty()) {
            return false;
        }
        for (RSSFeed rSSFeed : rSSFeedRepository.findAll()) {
            if (rSSFeed.getURI().equals(rssFeed.getURI())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void removeRSSFeedById(String id) {
        try {

            rSSFeedRepository.deleteById(Long.valueOf(id));
        } catch (Exception ex) {
            System.out.println("Can't remove the RSSFeed By Id" + id);
        }
    }

    @Autowired
    CamelContext camelContext;

    @Override
    public List<CamelRoute> getRunningRoutes() {
        return camelContext.getRoutes().stream()
                .filter(route -> route.getId().startsWith("Route"))
                .map(route -> new CamelRoute(route.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void stopRouteByID(String id) {
        try {
            camelContext.stopRoute(id);
        } catch (Exception ex) {
            Logger.getLogger(RSSFeedsManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void startAllRssRoutes() {
        for (RSSFeed rssFeed : getAllRSSFeeds()) {
            startEventRoute(rssFeed);
        }
    }

    @Override
    public void stopAllRssRoutes() {
        
        for (CamelRoute runningRoute : getRunningRoutes()) {
            stopRouteByID(runningRoute.getId());
        }
    }

    @Override
    public void startRouteByID(String id) {
        Optional<RSSFeed> rssFeed = rSSFeedRepository.findById(Long.parseLong(id));
        if (rssFeed.isPresent()){
            startEventRoute(rssFeed.get());
        }
        
    }
    
    private void startEventRoute(RSSFeed rssFeed) {
        try {
            EventRouter event = new EventRouter(camelContext,rssFeed);
            camelContext.addRoutes(event);
        } catch (Exception ex) {
            Logger.getLogger(RSSFeedsManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
