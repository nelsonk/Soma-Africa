package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nelson on 25/03/2015.
 */
public class MyExpandableList extends Activity {
   HashMap<String, List<String>> reg_category;
    ExpandableListView exp_list;
    List<String> sch_list;
    MyExpandableListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_main);
        exp_list = (ExpandableListView)findViewById(R.id.expandableListView);
        reg_category = ExpandableListData.getInfo();
        sch_list = new ArrayList<String>(reg_category.keySet());
        adapter = new MyExpandableListAdapter(this, reg_category, sch_list);
        exp_list.setAdapter(adapter);

        exp_list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getBaseContext(), sch_list.get(groupPosition) + "is expanded", Toast.LENGTH_LONG).show();
            }
        });

        exp_list.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getBaseContext(), sch_list.get(groupPosition) + "is collapsed", Toast.LENGTH_LONG).show();
            }
        });


        exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Toast.makeText(getBaseContext(), reg_category.get(sch_list.get(groupPosition)).get(childPosition) + "from category" + sch_list.get(groupPosition) + " is selected ", Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }
}
