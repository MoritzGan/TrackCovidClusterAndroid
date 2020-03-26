package de.trackcovidcluster.models;

import java.util.Date;

public class Cookie {

    private String position;
    private long   timestamp;

    public Cookie(String position, long timestamp) {
        this.position = position;
        this.timestamp = timestamp;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
