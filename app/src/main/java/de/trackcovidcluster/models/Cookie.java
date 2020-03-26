package de.trackcovidcluster.models;

import java.util.Date;

public class Cookie {

    private int id;
    private String position;
    private Date   timestamp;

    public Cookie(int id, String position, Date timestamp) {
        this.id = id;
        this.position = position;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
