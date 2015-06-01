package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.neliek.database.provider.MyDBContentProvider;

import java.net.URI;

/**
 * Created by Nelie K on 3/7/2015.
 */
public class Submit extends Activity implements View.OnClickListener {

    TextView detail;
    Button attach,submit;
    String comb,cut,username,school,type;
    private NotificationManager mNotificationManager;
    private int notificationID = 100;
    private int numMessages = 0;
    private static final int PICK_FROM_GALLERY = 101;
    String attachmentFile;
   	Uri URI = null;
    int columnIndex;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit);
        detail = (TextView)findViewById(R.id.tvdetail);
        attach = (Button)findViewById(R.id.battach);
        submit = (Button)findViewById(R.id.bsubmit);
        submit.setEnabled(false);
        submit.setVisibility(View.INVISIBLE);


        Intent intent = getIntent();
        comb = intent.getStringExtra("comb");
        cut = intent.getStringExtra("cut");
        school = intent.getStringExtra("school");
        type = intent.getStringExtra("type");
        username = intent.getStringExtra("username");
         detail.setText("Applying for" + " " + comb + " " + "at" + " " + school + " " + "cutoff" + " " + cut);


            Toast.makeText(getBaseContext(), "First attach file to enable submit button", Toast.LENGTH_LONG).show();

        attach.setOnClickListener(this);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bsubmit){
            register();
            sendmail();
            alert();


        }else{
            openGallery();
            stopService(new Intent(getBaseContext(), MyService.class));

        }

    }

    private void sendmail() {
        try {
                                     String email = "nelskon@gmail.com";
                                      String subject = "School Application";
                                     String message = "New application";

                                       final Intent emailIntent = new Intent(
                                                             android.content.Intent.ACTION_SEND);
                                     emailIntent.setType("plain/text");
                                   emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                                                           new String[] { email });
                                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                                                         subject);
                                    if (URI != null) {
                                                 emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
                                       }
                                     emailIntent
                                                     .putExtra(android.content.Intent.EXTRA_TEXT, message);
                                  this.startActivity(Intent.createChooser(emailIntent,
                                                            "Sending email..."));

            Toast.makeText(this,
                    "Email successfully sent",Toast.LENGTH_LONG).show();

                                } catch (Throwable t) {
                                     Toast.makeText(this,"Request failed try again: " + t.toString(),Toast.LENGTH_LONG).show();
                                }


    }

    private void alert() {

        /* Invoking the default notification service */
        NotificationCompat.Builder  mBuilder =
                new NotificationCompat.Builder(this);

        mBuilder.setContentTitle("Registration");
        mBuilder.setContentText("Your Application has been successful sent");
        mBuilder.setTicker("Registration Alert!");
        mBuilder.setAutoCancel(true);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);

      /* Increase notification number every time a new notification arrives */
        mBuilder.setNumber(++numMessages);



      /* Creates an explicit intent for an Activity in your app */
        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

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

        Intent i = new Intent(Submit.this, MainActivity.class);
        startActivity(i);
    }

    private void signin() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Confirmation");
        alertDialogBuilder.setMessage("Please signup and signin to apply");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               Intent i = new Intent(Submit.this, Welcome.class);
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

    private void register() {
        Uri newUri = null;
        String status = "pending";
        String ref_id = "3737008";
        // Defines an object to contain the new values to insert
        ContentValues newValues = new ContentValues();

// Sets the values of each column and inserts the word.
        newValues.put(DbClass.REGISTRATION_SCHNAME, school);
        newValues.put(DbClass.REGISTRATION_COURSETYPE, type);
        newValues.put(DbClass.REGISTRATION_COMBINATION, comb);
        newValues.put(DbClass.REGISTRATION_USERNAME, username);
        newValues.put(DbClass.REGISTRATION_REF_ID, ref_id);
        newValues.put(DbClass.REGISTRATION_STATUS, status);


        newUri = getContentResolver().insert(
                MyDBContentProvider.REGISTRATION_CONTENT_URI,   // the user dictionary content URI
                newValues                          // the values to insert
        );

        Toast.makeText(getBaseContext(), newUri.toString() + " Records Submitted", Toast.LENGTH_LONG).show();
    }

    public void openGallery() {
                      Intent intent = new Intent();
        	              intent.setType("image/*");
        	              intent.setAction(Intent.ACTION_GET_CONTENT);
        	              intent.putExtra("return-data", true);
                      startActivityForResult(
                	                           Intent.createChooser(intent, "Complete action using"),
               	                           PICK_FROM_GALLERY);

        	       }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        	              if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            	                     /**
             	                      * Get Path
             	                      */
                                 Uri selectedImage = data.getData();
                                String[] filePathColumn = { MediaStore.Images.Media.DATA };

            	                     Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                                 cursor.moveToFirst();
                                 columnIndex = cursor.getColumnIndex(filePathColumn[0]);
           	                     attachmentFile = cursor.getString(columnIndex);
            	                     Log.e("Attachment Path:", attachmentFile);
            	                     URI = Uri.parse("file://" + attachmentFile);
            	                     cursor.close();

                              submit.setEnabled(true);
                              submit.setVisibility(View.VISIBLE);
           	              }
        	       }




}
