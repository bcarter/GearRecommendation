package gov.osha.GearRecommendation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SelectCriteria extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectcriterialayout);

        Spinner locationSpinner = (Spinner) findViewById(R.id.locationSpinner);

        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this,
                R.array.location_array, android.R.layout.simple_spinner_item);

        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        locationSpinner.setAdapter(locationAdapter);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);

                System.out.println("location selected: " + item.toString());
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner accessSpinner = (Spinner) findViewById(R.id.accessSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> accessAdapter = ArrayAdapter.createFromResource(this,
                R.array.access_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        accessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        accessSpinner.setAdapter(accessAdapter);

        Spinner workPreformedSpinner = (Spinner) findViewById(R.id.workPreformedSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> workPreformedAdapter = ArrayAdapter.createFromResource(this,
                R.array.work_preformed_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        workPreformedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        workPreformedSpinner.setAdapter(workPreformedAdapter);
    }
}
