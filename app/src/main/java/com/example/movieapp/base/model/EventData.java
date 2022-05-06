package com.example.movieapp.base.model;

public class EventData {

    String eventId;
    Object data;

    public EventData(String eventId, Object data) {
        this.eventId = eventId;
        this.data = data;
    }

    public EventData(String eventId) {
        this(eventId, null);
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
