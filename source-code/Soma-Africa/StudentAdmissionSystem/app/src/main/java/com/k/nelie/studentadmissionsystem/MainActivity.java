package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.neliek.database.provider.MyDBContentProvider;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import static java.security.KeyStore.*;

/**
 * Created by Nelie K on 2/19/2015.
 */
public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private MListAdapter  myadapter;
    List<MListModel> myList=new ArrayList<MListModel>();
    String result,username;
    ListView main;
    Button enter, search;
    SharedPreferences pref;
    AutoCompleteTextView actv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        main = (ListView)findViewById(R.id.top_list);
        enter= (Button)findViewById(R.id.benter);
        search= (Button)findViewById(R.id.bsearch);
        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        autocomplete();
        addschs();
        list();
        main.setOnItemClickListener(this);
        enter.setOnClickListener(this);
        search.setOnClickListener(this);

    }

    private void autocomplete() {
        String[] projection = { DbClass.COLUMN_ID,
                DbClass.COLUMN_NAME, DbClass.COLUMN_STATUS, DbClass.COLUMN_RANK };
        Cursor cursor = getContentResolver().query(MyDBContentProvider.CONTENT_URI, projection, null, null,
                null);

        String array[] = new String[cursor.getCount()];
        int i = 0;
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            String name = cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_NAME));

           array[i]= name;
            ArrayAdapter adapter = new ArrayAdapter
                    (this, android.R.layout.simple_list_item_1, array);
            actv.setAdapter(adapter);
            actv.setThreshold(1);//will start working from first character
            i++;
            cursor.moveToNext();
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }


    }

    private void addschs() {

        String[] projection = { DbClass.COLUMN_ID,
                DbClass.COLUMN_NAME, DbClass.COLUMN_STATUS, DbClass.COLUMN_RANK };
        Cursor cursor = getContentResolver().query(MyDBContentProvider.CONTENT_URI, projection, null, null,
                null);


        if(cursor == null){
            Toast.makeText(getBaseContext(), "Error occurred during query", Toast.LENGTH_LONG).show();
        }else if(cursor.getCount() <1){
            Toast.makeText(getBaseContext(), "No schools were in db", Toast.LENGTH_LONG).show();

            for (int i = 0; i < 4; i++) {

                String[] school = {"Gombe Secondary School", "St Marry's College Kitende", "Makerere College School", "Lubiri Secondary School"};
                String[] rank = {"2nd", "1st", "3rd", "4th"};
                String[] status = {"Admission on", "Admission on", "Admission off", "Admission on"};

                Uri newUri;

// Defines an object to contain the new values to insert
                ContentValues newValues = new ContentValues();

// Sets the values of each column and inserts the word.
                newValues.put(DbClass.COLUMN_NAME, school[i]);
                newValues.put(DbClass.COLUMN_RANK, rank[i]);
                newValues.put(DbClass.COLUMN_STATUS, status[i]);


                newUri = getContentResolver().insert(
                        MyDBContentProvider.CONTENT_URI,   // the user dictionary content URI
                        newValues                          // the values to insert
                );

            }

        }




    }

    private void list() {

        String[] projection = { DbClass.COLUMN_ID,
                DbClass.COLUMN_NAME, DbClass.COLUMN_STATUS, DbClass.COLUMN_RANK };
        Cursor cursor = getContentResolver().query(MyDBContentProvider.CONTENT_URI, projection, null, null,
                null);

        if (cursor.moveToFirst()) {

            do {

                String name = cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_NAME));
                String rank = cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_RANK));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_STATUS));
                myList.add(new MListModel(name, rank, status));
                myadapter = new MListAdapter(myList, this);

                main.setAdapter(myadapter);
            }while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }





    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.benter) {

            if (actv.getText().toString().contentEquals("")){
                Toast.makeText(getBaseContext(), "Please enter school name", Toast.LENGTH_LONG).show();
            }else {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Confirmation");
                alertDialogBuilder.setMessage("Admit to" + " " + actv.getText().toString());
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent("MyCustomIntent");

                        // add data to the Intent
                        intent.putExtra("message", 1);
                        intent.setAction("com.javacodegeeks.android.A_CUSTOM_INTENT");
                        sendBroadcast(intent);

                        Intent i = new Intent(MainActivity.this, Course.class);
                        i.putExtra("school", actv.getText().toString());
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

        }else if (v.getId() == R.id.bsearch){
            Intent i = new Intent(MainActivity.this, Your_grades.class);
            startActivity(i);
        }



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

       MListModel myLists =myadapter.getItem(position);
        String schools= myLists.getSchool();
        String ranks=myLists.getRank();
        String statusp =myLists.getStatus();
        result = schools + " " + ranks + " " + statusp;
        actv.setText(schools);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(
                R.menu.menu_sch, menu );
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

        if (item.getItemId() == R.id.status) {
            reg_status();
        }else if (item.getItemId() == R.id.addsch){
            add_sch();
        }else if (item.getItemId() == R.id.addcomb){
            add_comb();
        }

        return super.onMenuItemSelected(featureId, item);
    }

    private void add_comb() {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.progress_alert_dialog, null);
        final EditText name = (EditText)promptsView.findViewById(R.id.etprog_alert);
        name.setHint("Enter Admin Password");


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (name.getText().toString().contentEquals("nelson")) {

                    Intent i = new Intent(MainActivity.this, Add_combination.class);
                    startActivity(i);
                } else{
                    Toast.makeText(getBaseContext(), "Wrong password, Please try again", Toast.LENGTH_LONG).show();
                }

            }
        });
        alertDialogBuilder.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.show();

    }

    private void add_sch() {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.progress_alert_dialog, null);
        final EditText name = (EditText)promptsView.findViewById(R.id.etprog_alert);
        name.setHint("Enter Admin Password");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (name.getText().toString().contentEquals("nelson")) {

                    Intent i = new Intent(MainActivity.this, Add_sch.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getBaseContext(), "Wrong password, Please try again", Toast.LENGTH_LONG).show();
                }

            }
        });
        alertDialogBuilder.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.show();

    }

    private void reg_status() {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.progress_alert_dialog, null);
        final EditText name = (EditText)promptsView.findViewById(R.id.etprog_alert);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                username = name.getText().toString();
                Toast.makeText(getBaseContext(), "you are welcome" + " " + username, Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, Progress.class);
                i.putExtra("username", username);
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
        editor.putString("school", actv.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        if (pref.contains("school"))
        {
            actv.setText(pref.getString("school", ""));

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("school", actv.getText().toString());
            editor.commit();



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        if (pref.contains("school"))
        {
            actv.setText(pref.getString("school", ""));

        }
    }
}
