package project.remindme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ReminderDB extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "reminderDB.db";
    public static final String TABLE_NAME = "Reminder";

    public static final String COL_ID = "ReminderID";
    public static final String COL_NAME = "ReminderName";
    public static final String COL_DESC = "ReminderDesc";
    public static final String COL_DEST = "ReminderDest";
    public static final String COL_LAT = "ReminderLat";
    public static final String COL_LNG = "ReminderLng";

    public ReminderDB(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table
        String CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY, " +
                COL_NAME + " TEXT, " +
                COL_DESC + " TEXT, " +
                COL_DEST + " TEXT, " +
                COL_LAT + " REAL, " +
                COL_LNG + " REAL)";
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DELETE);
        onCreate(db);
    }

    public String loadHandler() {
        String result = "";
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int current = cursor.getInt(0);
            String current_name = cursor.getString(1);
            String current_desc = cursor.getString(2);
            String current_dest = cursor.getString(3);
            double current_lat = cursor.getDouble(4);
            double current_lng = cursor.getDouble(5);
            result += String.valueOf((current)) + " " +
                    current_name + " " +
                    current_desc + " " +
                    current_dest + " " +
                    String.valueOf(current_lat) + " " +
                    String.valueOf(current_lng) + " " +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public int countRecords() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void addHandler(Reminder r) {
        ContentValues val = new ContentValues();

        val.put(COL_ID, r.getID());
        val.put(COL_NAME, r.getName());
        val.put(COL_DESC, r.getDescription());
        val.put(COL_DEST, r.getDestination());
        val.put(COL_LAT, r.getLatitude());
        val.put(COL_LNG, r.getLongitude());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, val);
        db.close();
    }

    public Reminder findHandler(String name) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_NAME + "  = '" + name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Reminder reminder = new Reminder();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();

            reminder.setID(Integer.parseInt(cursor.getString(0)));
            reminder.setName(cursor.getString(1));
            reminder.setDescription(cursor.getString(2));
            reminder.setDestination(cursor.getString(3));
            reminder.setLatitude(cursor.getDouble(4));
            reminder.setLongitude(cursor.getDouble(5));

            cursor.close();
        } else {
            reminder = null;
        }
        db.close();
        return reminder;
    }

    public Reminder findHandler(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + "  = '" + String.valueOf(id) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Reminder reminder = new Reminder();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();

            reminder.setID(Integer.parseInt(cursor.getString(0)));
            reminder.setName(cursor.getString(1));
            reminder.setDescription(cursor.getString(2));
            reminder.setDestination(cursor.getString(3));
            reminder.setLatitude(cursor.getDouble(4));
            reminder.setLongitude(cursor.getDouble(5));

            cursor.close();
        } else {
            reminder = null;
        }
        db.close();
        return reminder;
    }

    public boolean deleteHandler(int id) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + "  = '" + String.valueOf(id) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Reminder reminder = new Reminder();
        if (cursor.moveToFirst()) {
            // element exists, remove it
            reminder.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, COL_ID + "=?", new String[] { String.valueOf(reminder.getID()) });
            cursor.close();
        }
        db.close();
        return result;
    }

    public boolean updateHandler(int ID, String name, String desc, String dest, double lat, double lng) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();

        args.put(COL_ID, ID);
        args.put(COL_NAME, name);
        args.put(COL_DESC, desc);
        args.put(COL_DEST, dest);
        args.put(COL_LAT, lat);
        args.put(COL_LNG, lng);

        return db.update(TABLE_NAME, args, COL_ID + "=" + ID, null) > 0;
    }

    public ArrayList<Reminder> loadAllReminders() {
        ArrayList<Reminder> result = new ArrayList<>();

        String fullDbLoad = loadHandler();

        // parse line by line
        String[] line = fullDbLoad.split("\n");
        for (int i = 0; i < line.length; i++) {
            // parse out the individual elements
            String[] elements = line[i].split(" ");
            Reminder to_add = findHandler(Integer.parseInt(elements[0]));
            result.add(to_add);
        }

        return result;
    }

}
