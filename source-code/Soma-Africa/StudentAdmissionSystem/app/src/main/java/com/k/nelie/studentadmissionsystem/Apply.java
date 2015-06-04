package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by nelson on 29/03/2015.
 */
public class Apply extends Activity implements View.OnClickListener {
    TextView detail;
    Button apply, back;
    String comb,cut,username,school,type;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply);
        detail = (TextView)findViewById(R.id.tvdetail);
        apply = (Button)findViewById(R.id.bapply);
        back = (Button)findViewById(R.id.bback);

        Intent intent = getIntent();
        comb = intent.getStringExtra("comb");
        cut = intent.getStringExtra("cut");
        school = intent.getStringExtra("school");
        type = intent.getStringExtra("type");

        detail.setText("Applying for" + " " + comb + " " + "at" + " " + school + " " + "cutoff" + " " + cut);
        apply.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bback) {
            Intent i = new Intent(Apply.this, Combinations.class);
            i.putExtra("checkId", type);
            i.putExtra("school", school);
            startActivity(i);

        }else {

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
            Intent i = new Intent(Apply.this, MainActivity.class);
            startActivity(i);
        }

        return super.onMenuItemSelected(featureId, item);
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
