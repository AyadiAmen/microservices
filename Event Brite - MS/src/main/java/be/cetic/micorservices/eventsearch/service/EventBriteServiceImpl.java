package be.cetic.micorservices.eventsearch.service;

import be.cetic.micorservices.eventsearch.domain.Event;
import be.cetic.micorservices.eventsearch.domain.EventResult;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EventBriteServiceImpl implements EventBriteService {

    @Override
    public List<EventResult> searchEvent(Event event) {

        final String parameters = String.format("location.latitude=%s&location.longitude=%s&location.within=%skm", event.getLatitude(), event.getLongitude(), event.getWithin());
        final String uri = "https://www.eventbriteapi.com/v3/events/search/?" + parameters;

        return searchEventsFrom(uri);
    }
    
    
    @Override
    public List<EventResult> searchAllEvents() {
        final String uri = "https://www.eventbriteapi.com/v3/events/search";
        return searchEventsFrom(uri);
    }

    private List<EventResult> searchEventsFrom(final String uri) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = createHttpHeaders();
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Object.class);
            System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());
            final LinkedHashMap body = (LinkedHashMap) response.getBody();
            final List<Object> events = (List) body.get("events");
            return events.stream().map(LinkedHashMap.class::cast).map(evt -> convertToEvent(evt))
                    .collect(Collectors.toList());

        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
        }
        return null;
    }

    private EventResult convertToEvent(LinkedHashMap map) {
        String name = (String) ((LinkedHashMap) map.get("name")).get("text");
        String id = (String) map.get("id");
        String locale = (String) map.get("locale");
        String description = (String) ((LinkedHashMap) map.get("description")).get("text");
        return new EventResult(name, id, locale, description);
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + "XIOCUCDHEEGJ6TGQVRIJ");
        return headers;
    }

}
