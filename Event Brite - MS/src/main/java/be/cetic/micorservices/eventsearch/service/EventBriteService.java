package be.cetic.micorservices.eventsearch.service;

import be.cetic.micorservices.eventsearch.domain.Event;
import be.cetic.micorservices.eventsearch.domain.EventResult;
import java.util.List;

public interface EventBriteService {

    /**
     *
     * @param this methtod uses location parameters to find near events
     * @return
     */
    List<EventResult> searchEvent(Event event);

    /**
     * This method looks for all the events from Event Bride by default without
     * any parameters 
     *
     * @return A list of all the available events 
     */
    List<EventResult> searchAllEvents();

}
