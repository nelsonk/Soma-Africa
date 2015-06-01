package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by nelson on 29/03/2015.
 */
public class Your_grades extends Activity implements View.OnClickListener {
    EditText sub1,grade1;
    Button grade;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_grades);
        sub1 = (EditText)findViewById(R.id.etsub1);

        grade1 = (EditText)findViewById(R.id.etgrade1);

        grade = (Button)findViewById(R.id.bgrade);
        grade.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Your_grades.this, Grade.class);
        i.putExtra("comb", sub1.getText().toString());
        i.putExtra("grade1", grade1.getText().toString());
        startActivity(i);

    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putString("grade", grade1.getText().toString());
        editor.putString("sub", sub1.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("grade")) {
            grade1.setText(pref.getString("grade",""));
            sub1.setText(pref.getString("sub",""));

        }
    }




    @Override
    protected void onStop() {
        super.onStop();

            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("grade", grade1.getText().toString());
            editor.putString("sub", sub1.getText().toString());
            editor.commit();


    }


    @Override
    protected void onRestart() {
        super.onRestart();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("grade")) {
            grade1.setText(pref.getString("grade",""));
            sub1.setText(pref.getString("sub",""));

        }
    }
}
