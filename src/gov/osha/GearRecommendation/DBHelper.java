package gov.osha.GearRecommendation;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DBHelper extends SQLiteOpenHelper {
    public static final String COLUMN_ID = "_id";

    public static final String TABLE_EQUIPMENT = "equipment";
    public static final String EQUIPMENT_COLUMN_ITEM = "item";
    public static final String EQUIPMENT_COLUMN_INFORMATION = "information";

    public static final String TABLE_CRITERIA = "criteria";
    public static final String CRITERIA_COLUMN_CRITERIA = "criteria";
    public static final String CRITERIA_COLUMN_TYPE = "type";
    public static final String CRITERIA_COLUMN_INFORMATION = "information";

    public static final String TABLE_EQUIPMENT_CRITERIA = "equipmentCriteria";
    public static final String EQUIPMENT_CRITERIA_COLUMN_EQUIPMENT_ID = "equipmentId";
    public static final String EQUIPMENT_CRITERIA_COLUMN_CRITERIA_ID = "criteriaId";
    public static final String EQUIPMENT_CRITERIA_COLUMN_LEVEL = "level";

    private static final String DATABASE_NAME = "gear.db";
    private static final int DATABASE_VERSION = 1;
    private static String TAG = "gov.osha.displayEquipment";
    private Context context;

    private static final String TAG_EQUIPMENT = "equipment";
    private static final String TAG_CRITERIA = "criteria";
    private static final String TAG_EQUIPMENT_CRITERIA = "equipmentCriteria";

    // Database creation sql statement
    private static final String EQUIPMENT_DATABASE_CREATE = "create table "
            + TABLE_EQUIPMENT + "(" + COLUMN_ID
            + " integer primary key, " + EQUIPMENT_COLUMN_ITEM
            + " text not null," + EQUIPMENT_COLUMN_INFORMATION
            + " text);";

    private static final String CRITERIA_DATABASE_CREATE = "create table "
            + TABLE_CRITERIA + "(" + COLUMN_ID
            + " integer primary key, " + CRITERIA_COLUMN_CRITERIA
            + " text not null," + CRITERIA_COLUMN_TYPE
            + " text not null," + CRITERIA_COLUMN_INFORMATION
            + " text);";

    private static final String EQUIPMENT_CRITERIA_DATABASE_CREATE = "create table "
            + TABLE_EQUIPMENT_CRITERIA + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + EQUIPMENT_CRITERIA_COLUMN_EQUIPMENT_ID
            + " integer not null," + EQUIPMENT_CRITERIA_COLUMN_CRITERIA_ID
            + " integer not null," + EQUIPMENT_CRITERIA_COLUMN_LEVEL
            + " text not null);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EQUIPMENT_DATABASE_CREATE);
        db.execSQL(CRITERIA_DATABASE_CREATE);
        db.execSQL(EQUIPMENT_CRITERIA_DATABASE_CREATE);
        loadEquipment(db);
        loadCriteria(db);
        loadEquipmentCriteria(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_CRITERIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CRITERIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT);
        onCreate(db);
    }

    @SuppressWarnings("unchecked")
    public void loadEquipment(SQLiteDatabase db) {
        JSONObject jo = loadRawJSON(R.raw.equipment);

        // Getting Array of Equipment
        try {
            // equipment JSONArray
            JSONArray equipmentItems =  jo.getJSONArray(TAG_EQUIPMENT);
            ContentValues initialValues = new ContentValues();

            // looping through All Equipment
            for (int i = 0; i < equipmentItems.length(); i++) {
                JSONObject c = equipmentItems.getJSONObject(i);

                // Storing each json item in variable
                initialValues.put(COLUMN_ID, c.getString(COLUMN_ID));
                initialValues.put(EQUIPMENT_COLUMN_ITEM, c.getString(EQUIPMENT_COLUMN_ITEM));
                initialValues.put(EQUIPMENT_COLUMN_INFORMATION, c.getString(EQUIPMENT_COLUMN_INFORMATION));

                long insertId = db.insert(DBHelper.TABLE_EQUIPMENT, null,
                        initialValues);

                System.out.println("Equipment inserted with id: " + insertId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadCriteria(SQLiteDatabase db) {
        JSONObject jo = loadRawJSON(R.raw.criteria);

        // Getting Array of Equipment
        try {
            JSONArray criteriaItems = jo.getJSONArray(TAG_CRITERIA);
            ContentValues initialValues = new ContentValues();

            // looping through All Criteria
            for (int i = 0; i < criteriaItems.length(); i++) {
                JSONObject c = criteriaItems.getJSONObject(i);

                // Storing each json item in variable
                initialValues.put(COLUMN_ID, c.getString(COLUMN_ID));
                initialValues.put(CRITERIA_COLUMN_CRITERIA, c.getString(CRITERIA_COLUMN_CRITERIA));
                initialValues.put(CRITERIA_COLUMN_TYPE, c.getString(CRITERIA_COLUMN_TYPE));
                initialValues.put(CRITERIA_COLUMN_INFORMATION, c.getString(CRITERIA_COLUMN_INFORMATION));

                long insertId = db.insert(DBHelper.TABLE_CRITERIA, null,
                        initialValues);

                System.out.println("Criteria inserted with id: " + insertId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadEquipmentCriteria(SQLiteDatabase db) {
        JSONObject jo = loadRawJSON(R.raw.equipment_criteria);

        // Getting Array of Criteria
        try {
            JSONArray equipmentCriteriaItems = jo.getJSONArray(TAG_EQUIPMENT_CRITERIA);
            ContentValues initialValues = new ContentValues();

            // looping through All Equipment
            for (int i = 0; i < equipmentCriteriaItems.length(); i++) {
                JSONObject c = equipmentCriteriaItems.getJSONObject(i);

                // Storing each json item in variable
                initialValues.put(EQUIPMENT_CRITERIA_COLUMN_EQUIPMENT_ID, c.getString(EQUIPMENT_CRITERIA_COLUMN_EQUIPMENT_ID));
                initialValues.put(EQUIPMENT_CRITERIA_COLUMN_CRITERIA_ID, c.getString(EQUIPMENT_CRITERIA_COLUMN_CRITERIA_ID));
                initialValues.put(EQUIPMENT_CRITERIA_COLUMN_LEVEL, c.getString(EQUIPMENT_CRITERIA_COLUMN_LEVEL));

                long insertId = db.insert(DBHelper.TABLE_EQUIPMENT_CRITERIA, null,
                        initialValues);

                System.out.println("EquipmentCriteria inserted with id: " + insertId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private JSONObject loadRawJSON(int resourceId) {
        try {
            final InputStream is = context.getResources().openRawResource(resourceId);

            final StringBuilder jsonString = new StringBuilder();

            for (final BufferedReader isReader = new BufferedReader(new InputStreamReader(is), 16000);
                 isReader.ready(); ) {
                jsonString.append(isReader.readLine());
            }

            final JSONObject jo = new JSONObject(jsonString.toString());

            return jo;

        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}