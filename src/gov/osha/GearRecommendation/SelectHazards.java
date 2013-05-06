package gov.osha.GearRecommendation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class SelectHazards extends Activity {
    private ListView mainHazListView;
    private ArrayAdapter<mItems> listAdapter;
    ArrayList<String> checked = new ArrayList<String>();
    private DBAdapter datasource;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selecthazardslayout);

        datasource = new DBAdapter(SelectHazards.this);
        datasource.open();

//        List<Criteria> hazards = datasource.getCriteria("hazards");

        // Use the SimpleCursorAdapter to show the
        // elements in a ListView
//        mainHazListView = (ListView) findViewById(R.id.mainHazListView);
//        ArrayAdapter<Criteria> hazardsAdapter = new ArrayAdapter<Criteria>(this,
//                android.R.layout.simple_list_item_1, hazards);
//        mainHazListView.setAdapter(hazardsAdapter);

// Find the ListView resource.
        mainHazListView = (ListView) findViewById(R.id.mainHazListView);

// When item is tapped, toggle checked properties of CheckBox and
// Planet.
        mainHazListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                        mItems hazard = listAdapter.getItem(position);
                        hazard.toggleChecked();
                        SelectViewHolder viewHolder = (SelectViewHolder) item.getTag();
                        viewHolder.getCheckBox().setChecked(hazard.isChecked());
                        System.out.println(hazard.getName() + " is checked: " + hazard.isChecked());
                    }
                });

// Create and populate hazards.
//        items = (mItems[]) getLastNonConfigurationInstance();
//
        ArrayList<mItems> hazardList = new ArrayList<mItems>();
        List<Criteria> hazards = datasource.getCriteria("hazards");

        Iterator<Criteria> iterator = hazards.iterator();
        while (iterator.hasNext()) {
            hazardList.add(new mItems(iterator.next()));
        }

// Set our custom array adapter as the ListView's adapter.
        listAdapter = new SelectArrayAdapter(this, hazardList);
        mainHazListView.setAdapter(listAdapter);
    }

    /**
     * Holds hazard data.
     */
    private static class mItems {
        private String name = "";
        private boolean checked = false;
        private Integer id;

        HashSet<Integer> selectedCriteria = GearRecommendation.selectedCriteria;

        public mItems() {
        }

        public mItems(Criteria hazard) {
            this.name = hazard.getCriteria();
            this.id = new Integer(hazard.getId());
        }

        public mItems(Criteria hazard, boolean checked) {
            this.name = hazard.getCriteria();
            this.checked = checked;
            this.id = new Integer(hazard.getId());
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;

            if (this.isChecked()) {
                selectedCriteria.add(this.getId());
            } else {
                selectedCriteria.remove(this.getId());
            }
        }

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String toString() {
            return name;
        }

        public void toggleChecked() {
            setChecked(!checked);
        }
    }

    /**
     * Holds child views for one row.
     */
    private static class SelectViewHolder {
        private CheckBox checkBox;
        private TextView textView;

        public SelectViewHolder() {
        }

        public SelectViewHolder(TextView textView, CheckBox checkBox) {
            this.checkBox = checkBox;
            this.textView = textView;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }

    /**
     * Custom adapter for displaying an array of Hazard objects.
     */
    private static class SelectArrayAdapter extends ArrayAdapter<mItems> {
        private LayoutInflater inflater;

        public SelectArrayAdapter(Context context, List<mItems> hazardList) {
            super(context, R.layout.hazselectrow, R.id.rowTextView, hazardList);
// Cache the LayoutInflate to avoid asking for a new one each time.
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
// Hazard to display
            mItems hazard = (mItems) this.getItem(position);

// The child views in each row.
            CheckBox checkBox;
            TextView textView;

// Create a new row view
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.hazselectrow, null);

// Find the child views.
                textView = (TextView) convertView.findViewById(R.id.rowTextView);
                checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);
//                checkBox.setText("test");
// Optimization: Tag the row with it's child views, so we don't
// have to
// call findViewById() later when we reuse the row.
                convertView.setTag(new SelectViewHolder(textView, checkBox));
// If CheckBox is toggled, update the hazard it is tagged with.
                checkBox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        mItems hazard = (mItems) cb.getTag();
                        hazard.setChecked(cb.isChecked());
                        System.out.println(hazard.getName() + " is checkedx: " + hazard.isChecked());
                    }
                });
            }
// Reuse existing row view
            else {
// Because we use a ViewHolder, we avoid having to call
// findViewById().
                SelectViewHolder viewHolder = (SelectViewHolder) convertView
                        .getTag();
                checkBox = viewHolder.getCheckBox();
                textView = viewHolder.getTextView();
            }

// Tag the CheckBox with the Hazard it is displaying, so that we can
// access the hazard in onClick() when the CheckBox is toggled.
            checkBox.setTag(hazard);
// Display hazard data
            checkBox.setChecked(hazard.isChecked());
            textView.setText(hazard.getName());
            return convertView;
        }
    }
}

