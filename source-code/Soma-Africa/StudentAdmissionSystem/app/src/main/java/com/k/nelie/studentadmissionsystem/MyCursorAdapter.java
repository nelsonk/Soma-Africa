package com.k.nelie.studentadmissionsystem;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Nelie K on 3/1/2015.
 */
public class MyCursorAdapter extends CursorAdapter {


    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.schools, parent, false);

        ViewHolder myviewholder = new ViewHolder(view);
        view.setTag(myviewholder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder myviewholder = (ViewHolder)view.getTag();

                String name = cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_NAME));
                myviewholder.pschool.setText(name);
                myviewholder.prank.setText(cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_RANK)));

                myviewholder.pstatus.setText(cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_STATUS)));
   }
}
