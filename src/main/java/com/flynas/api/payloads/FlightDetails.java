package com.flynas.api.payloads;

public class FlightDetails {
    private String origin;
    private String destination;
    private String date;
    public FlightDetails(){}
    public FlightDetails(String origin,String destination,String date)
    {
        this.date=date;
        this.destination=destination;
        this.origin=origin;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

}
