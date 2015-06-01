package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.neliek.database.provider.MyDBContentProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;


/**
 * Created by nelson on 07/04/2015.
 */
public class Facebooksignup extends Activity {

    private Facebook facebook;
    private static final String APP_ID = "945834355446787";
    private static final String[] PERMISSIONS = new String[] { "publish_stream","read_stream", "offline_access"};
    private AsyncFacebookRunner mAsyncRunner;
    String FILENAME = "android_file";
    private SharedPreferences mPrefs;
    Button signup;
    String first_name, last_name, username,email,gender,city,birthday,country,tel;
    String comb,cut,type,school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        comb = intent.getStringExtra("comb");
        cut = intent.getStringExtra("cut");
        school = intent.getStringExtra("school");
        type = intent.getStringExtra("type");


        facebook = new Facebook(APP_ID);
        mAsyncRunner = new AsyncFacebookRunner(facebook);
        loginToFacebook();
        getProfileInfo();
        //postToWall();
        logoutFromFacebook();
        logindetails();

    }

    private void logindetails() {
        // Defines a new Uri object that receives the result of the insertion
        Uri newUri;

// Defines an object to contain the new values to insert
        ContentValues newValues = new ContentValues();

// Sets the values of each column and inserts the word.
        newValues.put(DbClass.SIGNUP_FNAME, first_name);
        newValues.put(DbClass.SIGNUP_LNAME, last_name);
        newValues.put(DbClass.SIGNUP_GENDER, gender);
        newValues.put(DbClass.SIGNUP_COUNTRY, country);
        newValues.put(DbClass.SIGNUP_CITY, city);
        newValues.put(DbClass.SIGNUP_AGE, birthday);
        newValues.put(DbClass.SIGNUP_TEL, tel);
        newValues.put(DbClass.SIGNUP_EMAIL, email);



        newUri = getContentResolver().insert(
                MyDBContentProvider.SIGNUP_CONTENT_URI,   // the user dictionary content URI
                newValues                          // the values to insert
        );


        Intent i = new Intent(Facebooksignup.this, LoginDetails.class);
        i.putExtra("comb", comb);
        i.putExtra("cut", cut);
        i.putExtra("school", school);
        i.putExtra("type", type);
        startActivity(i);
    }

    private void logoutFromFacebook() {
        mAsyncRunner.logout(this, new AsyncFacebookRunner.RequestListener() {
            @Override
            public void onComplete(String response, Object state) {
                Log.d("Log out from facebook", response);
                if (Boolean.parseBoolean(response)==true){
                    //user sucessfully logged out
                }
            }

            @Override
            public void onIOException(IOException e, Object state) {

            }

            @Override
            public void onFileNotFoundException(FileNotFoundException e, Object state) {

            }

            @Override
            public void onMalformedURLException(MalformedURLException e, Object state) {

            }

            @Override
            public void onFacebookError(FacebookError e, Object state) {

            }
        });
    }

    private void postToWall() {
        //post on user's wall
        facebook.dialog(this, "feed", new Facebook.DialogListener() {
            @Override
            public void onComplete(Bundle values) {

            }

            @Override
            public void onFacebookError(FacebookError e) {

            }

            @Override
            public void onError(DialogError e) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void getProfileInfo() {
        mAsyncRunner.request("me", new AsyncFacebookRunner.RequestListener() {
            @Override
            public void onComplete(String response, Object state) {
                Log.d("profile", response);
                //String json = response;
                try{
                    JSONObject profile = new JSONObject(response);
                    //geting name of user
                   username = profile.getString("username");
                   first_name = profile.getString("first_name");
                   last_name = profile.getString("last_name");
                   birthday = profile.getString("birthday");
                   gender = profile.getString("gender");
                   email = profile.getString("email");
                   city = profile.getJSONObject("hometown").getString("name");
                   country = profile.getJSONObject("location").getString("name");
                   tel = "null";

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Name:" + " " + first_name + "Email:" + " " + email, Toast.LENGTH_LONG).show();
                        }
                    });


                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onIOException(IOException e, Object state) {

            }

            @Override
            public void onFileNotFoundException(FileNotFoundException e, Object state) {

            }

            @Override
            public void onMalformedURLException(MalformedURLException e, Object state) {

            }

            @Override
            public void onFacebookError(FacebookError e, Object state) {

            }
        });

    }

    private void loginToFacebook() {
        mPrefs = getPreferences(MODE_PRIVATE);
        String access_token = mPrefs.getString("access_token", null);
        long expires = mPrefs.getLong("access_expires", 0);

        if (access_token != null){
            facebook.setAccessToken(access_token);

        }

        if (expires != 0){
            facebook.setAccessExpires(expires);
        }

        if(!facebook.isSessionValid()){

            facebook.authorize(this,
                    new String[]{"email","publish_stream"},
                    new Facebook.DialogListener() {
                        @Override
                        public void onComplete(Bundle values) {
                            //function to handle complete event
                            //edit preferences and update facebook access_token
                            SharedPreferences.Editor editor = mPrefs.edit();
                            editor.putString("access_token", facebook.getAccessToken());
                            editor.putLong("access_expires", facebook.getAccessExpires());
                            editor.commit();
                        }

                        @Override
                        public void onFacebookError(FacebookError e) {

                        }

                        @Override
                        public void onError(DialogError e) {

                        }

                        @Override
                        public void onCancel() {

                        }
                    }

                    );

        }

    }
}
