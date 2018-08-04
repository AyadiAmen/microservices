package be.cetic.micorservices.eventsearch.domain;


public class Event {
    
    private final double latitude ;
    
    private final double longitude ;
    
    private final int within ;

    public Event(double latitude, double longitude, int within) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.within = within;
    }

    public Event() {
        this(0,0,0);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getWithin() {
        return within;
    }
    
    
    
}
