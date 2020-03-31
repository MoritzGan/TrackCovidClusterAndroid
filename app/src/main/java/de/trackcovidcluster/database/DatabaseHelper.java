package de.trackcovidcluster.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;

import org.libsodium.jni.Sodium;

import java.util.ArrayList;
import java.util.List;

import de.trackcovidcluster.models.Cookie;

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
        db.execSQL("DROP TABLE IF EXISTS " + LocationData.TABLE_NAME);

        onCreate(db);
    }

    /**
     * Insert a encrypted cookie into the db.
     * Returns the id of the inserted row
     *
     * @param cookie
     * @return
     */

    public long insertDataSet(Cookie cookie, String pkey) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        byte[] bytes = new byte[cookie.getHashedUUID().getBytes().length];

        int result = Sodium.crypto_box_seal(
                bytes,
                cookie.getHashedUUID().getBytes(),
                cookie.getHashedUUID().getBytes().length,
                pkey.getBytes()
        );

        String decoded = new String(Base64.encode(bytes, Base64.DEFAULT));

        values.put(LocationData.COLUMN_ENCRYPTED_COOKIE, bytes);
        values.put(LocationData.COLUMN_TIME, cookie.getTimestamp());

        long id = db.insert(LocationData.TABLE_NAME, null, values);

        Log.d("SQL_HELPER", "\n" +
                " ADDED NEW DATASET TO LOCAL DB!\n" + decoded);

        db.close();

        return id;
    }

    /**
     * Returns all Cookies from database
     *
     * @return
     */

    public List<Cookie> getCookieBundle() {
        List<Cookie> sensorData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + LocationData.TABLE_NAME + " ORDER BY " + LocationData.COLUMN_TIME + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Cookie cookie = new Cookie(
                        String.valueOf(cursor.getColumnIndex(LocationData.COLUMN_ENCRYPTED_COOKIE)),
                        cursor.getColumnIndex(LocationData.COLUMN_TIME)
                );

                sensorData.add(cookie);

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
        db.delete(LocationData.TABLE_NAME, null, null);
    }
}
