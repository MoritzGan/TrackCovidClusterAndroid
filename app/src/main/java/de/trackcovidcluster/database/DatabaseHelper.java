package de.trackcovidcluster.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.libsodium.jni.NaCl;
import org.libsodium.jni.Sodium;

import java.util.ArrayList;
import java.util.List;

import de.trackcovidcluster.changeStatus.ReturnCookiesCallback;
import de.trackcovidcluster.models.Cookie;

import static de.trackcovidcluster.database.LocationData.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LocationDataDatabase";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table
        db.execSQL(LocationData.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    /**
     * Insert a encrypted cookie into the db.
     * Returns the id of the inserted row
     *
     * @param cookie
     * @return
     */

    public long insertDataSet(Cookie cookie, String pkey) throws JSONException {

        SQLiteDatabase db = this.getWritableDatabase();
        JSONObject jsonString = new JSONObject();
        ContentValues values = new ContentValues();

        jsonString.put("UUID", cookie.getHashedUUID());
        jsonString.put("Timestamp", cookie.getTimestamp());

        byte[] bytes = new byte[jsonString.toString().getBytes().length];
        byte[] jsonInBytes = jsonString.toString().getBytes();

        NaCl.sodium();
        Sodium.crypto_box_seal(
                bytes,
                jsonInBytes,
                jsonInBytes.length,
                pkey.getBytes()
        );

        String decoded = Base64.encodeToString(bytes, Base64.NO_WRAP);

        values.put(LocationData.COLUMN_ENCRYPTED_COOKIE, decoded);
        values.put(LocationData.COLUMN_TIME, cookie.getTimestamp());

        long id = db.insert(TABLE_NAME, null, values);

        db.close();

        Log.d("SQL_HELPER", "\n" +
                " ADDED NEW DATASET TO LOCAL DB!\n" + decoded + " IN ROW NUMBER " + id);

        return id;
    }

    /**
     * Returns all Cookies from database
     *
     * @return
     */

    public List<String> getCookieBundle() {
        List<String> sensorData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + LocationData.COLUMN_TIME + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                sensorData.add(String.valueOf(cursor.getString(cursor.getColumnIndex(LocationData.COLUMN_ENCRYPTED_COOKIE))));
                Log.d("Database", " " + String.valueOf(cursor.getString(cursor.getColumnIndex(LocationData.COLUMN_ENCRYPTED_COOKIE))));
            } while (cursor.moveToNext());
        }

        db.close();

        return sensorData;
    }

    /**
     * Deletes all Cookies. Call when tranfer to server was succesfull
     */

    public void delteAllCookies() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return count;
    }
}
