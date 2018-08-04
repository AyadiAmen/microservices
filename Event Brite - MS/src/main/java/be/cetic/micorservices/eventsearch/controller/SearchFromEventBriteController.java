package be.cetic.micorservices.eventsearch.controller;

import be.cetic.micorservices.eventsearch.domain.Event;
import be.cetic.micorservices.eventsearch.domain.EventResult;
import be.cetic.micorservices.eventsearch.service.EventBriteService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
final class SearchFromEventBriteController {

    private final EventBriteService eventBriteService;

    public SearchFromEventBriteController(EventBriteService eventBriteService) {
        this.eventBriteService = eventBriteService;
    }

    @GetMapping("/event")
    List<EventResult> search(@RequestParam(value = "latitude") String latitude,
            @RequestParam(value = "longitude") String longitude,
            @RequestParam(value = "within") String within) {

        System.out.println("=====>" + within);
        return eventBriteService.searchEvent(new Event(Double.parseDouble(latitude),
                Double.parseDouble(longitude),
                Integer.parseInt(within)));
    }

}
