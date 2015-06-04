package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neliek.database.provider.MyDBContentProvider;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Nelie K on 2/19/2015.
 */
public class Welcome extends Activity implements View.OnClickListener {

    Button signin;
    EditText username,password;
    // An array to contain selection arguments
    String[] selectionArgs = null;
    String name,passwd;
    TextView signup,fbsignup;
    String comb,cut,school,type;
    SharedPreferences pref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome);
        signup = (TextView) findViewById(R.id.tvsignup);
        fbsignup = (TextView) findViewById(R.id.tvfbsignup);
        username = (EditText) findViewById(R.id.etusername);
        password = (EditText) findViewById(R.id.etpassword);
        signin = (Button) findViewById(R.id.bsignin);


        Intent intent = getIntent();
        comb = intent.getStringExtra("comb");
        cut = intent.getStringExtra("cut");
        school = intent.getStringExtra("school");
        type = intent.getStringExtra("type");
        signup.setOnClickListener(this);
        signin.setOnClickListener(this);
        fbsignup.setOnClickListener(this);
    }

    private void logins() {

        if (username.getText().toString().contentEquals("")){
            Toast.makeText(getBaseContext(), "username empty", Toast.LENGTH_LONG).show();
        }else if (password.getText().toString().contentEquals("")) {
                Toast.makeText(getBaseContext(), "password empty", Toast.LENGTH_LONG).show();
            }else{


                String[] projection = { DbClass.LOGIN_ID,
                        DbClass.LOGIN_NAME, DbClass.LOGIN_PASSWORD };
                selectionArgs = new String[]{ username.getText().toString() };

                Cursor cursor = getContentResolver().query(MyDBContentProvider.LOGIN_CONTENT_URI, projection, DbClass.LOGIN_NAME + " = ?", selectionArgs,
                        null);
                if(cursor == null){
                    Toast.makeText(getBaseContext(), "Error occurred during query", Toast.LENGTH_LONG).show();
                }else if(cursor.getCount() <1){
                    Toast.makeText(getBaseContext(), "user doesnt exits, please signup", Toast.LENGTH_LONG).show();
                }else {
                    cursor.moveToFirst();
                    name = cursor.getString(cursor
                            .getColumnIndexOrThrow(DbClass.LOGIN_NAME));
                    passwd= cursor.getString(cursor
                            .getColumnIndexOrThrow(DbClass.LOGIN_PASSWORD));
                    if (username.getText().toString().contentEquals(name)){
                        if(password.getText().toString().contentEquals(passwd)){
                            Intent i = new Intent(Welcome.this, Submit.class);
                            i.putExtra("username", username.getText().toString());
                            i.putExtra("comb", comb);
                            i.putExtra("cut", cut);
                            i.putExtra("school", school);
                            i.putExtra("type", type);
                            startActivity(i);

                        }else{
                            Toast.makeText(getBaseContext(), "Wrong password", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getBaseContext(), "Wrong username", Toast.LENGTH_LONG).show();
                    }

                }


            }



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bsignin) {
             logins();

        }else if (v.getId() == R.id.tvsignup){
            Intent sign = new Intent(Welcome.this, SignUp.class);
            sign.putExtra("comb", comb);
            sign.putExtra("cut", cut);
            sign.putExtra("school", school);
            sign.putExtra("type", type);
            startActivity(sign);
        }else if (v.getId() == R.id.tvfbsignup){

            Intent i = new Intent(Welcome.this, Facebooksignup.class);
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
            Intent i = new Intent(Welcome.this, MainActivity.class);
            startActivity(i);
        }

        return super.onMenuItemSelected(featureId, item);
    }

}
