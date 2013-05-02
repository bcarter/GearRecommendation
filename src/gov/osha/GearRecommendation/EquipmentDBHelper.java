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

public class EquipmentDBHelper extends SQLiteOpenHelper {
    public static final String TABLE_EQUIPMENT = "equipment";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM = "item";
    public static final String COLUMN_INFORMATION = "information";

    private static final String DATABASE_NAME = "gear.db";
    private static final int DATABASE_VERSION = 4;
    private static String TAG = "gov.osha.displayEquipment";
    private Context context;

    private static final String TAG_EQUIPMENT = "equipment";
    // equipment JSONArray
    JSONArray equipmentItems = null;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_EQUIPMENT + "(" + COLUMN_ID
            + " integer primary key, " + COLUMN_ITEM
            + " text not null," + COLUMN_INFORMATION
            + " text);";

    public EquipmentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        loadEquipment(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS equipment");
        onCreate(db);
    }

    @SuppressWarnings("unchecked")
    public void loadEquipment(SQLiteDatabase db) {
        JSONObject jo = loadInitialEquipment(R.raw.equipment);

        // Getting Array of Equipment
        try {
            equipmentItems = jo.getJSONArray(TAG_EQUIPMENT);

            final ContentValues cv = new ContentValues();

            ContentValues initialValues = new ContentValues();

            // looping through All Equipment
            for (int i = 0; i < equipmentItems.length(); i++) {
                JSONObject c = equipmentItems.getJSONObject(i);

                // Storing each json item in variable
                initialValues.put(COLUMN_ID, c.getString(COLUMN_ID));
                initialValues.put(COLUMN_ITEM, c.getString(COLUMN_ITEM));
                initialValues.put(COLUMN_INFORMATION, c.getString(COLUMN_INFORMATION));

                long insertId = db.insert(EquipmentDBHelper.TABLE_EQUIPMENT, null,
                        initialValues);

                System.out.println("Equipment inserted with id: " + insertId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private JSONObject loadInitialEquipment(int resourceId) {
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