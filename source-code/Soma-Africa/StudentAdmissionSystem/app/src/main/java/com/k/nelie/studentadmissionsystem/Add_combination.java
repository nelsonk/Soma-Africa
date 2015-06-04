package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.neliek.database.provider.MyDBContentProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 27/03/2015.
 */
public class Add_combination extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText comb, cutoff;
    AutoCompleteTextView schname, coursetype;
    Button add,back;
    String username;
    Spinner types;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_combination);
        schname = (AutoCompleteTextView)findViewById(R.id.etadcombschname);
        coursetype = (AutoCompleteTextView)findViewById(R.id.etadtype);
        comb = (EditText)findViewById(R.id.etadcomb);
        cutoff = (EditText)findViewById(R.id.etadcutoff);
        back = (Button)findViewById(R.id.bback);

        schlist();
        coursetypes();
        add = (Button)findViewById(R.id.baddcomb);
        back.setOnClickListener(this);
        add.setOnClickListener(this);



    }

    private void coursetypes() {

        List<String> list = new ArrayList<String>();
        list.add("Sciences");
        list.add("Arts");

        ArrayAdapter adapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, list);
        adapter.setNotifyOnChange(true); // This is so I don't have to manually sync whenever changed

        coursetype.setThreshold(1);//will start working from first character
        coursetype.setAdapter(adapter);

    }

    private void schlist() {

        String[] projection = { DbClass.COLUMN_ID,
                DbClass.COLUMN_NAME, DbClass.COLUMN_STATUS, DbClass.COLUMN_RANK };
        Cursor cursor = getContentResolver().query(MyDBContentProvider.CONTENT_URI, projection, null, null,
                null);


        String array[] = new String[cursor.getCount()];
        int i = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            String name = cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_NAME));


            array[i]= name;
            ArrayAdapter adapter = new ArrayAdapter
                    (this, android.R.layout.simple_list_item_1, array);
            adapter.setNotifyOnChange(true); // This is so I don't have to manually sync whenever changed

            schname.setThreshold(1);//will start working from first character
            schname.setAdapter(adapter);

            i++;
            cursor.moveToNext();
        }


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bback) {
            Intent i = new Intent(Add_combination.this, MainActivity.class);
            startActivity(i);

        }else {
            Uri newUri = null;

            // Defines an object to contain the new values to insert
            ContentValues newValues = new ContentValues();

// Sets the values of each column and inserts the word.
            newValues.put(DbClass.COURSE_SCHNAME, schname.getText().toString());
            newValues.put(DbClass.COURSE_COURSETYPE, coursetype.getText().toString());
            newValues.put(DbClass.COURSE_COMBINATION, comb.getText().toString());
            newValues.put(DbClass.COURSE_CUTOFF, cutoff.getText().toString());


            newUri = getContentResolver().insert(
                    MyDBContentProvider.COURSE_CONTENT_URI,   // the user dictionary content URI
                    newValues                          // the values to insert
            );


            Toast.makeText(getBaseContext(), newUri.toString(), Toast.LENGTH_LONG).show();
            Intent i = new Intent(Add_combination.this, MainActivity.class);
            // i.putExtra("username", username);
            startActivity(i);

        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,
                "On Button Click : " +
                        "\n" + String.valueOf(types.getSelectedItem()) ,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
            Intent i = new Intent(Add_combination.this, MainActivity.class);
            startActivity(i);
        }

        return super.onMenuItemSelected(featureId, item);
    }



    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putString("school", schname.getText().toString());
        editor.putString("type", coursetype.getText().toString());
        editor.putString("comb", comb.getText().toString());
        editor.putString("cut", cutoff.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        schname.setText(pref.getString("school", ""));
        coursetype.setText(pref.getString("type", ""));
        comb.setText(pref.getString("comb", ""));
        cutoff.setText(pref.getString("cut", ""));
    }



    @Override
    protected void onStop() {
        super.onStop();

            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("school", schname.getText().toString());
            editor.putString("type", coursetype.getText().toString());
            editor.putString("comb", comb.getText().toString());
            editor.putString("cut", cutoff.getText().toString());
            editor.commit();



    }


    @Override
    protected void onRestart() {
        super.onRestart();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        schname.setText(pref.getString("school", ""));
        coursetype.setText(pref.getString("type", ""));
        comb.setText(pref.getString("comb", ""));
        cutoff.setText(pref.getString("cut", ""));
    }
}
