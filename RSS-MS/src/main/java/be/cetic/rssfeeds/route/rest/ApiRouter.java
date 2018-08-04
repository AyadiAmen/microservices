/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cetic.rssfeeds.route.rest;

import be.cetic.rssfeeds.entities.RSSFeed;
import be.cetic.rssfeeds.service.RSSFeedsManagementService;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiRouter extends RouteBuilder {

    @Autowired
    private RSSFeedsManagementService rSSFeedsManagementService;

    @Override
    public void configure() {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest("/rssfeeds").description("RSSfeeds REST SERVICE")
                
                
                .get("")
                .description("The list of all rss feeds urls")
                .outTypeList(RSSFeed.class)
                .to("direct:showall")
                
                
                .post("").
                description("add a new route for a new uri").
                type(RSSFeed.class)
                .to("direct:add")
                
                
                .delete("/{id}")
                .description("Delete an RSS by ID")
                .param().name("id").type(RestParamType.path).dataType("string").description("Id of the RSS to remove").endParam()
                .to("direct:remove");
                
        
        from("direct:showall").process(this::showAllRoutes);
        
        
        from("direct:add").process(this::addRoutes);
        
        
        from("direct:remove").process(this::removeRSS);
    }

    private void showAllRoutes(Exchange exchange) {
        List<RSSFeed> results = rSSFeedsManagementService.getAllRSSFeeds();
        exchange.getIn().setBody(results);

    }

    private void addRoutes(Exchange RSSFeed) {
        Object messageBody = RSSFeed.getIn().getBody();
        if (RSSFeed.class.isInstance(messageBody)) {
            rSSFeedsManagementService.saveRSSFeed(RSSFeed.class.cast(messageBody));
        }

    }

    private void removeRSS(Exchange exchange) {
        final String id =  exchange.getIn().getHeader("id",String.class);
        rSSFeedsManagementService.removeRSSFeedById(id);
        
    }

}
