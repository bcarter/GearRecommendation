package gov.osha.GearRecommendation;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

public class DisplayEquipment extends ListActivity {
    private DBAdapter datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayequipmentlayout);

        datasource = new DBAdapter(DisplayEquipment.this);
        datasource.open();

        List<Equipment> values = datasource.getAllEquipment();

        // Use the SimpleCursorAdapter to show the
        // elements in a ListView
//        ListView requiredList = (ListView) findViewById(R.id.requiredListView);
        ArrayAdapter<Equipment> adapter = new ArrayAdapter<Equipment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);      // Will be called via the onClick attribute
                                     // of the buttons in main.xml
    }
        public void onClick(View view) {
            @SuppressWarnings("unchecked")
            ArrayAdapter<Equipment> adapter = (ArrayAdapter<Equipment>) getListAdapter();
            Equipment equipment = null;
            switch (view.getId()) {
                case R.id.add:
                    String[] equipmentList = new String[] { "Cool", "Very nice", "Hate it" };
                    int nextInt = new Random().nextInt(3);
                    // Save the new equipment to the database
                    equipment = datasource.createEquipment(equipmentList[nextInt], "test");
                    adapter.add(equipment);
                    break;
                case R.id.delete:
                    if (getListAdapter().getCount() > 0) {
                        equipment = (Equipment) getListAdapter().getItem(0);
                        datasource.deleteEquipment(equipment);
                        adapter.remove(equipment);
                    }
//                    datasource.loadEquipment();
                    break;
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onResume() {
            datasource.open();
            super.onResume();
        }

        @Override
        protected void onPause() {
            datasource.close();
            super.onPause();
        }

//        ListView requiredList = (ListView) findViewById(R.id.requiredListView);
//        ArrayAdapter<CharSequence> requiredAdapter = ArrayAdapter.createFromResource(this,
//                R.array.required_array, android.R.layout.simple_list_item_1);
//        requiredList.setAdapter(requiredAdapter);

//        ListView recommendedList = (ListView) findViewById(R.id.recommendedListView);
//        ArrayAdapter<CharSequence> recommendedAdapter = ArrayAdapter.createFromResource(this,
//                R.array.recommended_array, android.R.layout.simple_list_item_1);
//        recommendedList.setAdapter(recommendedAdapter);
//
//        ListView prohibitedList = (ListView) findViewById(R.id.prohibitedListView);
//        ArrayAdapter<CharSequence> prohibitedAdapter = ArrayAdapter.createFromResource(this,
//                R.array.prohibited_array, android.R.layout.simple_list_item_1);
//        prohibitedList.setAdapter(prohibitedAdapter);
}

