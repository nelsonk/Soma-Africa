package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class Main extends Activity {

    private MyListAdapter  myadapter;
    List<MyListModel> myList=new ArrayList<MyListModel>();

    ListView main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        main = (ListView)findViewById(R.id.lvmain);
        String program ="Testing my list view";
        String time = "Is my app fine";
        myList.add(new MyListModel(program, time));

        myadapter = new MyListAdapter(myList, this);

        main.setAdapter(myadapter);

    }
}
