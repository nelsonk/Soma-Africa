package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnGroupExpandListener;


import com.neliek.database.provider.MyDBContentProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nelson on 24/03/2015.
 */
public class Progress extends Activity {


    HashMap<String, List<String>> reg_category;
    HashMap<String, List<String>> sch_details = new HashMap<String, List<String>>();
    ExpandableListView exp_list;
    List<String> sch_list;
    MyExpandableListAdapter adapter;
    String username;
    String[] selectionArgs = null;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_main);
        exp_list = (ExpandableListView)findViewById(R.id.expandableListView);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        list();
        sch_list = new ArrayList<String>(sch_details.keySet());
        adapter = new MyExpandableListAdapter(this, sch_details, sch_list);
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

    private void expandablelist() {


        setContentView(R.layout.progress_main);



    }

    private void list() {

        selectionArgs = new String[]{ username };

        String[] projection = { DbClass.REGISTRATION_SCHNAME,
                DbClass.REGISTRATION_STATUS, DbClass.COURSE_COMBINATION, DbClass.REGISTRATION_COURSETYPE, DbClass.REGISTRATION_REF_ID, DbClass.REGISTRATION_USERNAME, DbClass.REGISTRATION_ID };
        Cursor cursor = getContentResolver().query(MyDBContentProvider.REGISTRATION_CONTENT_URI, projection, DbClass.REGISTRATION_USERNAME + " = ?", selectionArgs,null);

        if(cursor == null){
            Toast.makeText(getBaseContext(), "Error occurred during query", Toast.LENGTH_LONG).show();
        }else if(cursor.getCount() <1){
            Toast.makeText(getBaseContext(), "No applications to any school yet,Please go back and apply", Toast.LENGTH_LONG).show();
        }else {

            if (cursor.moveToFirst()) {
                do {
                    String sch = cursor.getString(cursor.getColumnIndexOrThrow(DbClass.REGISTRATION_SCHNAME));
                    String comb = cursor.getString(cursor.getColumnIndexOrThrow(DbClass.REGISTRATION_COMBINATION));
                    String status = cursor.getString(cursor.getColumnIndexOrThrow(DbClass.REGISTRATION_STATUS));


                    List<String> schs = new ArrayList<String>();
                    schs.add(comb);
                    schs.add(status);

                    sch_details.put(sch, schs);

                } while (cursor.moveToNext());
            }
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putString("username", username);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("username")) {
            username = (pref.getString("username",""));
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("username", username);
            editor.commit();



    }


    @Override
    protected void onRestart() {
        super.onRestart();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("username")) {
            username = (pref.getString("username",""));
        }
    }
}
