package gov.osha.GearRecommendation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SelectCriteria extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectcriterialayout);

        Spinner locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this,
                R.array.location_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        locationSpinner.setAdapter(locationAdapter);

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
