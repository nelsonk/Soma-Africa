package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.Selection;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nelie K on 3/2/2015.
 */
public class Contacts extends Activity implements View.OnClickListener {
    TextView contact_name, contact_number;
    Button updatecontact;
    String displayName;
    private static final int PICK_CONTACT_REQUEST = 0;
    Uri contactUri = Uri.parse("content://com.android.contacts/contacts");
    private Boolean isSavedInstanceState= false;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        updatecontact = (Button)findViewById(R.id.bupdatecontact);
        contact_name = (TextView)findViewById(R.id.tvcontactname);
        contact_number = (TextView)findViewById(R.id.tvcontactnumber);
        renderContact(null);


        if( savedInstanceState!= null){ // get saved state from bundle after a soft kill

            contact_name.setText(savedInstanceState.getString("name"));
            contact_number.setText(savedInstanceState.getString("number"));


        }
        else { // get saved state from preferences on first pass after a hard kill
            pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);


            contact_name.setText(pref.getString("name", ""));
            contact_number.setText(pref.getString("number", ""));

        }



        updatecontact.setOnClickListener(this);

    }


    private void renderContact(Uri uri) {
        if (uri == null) {
            contact_name.setText("");
            contact_number.setText("");

        } else {
            getDisplayName(uri);
            getMobileNumber(uri);

        }
    }

    private void getDisplayName(Uri uri) {
        displayName = null;
       Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {

            displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }
        cursor.close();
        contact_name.setText(displayName);


    }

    private void getMobileNumber(Uri uri) {
        String phoneNumber = null;

        Cursor phoneCursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER },
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ? ",
        new String[] { displayName },
                null
        );
        if (phoneCursor.moveToFirst()) {
            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER)
            );
        }
        phoneCursor.close();

        contact_number.setText(phoneNumber);


    }


    @Override
    public void onClick(View v) {


            Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
            pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
            startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);



    }



    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
               renderContact(intent.getData());

            }

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
            Intent i = new Intent(Contacts.this, MainActivity.class);
            startActivity(i);
        }

        return super.onMenuItemSelected(featureId, item);
    }



    @Override
    protected void onResume() {
        super.onResume();
        isSavedInstanceState= false;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.clear();
        outState.putString("name", contact_name.getText().toString());
        outState.putString("number", contact_number.getText().toString());
        isSavedInstanceState= true;
        super.onSaveInstanceState(outState); // save view state
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isSavedInstanceState) { // this is a HARD KILL, write to prefs
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("name", contact_name.getText().toString());
            editor.putString("number", contact_number.getText().toString());
            editor.commit();

        }

    }




}
