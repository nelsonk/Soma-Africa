package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.database.Cursor;

import com.neliek.database.provider.MyDBContentProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nelson on 25/03/2015.
 */
public class ExpandableListData extends Activity {



    public static HashMap<String, List<String>> getInfo() {
     HashMap<String, List<String>> sch_details = new HashMap<String, List<String>>();
        List<String> lubiri = new ArrayList<String>();
        lubiri.add("MEG");
        lubiri.add("BCM");
        List<String> macos = new ArrayList<String>();
        macos.add("HEG");
        macos.add("PCM");
        List<String> mengo = new ArrayList<String>();
        mengo.add("LEG");
        mengo.add("BCP");
        List<String> kitende = new ArrayList<String>();
        kitende.add("MEG");
        kitende.add("BCM");

        sch_details.put("Lubiri SS", lubiri);
        sch_details.put("Makerere College", macos);
        sch_details.put("Mengo SS", mengo);
        sch_details.put("ST Marys Kitende", kitende);

        return sch_details;
}

}
