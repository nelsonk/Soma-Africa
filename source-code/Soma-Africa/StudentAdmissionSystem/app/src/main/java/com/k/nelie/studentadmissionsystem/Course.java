package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.neliek.database.provider.MyDBContentProvider;

/**
 * Created by Nelie K on 2/28/2015.
 */
public class Course extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    RadioButton sciences, arts;
    Button classes, contacts;
    String sch, username;
    SharedPreferences pref;
    int sci=0;
    int art =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classes);
        sciences = (RadioButton)findViewById(R.id.rbsciences);
        arts = (RadioButton)findViewById(R.id.rbarts);
        classes = (Button)findViewById(R.id.bclasses);

        Intent intent = getIntent();
        sch = intent.getStringExtra("school");
        sciences.setOnCheckedChangeListener(this);
        arts.setOnCheckedChangeListener(this);
        classes.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

                if (sciences.isChecked()) {
                    Toast.makeText(getBaseContext(), "sciences selected", Toast.LENGTH_LONG).show();
                    String checkId = "sciences";
                    String school = sch;
                    Intent i = new Intent(Course.this, Combinations.class);
                    i.putExtra("checkId", checkId);
                    i.putExtra("school", school);
                   startActivity(i);
                }
                if (arts.isChecked()) {
                    Toast.makeText(getBaseContext(), "Arts selected", Toast.LENGTH_LONG).show();
                    String checkId = "arts";
                    String school = sch;
                    Intent i = new Intent(Course.this, Combinations.class);
                    i.putExtra("checkId", checkId);
                    i.putExtra("school", school);
                    startActivity(i);
                }
                if (!sciences.isChecked() && !arts.isChecked()) {

                    Toast.makeText(getBaseContext(), "Please 1st check either sciences or arts", Toast.LENGTH_LONG).show();

                }



    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
  switch (buttonView.getId()) {
      case R.id.rbsciences:
          if (sciences.isChecked()) {
              sci =1;
              art = 0;
              arts.setChecked(false);

          }
          break;
      case R.id.rbarts:
          if (arts.isChecked()) {
             sci = 0;
              art = 1;
              sciences.setChecked(false);
          }
          break;
  }
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putString("school", sch);
        editor.putInt("sciences", sci);
        editor.putInt("arts", art);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("school")) {
            sch=pref.getString("school", "");
            sci=pref.getInt("sciences", 0);
            art=pref.getInt("arts",0);
            if(sci==1){
                sciences.setChecked(true);
            }else if(sci==0){
                sciences.setChecked(false);
            }
            if(art==1){
                arts.setChecked(true);
            }else if(art==0){
                arts.setChecked(false);
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("school", sch);
            editor.putInt("sciences", sci);
            editor.putInt("arts", art);
            editor.commit();


    }


    @Override
    protected void onRestart() {
        super.onRestart();

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("school")) {
            sch=pref.getString("school", "");
            sci=pref.getInt("sciences", 0);
            art=pref.getInt("arts",0);
            if(sci==1){
                sciences.setChecked(true);
            }else if(sci==0){
                sciences.setChecked(false);
            }
            if(art==1){
                arts.setChecked(true);
            }else if(art==0){
                arts.setChecked(false);
            }
        }

    }
}
