package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.neliek.database.provider.MyDBContentProvider;

/**
 * Created by nelson on 29/03/2015.
 */
public class Splash_screan extends Activity {


    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screan);
        schools();
        rootlogins();
        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(Splash_screan.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

    private void rootlogins() {
        // Defines a new Uri object that receives the result of the insertion
        Uri newUri;

// Defines an object to contain the new values to insert
        ContentValues newValues = new ContentValues();

// Sets the values of each column and inserts the word.
        newValues.put(DbClass.LOGIN_NAME, "nelson");
        newValues.put(DbClass.LOGIN_PASSWORD, "nelson");


        newUri = getContentResolver().insert(
                MyDBContentProvider.LOGIN_CONTENT_URI,   // the user dictionary content URI
                newValues                          // the values to insert
        );

    }

    private void schools() {

        String[] projection = { DbClass.COURSE_ID,
                DbClass.COURSE_SCHNAME, DbClass.COURSE_COURSETYPE, DbClass.COURSE_COMBINATION, DbClass.COURSE_CUTOFF };
        Cursor cursor = getContentResolver().query(MyDBContentProvider.COURSE_CONTENT_URI, projection, null, null,
                null);
        if(cursor == null){
            Toast.makeText(getBaseContext(), "Error occurred during query", Toast.LENGTH_LONG).show();
        }else if(cursor.getCount() <1) {
            Toast.makeText(getBaseContext(), "No courses were in db", Toast.LENGTH_LONG).show();

            Uri newUri = null;
            String[] school = {"Gombe Secondary School", "Gombe Secondary School", "Gombe Secondary School", "Gombe Secondary School", "St Marry's College Kitende", "St Marry's College Kitende", "St Marry's College Kitende", "St Marry's College Kitende", "Makerere College School", "Makerere College School", "Makerere College School", "Makerere College School", "Lubiri Secondary School", "Lubiri Secondary School", "Lubiri Secondary School", "Lubiri Secondary School"};
            String[] type = {"Sciences", "Sciences", "Arts", "Arts", "Sciences", "Sciences", "Arts", "Arts", "Sciences", "Sciences", "Arts", "Arts", "Sciences", "Sciences", "Arts", "Arts"};
            String[] combination = {"PEM", "BCM", "HED", "HEG", "MEG", "BCM", "DEG", "LEG", "PCM", "BCM", "LAD", "HEG", "PEM", "PCB", "HDG", "HEG"};
            String[] cutoff = {"3,4,4", "3,3,3", "5,4,4", "5,3,3", "3,3,3", "3,3,3", "5,3,4", "5,5,5", "4,3,3", "2,3,3", "6,6,5", "6,5,5", "3,2,4", "3,3,3", "4,4,6", "4,3,5"};
            for (int i = 0; i < 16; i++) {
 // Defines an object to contain the new values to insert
                ContentValues newValues = new ContentValues();

// Sets the values of each column and inserts the word.
                newValues.put(DbClass.COURSE_SCHNAME, school[i]);
                newValues.put(DbClass.COURSE_COURSETYPE, type[i]);
                newValues.put(DbClass.COURSE_COMBINATION, combination[i]);
                newValues.put(DbClass.COURSE_CUTOFF, cutoff[i]);


                newUri = getContentResolver().insert(
                        MyDBContentProvider.COURSE_CONTENT_URI,   // the user dictionary content URI
                        newValues                          // the values to insert
                );
            }

            Toast.makeText(getBaseContext(), newUri.toString(), Toast.LENGTH_LONG).show();

        }

    }

}
