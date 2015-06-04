package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;

import com.neliek.database.provider.MyDBContentProvider;

/**
 * Created by Nelie K on 3/17/2015.
 */
public class SignUp extends Activity implements View.OnClickListener {

    Button signup, back;
    EditText fname,sname,gender,country,city,age,tel,email;
    SharedPreferences pref;
    String comb,cut,type,school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        fname = (EditText)findViewById(R.id.etsfirstname);
        sname = (EditText)findViewById(R.id.etssurname);
        gender = (EditText)findViewById(R.id.etsgender);
        country = (EditText)findViewById(R.id.etscountry);
        city = (EditText)findViewById(R.id.etscity);
        age = (EditText)findViewById(R.id.etsage);
        tel = (EditText)findViewById(R.id.etstel);
        email = (EditText)findViewById(R.id.etsemail);
        signup = (Button)findViewById(R.id.bssubmit);
        back = (Button)findViewById(R.id.bback);


        Intent intent = getIntent();
        if(intent.hasExtra("comb")) {
            comb = intent.getStringExtra("comb");
            cut = intent.getStringExtra("cut");
            school = intent.getStringExtra("school");
            type = intent.getStringExtra("type");
        }
        signup.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bback) {
            Intent i = new Intent(SignUp.this, Welcome.class);
            i.putExtra("comb", comb);
            i.putExtra("cut", cut);
            i.putExtra("school", school);
            i.putExtra("type", type);
            startActivity(i);

        }else {

            // Defines a new Uri object that receives the result of the insertion
            Uri newUri;

// Defines an object to contain the new values to insert
            ContentValues newValues = new ContentValues();

// Sets the values of each column and inserts the word.
            newValues.put(DbClass.SIGNUP_FNAME, fname.getText().toString());
            newValues.put(DbClass.SIGNUP_LNAME, sname.getText().toString());
            newValues.put(DbClass.SIGNUP_GENDER, gender.getText().toString());
            newValues.put(DbClass.SIGNUP_COUNTRY, country.getText().toString());
            newValues.put(DbClass.SIGNUP_CITY, city.getText().toString());
            newValues.put(DbClass.SIGNUP_AGE, age.getText().toString());
            newValues.put(DbClass.SIGNUP_TEL, tel.getText().toString());
            newValues.put(DbClass.SIGNUP_EMAIL, email.getText().toString());


            newUri = getContentResolver().insert(
                    MyDBContentProvider.SIGNUP_CONTENT_URI,   // the user dictionary content URI
                    newValues                          // the values to insert
            );


            Intent i = new Intent(SignUp.this, LoginDetails.class);
            i.putExtra("comb", comb);
            i.putExtra("cut", cut);
            i.putExtra("school", school);
            i.putExtra("type", type);
            startActivity(i);
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
            Intent i = new Intent(SignUp.this, MainActivity.class);
            startActivity(i);
        }

        return super.onMenuItemSelected(featureId, item);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = pref.edit();
        if (pref.contains("fname")) {
            editor.clear();
            editor.putString("fname", fname.getText().toString());
            editor.putString("sname", sname.getText().toString());
            editor.putString("nationality", gender.getText().toString());
            editor.putString("country", country.getText().toString());
            editor.putString("email", email.getText().toString());
            editor.putString("city", city.getText().toString());
            editor.putString("age", age.getText().toString());
            editor.putString("tel", tel.getText().toString());
            editor.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        if (pref.contains("fname")) {
            fname.setText(pref.getString("fname", ""));
            sname.setText(pref.getString("sname", ""));
            gender.setText(pref.getString("nationality", ""));
            country.setText(pref.getString("country", ""));
            city.setText(pref.getString("city", ""));
            age.setText(pref.getString("age", ""));
            tel.setText(pref.getString("tel", ""));
            email.setText(pref.getString("email", ""));
        }
    }



    @Override
    protected void onStop() {
        super.onStop();

            SharedPreferences.Editor editor = pref.edit();
        if (pref.contains("fname")) {
            editor.clear();
            editor.putString("fname", fname.getText().toString());
            editor.putString("sname", sname.getText().toString());
            editor.putString("nationality", gender.getText().toString());
            editor.putString("country", country.getText().toString());
            editor.putString("email", email.getText().toString());
            editor.putString("city", city.getText().toString());
            editor.putString("age", age.getText().toString());
            editor.putString("tel", tel.getText().toString());
            editor.commit();
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("fname")) {

            fname.setText(pref.getString("fname", ""));
            sname.setText(pref.getString("sname", ""));
            gender.setText(pref.getString("nationality", ""));
            country.setText(pref.getString("country", ""));
            city.setText(pref.getString("city", ""));
            age.setText(pref.getString("age", ""));
            tel.setText(pref.getString("tel", ""));
            email.setText(pref.getString("email", ""));
        }
    }
}
