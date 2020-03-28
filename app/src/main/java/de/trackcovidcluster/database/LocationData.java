package de.trackcovidcluster.database;

public class LocationData {

    public static final String TABLE_NAME = "locationData";
    public static final String COLUMN_ID   = "id";
    public static final String COLUMN_POS = "position";
    public static final String COLUMN_TIME = "timestamp";

    private int     id;
    private String  position;
    private String  timestamp;

    /**
     *      Create the SQL table.
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_POS       + " VARCHAR,"
                    + COLUMN_TIME       + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public LocationData() {
    }

    public LocationData(int id, String position, String timestamp) {
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
