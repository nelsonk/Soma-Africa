package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.neliek.database.provider.MyDBContentProvider;

/**
 * Created by Nelie K on 3/17/2015.
 */
public class LoginDetails extends Activity implements View.OnClickListener {

    Button enter;
    EditText username,passwd,password;
    private NotificationManager mNotificationManager;
    private int notificationID = 100;
    private int numMessages = 0;
    SharedPreferences pref;
    String comb, cut,type,school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.logindetails);
        username = (EditText)findViewById(R.id.etlusername);
        passwd = (EditText)findViewById(R.id.etlpassword);
        password = (EditText)findViewById(R.id.etlcpassword);
        enter = (Button)findViewById(R.id.blenter);

        Intent intent = getIntent();
        comb = intent.getStringExtra("comb");
        cut = intent.getStringExtra("cut");
        school = intent.getStringExtra("school");
        type = intent.getStringExtra("type");

        enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (passwd.getText().toString().contentEquals(password.getText().toString())) {

            // Defines a new Uri object that receives the result of the insertion
            Uri newUri;

// Defines an object to contain the new values to insert
            ContentValues newValues = new ContentValues();

// Sets the values of each column and inserts the word.
            newValues.put(DbClass.LOGIN_NAME, username.getText().toString());
            newValues.put(DbClass.LOGIN_PASSWORD, passwd.getText().toString());


            newUri = getContentResolver().insert(
                    MyDBContentProvider.LOGIN_CONTENT_URI,   // the user dictionary content URI
                    newValues                          // the values to insert
            );



               /* Invoking the default notification service */
            NotificationCompat.Builder  mBuilder =
                    new NotificationCompat.Builder(this);

            mBuilder.setContentTitle("Sign Up");
            mBuilder.setContentText("You have successfully signed up");
            mBuilder.setTicker("Sign Up Alert!");
            mBuilder.setAutoCancel(true);
            mBuilder.setSmallIcon(R.drawable.ic_launcher);

      /* Increase notification number every time a new notification arrives */
            mBuilder.setNumber(++numMessages);

         /* Creates an explicit intent for an Activity in your app */
            Intent resultIntent = new Intent(this, Welcome.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(Welcome.class);

      /* Adds the Intent that starts the Activity to the top of the stack */
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            mBuilder.setContentIntent(resultPendingIntent);

            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

      /* notificationID allows you to update the notification later on. */
            mNotificationManager.notify(notificationID, mBuilder.build());

            Intent i = new Intent(LoginDetails.this, Welcome.class);
            i.putExtra("comb", comb);
            i.putExtra("cut", cut);
            i.putExtra("school", school);
            i.putExtra("type", type);
            startActivity(i);



        }else{
            Toast.makeText(getBaseContext(), "password doesnt match", Toast.LENGTH_LONG).show();
        }



    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putString("username", username.getText().toString());
        editor.putString("passwd", passwd.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        passwd.setText(pref.getString("passwd",""));
        password.setText(pref.getString("password", ""));
        username.setText(pref.getString("username",""));
    }


    @Override
    protected void onStop() {
        super.onStop();

            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("username", username.getText().toString());
            editor.putString("passwd", passwd.getText().toString());
            editor.putString("password", password.getText().toString());
            editor.commit();



    }


    @Override
    protected void onRestart() {
        super.onRestart();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        passwd.setText(pref.getString("passwd",""));
        password.setText(pref.getString("password",""));
        username.setText(pref.getString("username",""));
    }


}
