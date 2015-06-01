package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by nelson on 29/03/2015.
 */
public class Apply extends Activity implements View.OnClickListener {
    TextView detail;
    Button apply;
    String comb,cut,username,school,type;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply);
        detail = (TextView)findViewById(R.id.tvdetail);
        apply = (Button)findViewById(R.id.bapply);
        Intent intent = getIntent();
        comb = intent.getStringExtra("comb");
        cut = intent.getStringExtra("cut");
        school = intent.getStringExtra("school");
        type = intent.getStringExtra("type");

        detail.setText("Applying for" + " " + comb + " " + "at" + " " + school + " " + "cutoff" + " " + cut);
        apply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Confirmation");
        alertDialogBuilder.setMessage("Please signup and signin to apply");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(Apply.this, Welcome.class);
                i.putExtra("comb", comb);
                i.putExtra("cut", cut);
                i.putExtra("school", school);
                i.putExtra("type", type);

                startActivity(i);
            }
        });
        alertDialogBuilder.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.show();
    }


    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putString("school", school);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("school")) {
            school = pref.getString("school","");

        }
    }


    @Override
    protected void onStop() {
        super.onStop();

            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("school", school);
            editor.commit();



    }


    @Override
    protected void onRestart() {
        super.onRestart();

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("school")) {
            school = pref.getString("school","");

        }
    }
}
