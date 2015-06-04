package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.neliek.database.provider.MyDBContentProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 29/03/2015.
 */
public class Grade extends Activity implements AdapterView.OnItemClickListener {
    ListView grade_list;
    String type,sch,combinations,cutoffs,username,comb,grade1;
    private MListAdapter  myadapter;
    List<MListModel> myList=new ArrayList<MListModel>();
    private Boolean isSavedInstanceState= false;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade);
        grade_list = (ListView)findViewById(R.id.grade_list);
        Intent intent = getIntent();
        comb = intent.getStringExtra("comb");
        grade1 = intent.getStringExtra("grade1");
        list_grade();
        grade_list.setOnItemClickListener(this);
    }

    private void list_grade() {

        String[] projection = { DbClass.COURSE_ID,
                DbClass.COURSE_SCHNAME, DbClass.COURSE_COURSETYPE, DbClass.COURSE_COMBINATION, DbClass.COURSE_CUTOFF };
        Cursor cursor = getContentResolver().query(MyDBContentProvider.COURSE_CONTENT_URI, projection, null, null,
                null);
        if(cursor == null){
            Toast.makeText(getBaseContext(), "Error occurred during query", Toast.LENGTH_LONG).show();
        }else if(cursor.getCount() <1){
            Toast.makeText(getBaseContext(), "No schools available", Toast.LENGTH_LONG).show();
        }else {

            if (cursor.moveToFirst()) {

                do {


                    combinations = cursor.getString(cursor
                            .getColumnIndexOrThrow(DbClass.COURSE_COMBINATION));
                    if (combinations.compareToIgnoreCase(comb) == 0) {

                        type = cursor.getString(cursor
                                .getColumnIndexOrThrow(DbClass.COURSE_COURSETYPE));

                        sch = cursor.getString(cursor
                                .getColumnIndexOrThrow(DbClass.COURSE_SCHNAME));

                        cutoffs = cursor.getString(cursor
                                .getColumnIndexOrThrow(DbClass.COURSE_CUTOFF));
                        if(cutoffs.charAt(0)>= grade1.charAt(0) && cutoffs.charAt(2)>= grade1.charAt(2) && cutoffs.charAt(4)>= grade1.charAt(4) ) {

                            myList.add(new MListModel(sch, combinations, cutoffs));
                            myadapter = new MListAdapter(myList, this);

                            grade_list.setAdapter(myadapter);
                        }else{
                            Toast.makeText(getBaseContext(), "The cutoffs are higher", Toast.LENGTH_LONG).show();
                        }
                    }


                } while (cursor.moveToNext());

            }
            if (!cursor.isClosed()) {
                cursor.close();
            }

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MListModel myLists =myadapter.getItem(position);
        String schools= myLists.getSchool();
        String combs=myLists.getRank();
        String cuts =myLists.getStatus();
        String types = "null";
        Intent i = new Intent(Grade.this, Apply.class);
        i.putExtra("comb", combs);
        i.putExtra("cut", cuts);
        // i.putExtra("username", username);
        i.putExtra("school", schools);
        i.putExtra("type", types);
        startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(
                R.menu.home, menu );
        setMenuBackground();
        return super.onCreateOptionsMenu(menu);
    }

    private void setMenuBackground() {

        getLayoutInflater().setFactory( new LayoutInflater.Factory() {

            @Override
            public View onCreateView ( String name, Context context, AttributeSet attrs ) {

                if ( name.equalsIgnoreCase( "com.android.internal.view.menu.IconMenuItemView" ) ) {

                    try { // Ask our inflater to create the view
                        LayoutInflater f = getLayoutInflater();
                        final View view = f.createView( name, null, attrs );
                                        /*
                                         * The background gets refreshed each time a new item is added the options menu.
                                         * So each time Android applies the default background we need to set our own
                                         * background. This is done using a thread giving the background change as runnable
                                         * object
                                         */
                        new Handler().post( new Runnable() {
                            public void run () {
                                view.setBackgroundResource( R.drawable.menu_bg);
                            }
                        } );
                        return view;
                    }
                    catch ( InflateException e ) {}
                    catch ( ClassNotFoundException e ) {}
                }
                return null;
            }
        });
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        if (item.getItemId() == R.id.home) {
            Intent i = new Intent(Grade.this, MainActivity.class);
            startActivity(i);
        }

        return super.onMenuItemSelected(featureId, item);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putString("grade", grade1);
        editor.putString("comb", comb);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("comb")) {
            grade1 = (pref.getString("grade",""));
            comb = (pref.getString("comb",""));

        }
    }



    @Override
    protected void onStop() {
        super.onStop();

            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("grade", grade1);
            editor.putString("comb", comb);
            editor.commit();



    }


    @Override
    protected void onRestart() {
        super.onRestart();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("comb")) {
            grade1 = (pref.getString("grade",""));
            comb = (pref.getString("comb",""));

        }
    }
}
