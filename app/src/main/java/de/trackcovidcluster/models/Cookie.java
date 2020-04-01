package de.trackcovidcluster.models;

import java.util.Date;

public class Cookie {

    private String hashedUUID;
    private long   timestamp;

    public Cookie(String hashedUUID, long timestamp) {
        this.hashedUUID = hashedUUID;
        this.timestamp = timestamp;
    }

    public String getHashedUUID() {
        return hashedUUID;
    }

    public void setHashedUUID(String hashedUUID) {
        this.hashedUUID = hashedUUID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
