package be.cetic.micorservices.eventsearch.domain;


public class EventResult {

    private final String name;
    private final String id;
    private final String local;
    private final String description;

    public EventResult(String name, String id, String local, String description) {
        this.name = name;
        this.id = id;
        this.local = local;
        this.description = description;
    }

    public EventResult() {
        this("", "", "", "");
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getLocal() {
        return local;
    }

    public String getDescription() {
        return description;
    }

    
}
