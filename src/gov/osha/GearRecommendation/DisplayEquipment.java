package gov.osha.GearRecommendation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class DisplayEquipment extends Activity {
    private DBAdapter datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayequipmentlayout);

        datasource = new DBAdapter(DisplayEquipment.this);
        datasource.open();

        String[] requiredCriteria = {"2", "4", "5"};
        List<Equipment> requiredValues = datasource.getAllEquipment("required", requiredCriteria);
        String[] recommendedCriteria = {"1", "3", "5"};
        List<Equipment> recommendedValues = datasource.getAllEquipment("recommended", recommendedCriteria);

        // Use the SimpleCursorAdapter to show the
        // elements in a ListView
        ListView requiredList = (ListView) findViewById(R.id.requiredListView);
        ArrayAdapter<Equipment> requiredAdapter = new ArrayAdapter<Equipment>(this,
                android.R.layout.simple_list_item_1, requiredValues);
        requiredList.setAdapter(requiredAdapter);

        ListView recommendedList = (ListView) findViewById(R.id.recommendedListView);
        ArrayAdapter<Equipment> recommendedAdapter = new ArrayAdapter<Equipment>(this,
                android.R.layout.simple_list_item_1, recommendedValues);
        recommendedList.setAdapter(recommendedAdapter);
    }

    public List<Integer> getSelectedCriteria() {
        return null;

    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
//    public void onClick(View view) {
//        @SuppressWarnings("unchecked")
//        ArrayAdapter<Equipment> adapter = (ArrayAdapter<Equipment>) getListAdapter();
//        Equipment equipment = null;
//        switch (view.getId()) {
//            case R.id.add:
//                String[] equipmentList = new String[]{"Cool", "Very nice", "Hate it"};
//                int nextInt = new Random().nextInt(3);
//                // Save the new equipment to the database
//                equipment = datasource.createEquipment(equipmentList[nextInt], "test");
//                adapter.add(equipment);
//                break;
//            case R.id.delete:
//                if (getListAdapter().getCount() > 0) {
//                    equipment = (Equipment) getListAdapter().getItem(0);
//                    datasource.deleteEquipment(equipment);
//                    adapter.remove(equipment);
//                }
//                break;
//        }
//        adapter.notifyDataSetChanged();
//    }

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

//        ListView recommendedList = (ListView) findViewById(R.id.recommendedListView);
//        ArrayAdapter<CharSequence> recommendedAdapter = ArrayAdapter.createFromResource(this,
//                R.array.recommended_array, android.R.layout.simple_list_item_1);
//        recommendedList.setAdapter(recommendedAdapter);

//        ListView requiredList = (ListView) findViewById(R.id.requiredListView);
//        ArrayAdapter<CharSequence> requiredAdapter = ArrayAdapter.createFromResource(this,
//                R.array.required_array, android.R.layout.simple_list_item_1);
//        requiredList.setAdapter(requiredAdapter);
}

