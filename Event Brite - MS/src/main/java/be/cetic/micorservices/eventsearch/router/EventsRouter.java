package be.cetic.micorservices.eventsearch.router;

import be.cetic.micorservices.eventsearch.domain.Event;
import be.cetic.micorservices.eventsearch.domain.EventResult;
import be.cetic.micorservices.eventsearch.service.EventBriteService;
import java.util.List;
import java.util.Objects;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import static org.apache.camel.model.rest.RestParamType.query;
import org.springframework.stereotype.Component;

@Component
public class EventsRouter extends RouteBuilder {

    private final EventBriteService eventBriteService;

    public EventsRouter(EventBriteService eventBriteService) {
        this.eventBriteService = eventBriteService;
    }

    @Override
    public void configure() {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest("/events")
                .description("Events REST SERVICE")
                .get("/all")
                .description("The list of all events")
                .outTypeList(EventResult.class)
                .to("direct:all")
                .get("/byregion").param().name("latitude").type(query).description("the latitude of the location").dataType("double").endParam().
                param().name("laongitude").type(query).description("the longitude of the location").dataType("double").endParam().
                param().name("within").type(query).description("the ray of search").dataType("integer").endParam()
                .description("The list of all events by region. The service requires three parameters ...")
                .outTypeList(EventResult.class)
                .to("direct:byRegion");
        from("direct:all").process(this::searchAllEvents);
        from("direct:byRegion").process(this::searchAllEventsByRegion);
    }

    private void searchAllEvents(Exchange exchange) {
        List<EventResult> results = eventBriteService.searchAllEvents();
        exchange.getIn().setBody(results);

    }

    private void searchAllEventsByRegion(Exchange exchange) {

        Object latitude = exchange.getIn().getHeader("latitude");
        Object longitude = exchange.getIn().getHeader("longitude");
        Object within = exchange.getIn().getHeader("within");
        final List<EventResult> results;
        if (Objects.nonNull(within)
                && Objects.nonNull(longitude)
                && Objects.nonNull(within)) {
            System.out.println(String.format("Looking for Events by Region of : %s latitude and %s longitude within %s km", latitude, longitude, within));
            Event event = new Event(Double.valueOf(latitude.toString()), Double.valueOf(longitude.toString()), Integer.valueOf(within.toString()));
            results = eventBriteService.searchEvent(event);
        } else {
            results = eventBriteService.searchAllEvents();
        }

        exchange.getIn().setBody(results);

    }
}
