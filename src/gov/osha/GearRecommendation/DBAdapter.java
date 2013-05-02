package gov.osha.GearRecommendation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBAdapter
{
    private static final String DATABASE_TABLE = "equipment";
    private static final String TAG_EQUIPMENT = "equipment";
    public static final String KEY_ID = "_id";
    public static final String KEY_ITEM = "item";
    public static final String KEY_INFORMATION = "information";

    private SQLiteDatabase db;
    private Context context;
    private DBHelper dbHelper;
    private String[] allColumns = { DBHelper.COLUMN_ID,
            DBHelper.COLUMN_ITEM,
            DBHelper.COLUMN_INFORMATION };
    // contacts JSONArray
    JSONArray equipmentItems = null;

    private static String TAG = "gov.osha.displayEquipment";

    public DBAdapter(Context context)
    {
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    public DBAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbHelper.close();
    }

    public Equipment createEquipment(String item, String information)
    {
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(KEY_ITEM, item);
//        initialValues.put(KEY_INFORMATION, information);
//        long insertId = db.insert(DBHelper.TABLE_EQUIPMENT, null,
//                initialValues);
        loadEquipment();
        Cursor cursor = db.query(DBHelper.TABLE_EQUIPMENT,
                allColumns, DBHelper.COLUMN_ID + " = " + 1, null,
                null, null, null);
        cursor.moveToFirst();
        Equipment equipment = cursorToEquipment(cursor);
        cursor.close();
        return equipment;
    }

    public void deleteEquipment(Equipment equipment) {
        long id = equipment.getId();
        System.out.println("Equipment deleted with id: " + id);
        db.delete(DBHelper.TABLE_EQUIPMENT, DBHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Equipment> getAllEquipment() {
        List<Equipment> equipmentList = new ArrayList<Equipment>();

        Cursor cursor = db.query(DBHelper.TABLE_EQUIPMENT,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Equipment equipment = cursorToEquipment(cursor);
            equipmentList.add(equipment);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return equipmentList;
    }

    private Equipment cursorToEquipment(Cursor cursor) {
        Equipment equipment = new Equipment();
        equipment.setId(cursor.getLong(0));
        equipment.setItem(cursor.getString(1));
        return equipment;
    }

    @SuppressWarnings("unchecked")
    public void loadEquipment(){
        JSONObject jo = loadInitialEquipment(R.raw.equipment);

        // Getting Array of Equipment
        try {
        equipmentItems = jo.getJSONArray(TAG_EQUIPMENT);

//        db.beginTransaction();
        final ContentValues cv = new ContentValues();

        ContentValues initialValues = new ContentValues();

        // looping through All Equipment
        for(int i = 0; i < equipmentItems.length(); i++){
            JSONObject c = equipmentItems.getJSONObject(i);

            // Storing each json item in variable
            initialValues.put(KEY_ID, c.getString(KEY_ID));
            initialValues.put(KEY_ITEM, c.getString(KEY_ITEM));
            initialValues.put(KEY_INFORMATION, c.getString(KEY_INFORMATION));

            long insertId = db.insert(DBHelper.TABLE_EQUIPMENT, null,
                    initialValues);

            System.out.println("Equipment inserted with id: " + insertId);

        }
    } catch (JSONException e) {
    e.printStackTrace();
//}   finally {
//            db.setTransactionSuccessful();
//            db.endTransaction();
//            db.close();

        }
//        Log.d(TAG, "Successfully added " + jo.length() + " classification entries.");
    }

    /**
     * A weight file is just a static JSON file used to set the initial
     * weights for unit recommendation precedence.
     *
     * @param resourceId
     * @return
     */
    @SuppressWarnings("unchecked")
    private JSONObject loadInitialEquipment(int resourceId){
        try{

            final JSONObject jo = loadJsonObjectFromRawResource(context, resourceId);

            // remove all "comments", which are just key entries that start with "--"
            for (final Iterator i = jo.keys(); i.hasNext(); ){
                final String key = (String)i.next();
                if (key.startsWith("--")){
                    i.remove();
                }
            }

            return jo;

        }catch (final Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static JSONObject loadJsonObjectFromRawResource(Context context, int resourceId) throws IOException, JSONException {
        Log.d(TAG, "context: " + context + "     resourceId: " + resourceId);
        final InputStream is = context.getResources().openRawResource(resourceId);

        final StringBuilder jsonString = new StringBuilder();

        for (final BufferedReader isReader = new BufferedReader(new InputStreamReader(is), 16000);
             isReader.ready();){
            jsonString.append(isReader.readLine());
        }

        Log.d(TAG, "json " + jsonString.toString());
        return new JSONObject(jsonString.toString());
    }

}