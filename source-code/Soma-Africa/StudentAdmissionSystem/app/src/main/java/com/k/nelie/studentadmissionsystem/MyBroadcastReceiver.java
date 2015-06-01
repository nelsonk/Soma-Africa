package com.k.nelie.studentadmissionsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Nelie K on 3/8/2015.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    int count;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Extract data included in the Intent
        int access_count = intent.getIntExtra("message",0);


        count = count + access_count;

                 Toast.makeText(context, "Soma Africa has had " + count + " " + "more view", Toast.LENGTH_LONG).show();



    }



}
