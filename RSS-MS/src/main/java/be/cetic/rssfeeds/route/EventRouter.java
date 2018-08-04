/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cetic.rssfeeds.route;

import be.cetic.rssfeeds.entities.RSSFeed;
import java.util.stream.Collectors;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;

/**
 *
 * @author cetic
 */
public class EventRouter extends RouteBuilder {
    
    private final RSSFeed rssFeed  ;
    
    private RouteDefinition routeDefinition ;

    public EventRouter(CamelContext context,RSSFeed rssFeed) {
        super(context);
        this.rssFeed = rssFeed;
    }
    


    @Override
    public void configure() throws Exception {
           final String uri = rssFeed.getParameters().stream()
                    .map(param -> param.getName() + "=" + param.getValue())
                    .collect(Collectors.joining("&", rssFeed.getURI() + "?", ""));
           final String mongoBeanURI = String.format("mongoBean?database=RSSdb&collection=%s&operation=insert", rssFeed.getCollection());
          RouteDefinition def =  from("rss:"+uri).id("Route_"+rssFeed.getId())
                    .to("mongodb:"+mongoBeanURI)
                    .log("${body}");

    }

    public RouteDefinition getRouteDefinition() {
        return routeDefinition;
    }
    
    
    
    

}
