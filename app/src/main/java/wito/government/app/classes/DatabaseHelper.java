package wito.government.app.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database name and version
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the user info table
        String createUserInfoTable = "create table userInfo (firstName text, lastName text, phone text, password text, nida text, id text)";
        db.execSQL(createUserInfoTable);

        // Create appointments table
        String appointmentsTableString = "create table appointments(id text, doctorName text, doctorImage text, doctorId text, date text, time text)";
        db.execSQL(appointmentsTableString);

        // Create Medical Info Table
        String walletQuery = "" +
                "create table wallet(" +
                "salioKuu text," +
                "thamaniHisa text," +
                "idadiMiadi text," +
                "hisaKusubiri text)";
        db.execSQL(walletQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Drop older tables if they exist
//        db.execSQL("DROP TABLE IF EXISTS " + FAVORITES_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + CART_TABLE_NAME);

        // Create new tables
        onCreate(db);
    }


    public HashMap<String,String> getTableData(String tableName) {
        HashMap<String, String> data = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tableName, null);
        if (cursor.moveToFirst()) {
            do {
                for (int i =0; i<cursor.getColumnCount(); i++) {
                    data.put(cursor.getColumnName(i), cursor.getString(i));
                }
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return data;
    }


    public boolean itemExists(String tableName, String id) {
        String[] columns = {"id"};
        String selection = "id = ?";
        String[] selectionArgs = {id};
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null);

        boolean itemExists = cursor.moveToFirst();
        cursor.close();
        return itemExists;
    }

    public void addToTable(String tableName, ContentValues values) {
        this.getWritableDatabase().insert(tableName, null, values);
    }

    public void removeFromTable(String tableName, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "id = ?";
        String[] selectionArgs = { id };
        db.delete(tableName, selection, selectionArgs);
        db.close();
    }
}
