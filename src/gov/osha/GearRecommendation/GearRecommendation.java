package gov.osha.GearRecommendation;

import android.app.Application;
import android.content.Context;

import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: bcarter
 * Date: 5/6/13
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class GearRecommendation extends Application {
    private static Context context;

    static HashSet<Integer> selectedCriteria = new HashSet<Integer>();

    public void onCreate(){
        super.onCreate();
        GearRecommendation.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return GearRecommendation.context;
    }


}
