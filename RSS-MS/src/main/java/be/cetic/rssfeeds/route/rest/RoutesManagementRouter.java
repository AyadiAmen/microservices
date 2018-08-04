/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cetic.rssfeeds.route.rest;

import be.cetic.rssfeeds.domain.CamelRoute;
import be.cetic.rssfeeds.service.RSSFeedsManagementService;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoutesManagementRouter extends RouteBuilder {

    @Autowired
    private RSSFeedsManagementService rSSFeedsManagementService;

    @Override
    public void configure() {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest("/routes").description("All the services to manage Camel routers")
                .get("")
                .description("The list of all running routes")
                .outTypeList(CamelRoute.class)
                .to("direct:allRunningRoutes")
                .put("/stop/{id}")
                .description("Stop the route by ID")
                .param().name("id").type(RestParamType.path).dataType("string").description("Id of the running route to stop").endParam()
                .to("direct:stopRouteByID")
                .put("/start/{id}")
                .description("Start the route by ID")
                .param().name("id").type(RestParamType.path).dataType("string").description("Id of the running route to stop").endParam()
                .to("direct:startRouteByID")
                .put("/start")
                .description("Start all the rss routes")
                .to("direct:startAllRssRoutes")
                .put("/stop")
                .description("Stop all the rss routes")
                .to("direct:stopAllRssRoutes");

        from("direct:startAllRssRoutes").process(this::startAllRssRoutes);
        from("direct:stopAllRssRoutes").process(this::stopAllRssRoutes);;

        from("direct:allRunningRoutes").process(this::showAllRoutes);
        from("direct:stopRouteByID").process(this::stopRouteByID);
        from("direct:startRouteByID").process(this::startRouteByID);
    }

    private void showAllRoutes(Exchange exchange) {
        List<CamelRoute> results = rSSFeedsManagementService.getRunningRoutes();
        exchange.getIn().setBody(results);
    }

    private void stopRouteByID(Exchange exchange) {

        final String id = exchange.getIn().getHeader("id", String.class);
        rSSFeedsManagementService.stopRouteByID(id);
    }
    
     private void startAllRssRoutes(Exchange exchange) {
        rSSFeedsManagementService.startAllRssRoutes();
    }

    private void stopAllRssRoutes(Exchange exchange) {
        rSSFeedsManagementService.stopAllRssRoutes();
    }
    
    private void startRouteByID(Exchange exchange) {
         final String id = exchange.getIn().getHeader("id", String.class);
        rSSFeedsManagementService.startRouteByID(id);
    }

}
