package gov.osha.GearRecommendation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EquipmentDBAdapter {
    public static final String KEY_ITEM = "item";
    public static final String KEY_INFORMATION = "information";

    private SQLiteDatabase db;
    private Context context;
    private EquipmentDBHelper dbHelper;
    private String[] allColumns = {EquipmentDBHelper.COLUMN_ID,
            EquipmentDBHelper.COLUMN_ITEM,
            EquipmentDBHelper.COLUMN_INFORMATION};

    private static String TAG = "gov.osha.displayEquipment";

    public EquipmentDBAdapter(Context context) {
        dbHelper = new EquipmentDBHelper(context);
        this.context = context;
    }

    public EquipmentDBAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public List<Equipment> getAllEquipment() {
        List<Equipment> equipmentList = new ArrayList<Equipment>();

        Cursor cursor = db.query(EquipmentDBHelper.TABLE_EQUIPMENT,
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

//    public Equipment createEquipment(String item, String information) {
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(KEY_ITEM, item);
//        initialValues.put(KEY_INFORMATION, information);
//        long insertId = db.insert(EquipmentDBHelper.TABLE_EQUIPMENT, null,
//                initialValues);
//        Cursor cursor = db.query(EquipmentDBHelper.TABLE_EQUIPMENT,
//                allColumns, EquipmentDBHelper.COLUMN_ID + " = " + 1, null,
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
//        db.delete(EquipmentDBHelper.TABLE_EQUIPMENT, EquipmentDBHelper.COLUMN_ID
//                + " = " + id, null);
//    }
}