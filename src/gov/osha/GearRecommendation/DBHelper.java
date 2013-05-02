package gov.osha.GearRecommendation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_EQUIPMENT = "equipment";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM = "item";
    public static final String COLUMN_INFORMATION = "information";

    private static final String DATABASE_NAME = "gear.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_EQUIPMENT + "(" + COLUMN_ID
            + " integer primary key, " + COLUMN_ITEM
            + " text not null," + COLUMN_INFORMATION
            + " text);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
//        db.execSQL("INSERT INTO equipment VALUES (null, 'item 1', 'item 1 info');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS equipment");
        onCreate(db);
    }

}