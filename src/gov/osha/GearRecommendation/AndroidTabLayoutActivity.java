package gov.osha.GearRecommendation;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;

/**
 * Created with IntelliJ IDEA.
 * User: bcarter
 * Date: 4/18/13
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class AndroidTabLayoutActivity extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);

        TabHost tabHost = getTabHost();

//        LayoutInflater.from(this).inflate(R.layout.tabs1, tabHost.getTabContentView(), true);
//
        tabHost.addTab(tabHost.newTabSpec("Criteria")
                .setIndicator("Criteria")
                .setContent(new Intent(this, SelectCriteria.class)));
        tabHost.addTab(tabHost.newTabSpec("Hazards")
                .setIndicator("Hazards")
                .setContent(new Intent(this, SelectHazzrds.class)));
        tabHost.addTab(tabHost.newTabSpec("Equipment")
                .setIndicator("Equipment")
                .setContent(new Intent(this, DisplayEquipment.class)));
    }
}