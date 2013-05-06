package gov.osha.GearRecommendation;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter {
    private SQLiteDatabase db;
    private Context context;
    private DBHelper dbHelper;
    private String[] allColumns = {DBHelper.COLUMN_ID,
            DBHelper.EQUIPMENT_COLUMN_ITEM,
            DBHelper.EQUIPMENT_COLUMN_INFORMATION};
    private String[] allCriteriaColumns = {DBHelper.COLUMN_ID,
            DBHelper.CRITERIA_COLUMN_CRITERIA,
            DBHelper.CRITERIA_COLUMN_TYPE,
            DBHelper.CRITERIA_COLUMN_INFORMATION};

    private static String TAG = "gov.osha.displayEquipment";

    public DBAdapter(Context context) {
        this.context = context;
        dbHelper = new DBHelper(this.context);
    }

    public DBAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public List<Equipment> getAllEquipment(String level, String[] criteria) {
        List<Equipment> equipmentList = new ArrayList<Equipment>();
        ArrayList<String> selectionArgs = new ArrayList<String>();
        selectionArgs.add(level);

        for (int i = 0; i < criteria.length; i++) {
            selectionArgs.add(criteria[i]);
        }

        String[] selectionArgsArray = new String[selectionArgs.size()];
        selectionArgsArray = selectionArgs.toArray(selectionArgsArray);

        final String MY_QUERY = "SELECT * FROM equipment e INNER JOIN equipmentCriteria ec ON e._id=ec.equipmentId WHERE ec.level=? and ec.criteriaId in(" + makePlaceholders(criteria.length) + ")";

        Cursor cursor = db.rawQuery(MY_QUERY, selectionArgsArray);

//        Cursor cursor = db.query(DBHelper.TABLE_EQUIPMENT,
//                allColumns, "_id in(select equipmentId from equipmentCriteria where level=? and criteriaId in(" + makePlaceholders(criteria.length) + "))", selectionArgsArray, null, null, null);

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

    String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    public List<Criteria> getCriteria(String type) {
        List<Criteria> criteriaList = new ArrayList<Criteria>();
        ArrayList<String> selectionArgs = new ArrayList<String>();
        selectionArgs.add(type);

        String[] selectionArgsArray = new String[selectionArgs.size()];
        selectionArgsArray = selectionArgs.toArray(selectionArgsArray);

        Cursor cursor = db.query(DBHelper.TABLE_CRITERIA,
                allCriteriaColumns, "type=?", selectionArgsArray, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Criteria criteria = cursorToCriteria(cursor);
            criteriaList.add(criteria);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return criteriaList;
    }

    private Criteria cursorToCriteria(Cursor cursor) {
        Criteria criteria = new Criteria();
        criteria.setId(cursor.getInt(0));
        criteria.setCriteria(cursor.getString(1));
        criteria.setType(cursor.getString(2));
        criteria.setInformation(cursor.getString(3));
        return criteria;
    }

//    public Equipment createEquipment(String item, String information) {
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(KEY_ITEM, item);
//        initialValues.put(KEY_INFORMATION, information);
//        long insertId = db.insert(DBHelper.TABLE_EQUIPMENT, null,
//                initialValues);
//        Cursor cursor = db.query(DBHelper.TABLE_EQUIPMENT,
//                allColumns, DBHelper.COLUMN_ID + " = " + 1, null,
//                null, null, null);
//        cursor.moveToFirst();
//        Equipment equipment = cursorToEquipment(cursor);
//        cursor.close();
//        return equipment;
//    }
//
//    public void deleteEquipment(Equipment equipment) {
//        long id = equipment.getId();
//        System.out.println("Equipment deleted with id: " + id);
//        db.delete(DBHelper.TABLE_EQUIPMENT, DBHelper.COLUMN_ID
//                + " = " + id, null);
//    }
}