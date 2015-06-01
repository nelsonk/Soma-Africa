package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.neliek.database.provider.MyDBContentProvider;

/**
 * Created by nelson on 27/03/2015.
 */
public class Add_sch extends Activity implements View.OnClickListener {
    EditText schname, rank, status;
    Button add;
    String username;
    private Boolean isSavedInstanceState= false;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsch);
        schname = (EditText)findViewById(R.id.etadschname);
        rank = (EditText)findViewById(R.id.etadrank);
        status = (EditText)findViewById(R.id.etadstatus);
        add = (Button)findViewById(R.id.baddsch);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Defines a new Uri object that receives the result of the insertion
        Uri newUri;

// Defines an object to contain the new values to insert
        ContentValues newValues = new ContentValues();

// Sets the values of each column and inserts the word.
        newValues.put(DbClass.COLUMN_NAME, schname.getText().toString());
        newValues.put(DbClass.COLUMN_RANK, rank.getText().toString());
        newValues.put(DbClass.COLUMN_STATUS, status.getText().toString());


        newUri = getContentResolver().insert(
                MyDBContentProvider.CONTENT_URI,   // the user dictionary content URI
                newValues                          // the values to insert
        );
        Toast.makeText(getBaseContext(), newUri.toString(), Toast.LENGTH_LONG).show();
        Intent i = new Intent(Add_sch.this, MainActivity.class);
        startActivity(i);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putString("school", schname.getText().toString());
        editor.putString("rank", rank.getText().toString());
        editor.putString("status", status.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        schname.setText(pref.getString("school", ""));
        rank.setText(pref.getString("rank", ""));
        status.setText(pref.getString("status", ""));
    }



    @Override
    protected void onStop() {
        super.onStop();

            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("school", schname.getText().toString());
            editor.putString("rank", rank.getText().toString());
            editor.putString("status", status.getText().toString());
            editor.commit();



    }


    @Override
    protected void onRestart() {
        super.onRestart();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        schname.setText(pref.getString("school", ""));
        rank.setText(pref.getString("rank", ""));
        status.setText(pref.getString("status", ""));
    }
}
