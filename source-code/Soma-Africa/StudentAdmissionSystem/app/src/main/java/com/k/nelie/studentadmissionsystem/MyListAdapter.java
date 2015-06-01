package com.k.nelie.studentadmissionsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nelie K on 2/14/2015.
 */
public class MyListAdapter extends BaseAdapter {

    private List<MyListModel> items;
    private Context context;
    private int numItems = 0;

    public MyListAdapter(List<MyListModel> items, Context context) {
        this.items = items;
        this.context = context;
        this.numItems = items.size();
    }

    @Override
    public int getCount() {
        return numItems;
    }

    @Override
    public MyListModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyListModel item = items.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list, parent, false);
        TextView personName = (TextView) rowView.findViewById(R.id.tvtest);
        TextView personLocation= (TextView) rowView.findViewById(R.id.tvfine);

        String name= item.getProg();
        String location= item.getDate();

        personName.setText(name);
        personLocation.setText(location);

        return rowView;
    }
}
