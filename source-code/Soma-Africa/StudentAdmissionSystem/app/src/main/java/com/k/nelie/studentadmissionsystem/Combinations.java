package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.neliek.database.provider.MyDBContentProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nelie K on 2/28/2015.
 */
public class Combinations extends Activity implements AdapterView.OnItemClickListener {
    ListView comb;
    private CombListAdapter  myadapter;
    List<CombListModel> myList=new ArrayList<CombListModel>();
    String checkIds,sch,combinations,cutoffs,username;
    // An array to contain selection arguments
    String[] selectionArgs = null;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combinations);
        comb = (ListView) findViewById(R.id.course_list);


        Bundle extras = getIntent().getExtras();
        checkIds = extras.getString("checkId");
        sch = extras.getString("school");



        if (checkIds.equals("sciences")) {
             scienceslist();

        }else{

            artslist();

        }
        comb.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startService(new Intent(getBaseContext(), MyService.class));
        CombListModel myLists =myadapter.getItem(position);
        String comb= myLists.getComb();
        String cutoff=myLists.getCut();
        Intent i = new Intent(Combinations.this, Apply.class);
        i.putExtra("comb", comb);
        i.putExtra("cut", cutoff);
        i.putExtra("school", sch);
        i.putExtra("type", checkIds);
        startActivity(i);

    }



    private void scienceslist() {
        String[] projection = { DbClass.COURSE_ID,
                DbClass.COURSE_SCHNAME, DbClass.COURSE_COURSETYPE, DbClass.COURSE_COMBINATION, DbClass.COURSE_CUTOFF };
        selectionArgs = new String[]{ sch };

        Cursor cursor = getContentResolver().query(MyDBContentProvider.COURSE_CONTENT_URI, projection, DbClass.COURSE_SCHNAME + " = ?", selectionArgs,
                null);
        if(cursor == null){
            Toast.makeText(getBaseContext(), "Error occurred during query", Toast.LENGTH_LONG).show();
        }else if(cursor.getCount() <1){
            Toast.makeText(getBaseContext(), "user doesnt exits, please signup", Toast.LENGTH_LONG).show();
        }else {

            if (cursor.moveToFirst()) {

                do {
                    // cursor.moveToFirst();
                    String type =  cursor.getString(cursor
                            .getColumnIndexOrThrow(DbClass.COURSE_COURSETYPE));
                    if (type.contentEquals("Sciences")) {
                        combinations = cursor.getString(cursor
                                .getColumnIndexOrThrow(DbClass.COURSE_COMBINATION));
                        cutoffs = cursor.getString(cursor
                                .getColumnIndexOrThrow(DbClass.COURSE_CUTOFF));
                        myList.add(new CombListModel(combinations, cutoffs));

                        myadapter = new CombListAdapter(myList, this);

                        comb.setAdapter(myadapter);
                    }

                } while (cursor.moveToNext());

            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
    }



    private void artslist() {

        String[] projection = { DbClass.COURSE_ID,
                DbClass.COURSE_SCHNAME, DbClass.COURSE_COURSETYPE, DbClass.COURSE_COMBINATION, DbClass.COURSE_CUTOFF };
        selectionArgs = new String[]{ sch };

        Cursor cursor = getContentResolver().query(MyDBContentProvider.COURSE_CONTENT_URI, projection, DbClass.COURSE_SCHNAME + " = ?", selectionArgs,
                null);
        if(cursor == null){
            Toast.makeText(getBaseContext(), "Error occurred during query", Toast.LENGTH_LONG).show();
        }else if(cursor.getCount() <1){
            Toast.makeText(getBaseContext(), "user doesnt exits, please signup", Toast.LENGTH_LONG).show();
        }else {

            if (cursor.moveToFirst()) {

                do {
                    String type =  cursor.getString(cursor
                            .getColumnIndexOrThrow(DbClass.COURSE_COURSETYPE));
                    if (type.contentEquals("Arts")) {

                        // cursor.moveToFirst();
                        combinations = cursor.getString(cursor
                                .getColumnIndexOrThrow(DbClass.COURSE_COMBINATION));
                        cutoffs = cursor.getString(cursor
                                .getColumnIndexOrThrow(DbClass.COURSE_CUTOFF));
                        myList.add(new CombListModel(combinations, cutoffs));

                        myadapter = new CombListAdapter(myList, this);

                        comb.setAdapter(myadapter);
                    }

                } while (cursor.moveToNext());

            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putString("school", sch);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("school")) {
            sch = pref.getString("school","");

        }
    }



    @Override
    protected void onStop() {
        super.onStop();

            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("school", sch);
            editor.commit();



    }


    @Override
    protected void onRestart() {
        super.onRestart();

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("school")) {
            sch = pref.getString("school","");

        }
    }


}
