package de.trackcovidcluster.database;

public class LocationData {

    public static final String TABLE_NAME = "locationData";
    public static final String COLUMN_ID   = "id";
    public static final String COLUMN_ENCRYPTED_COOKIE = "cookie";
    public static final String COLUMN_TIME = "timestamp";

    private int     id;
    private String  cookie;
    private String  timestamp;

    /**
     *      Create the SQL table.
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME        + "("
                    + COLUMN_ENCRYPTED_COOKIE   + " VARCHAR,"
                    + COLUMN_TIME               + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public LocationData() {
    }

    public LocationData(int id, String cookie, String timestamp) {
        this.id = id;
        this.cookie = cookie;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
