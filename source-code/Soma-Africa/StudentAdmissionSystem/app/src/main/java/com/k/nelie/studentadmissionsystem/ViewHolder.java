package com.k.nelie.studentadmissionsystem;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Nelie K on 3/8/2015.
 */
public class ViewHolder {

    public final TextView pschool, prank, pstatus;

    public ViewHolder(View view) {
        pschool = (TextView) view.findViewById(R.id.tvschool);
        prank = (TextView) view.findViewById(R.id.tvrank);
        pstatus = (TextView) view.findViewById(R.id.tvstatus);
    }
}
