package com.k.nelie.studentadmissionsystem;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nelson on 25/03/2015.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private HashMap<String, List<String>> reg_category;
    private Context ctx;
    private List<String> sch_list;

    private Activity activity;
    private ArrayList<Object> childtems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems;
    String child;

    public MyExpandableListAdapter(Context ctx, HashMap<String, List<String>> reg_category, List<String> sch_list) {
        this.ctx = ctx;
        this.reg_category = reg_category;
        this.sch_list = sch_list;

    }



    @Override
    public int getGroupCount() {
        return sch_list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return reg_category.get(sch_list.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return sch_list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return reg_category.get(sch_list.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String group_title = (String)getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.progress_sch, null);
        }

        TextView parent_view = (TextView)convertView.findViewById(R.id.lstsch);
        parent_view.setTypeface(null, Typeface.BOLD);
        parent_view.setText(group_title);
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String child_title = (String) getChild(groupPosition, childPosition);

       TextView comb = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             convertView = inflater.inflate(R.layout.progress_details, null);
        }
        comb = (TextView) convertView.findViewById(R.id.lstcomb);
        comb.setText(child_title);
       convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(activity, child_title,
                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
