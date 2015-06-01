package com.k.nelie.studentadmissionsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nelie K on 2/19/2015.
 */
public class MListAdapter extends BaseAdapter {
    private List<MListModel> items;
    private Context context;
    private int numItems = 0;

    public MListAdapter(List<MListModel> items, Context context) {
        this.items = items;
        this.context = context;
        this.numItems = items.size();
    }

    @Override
    public int getCount() {
        return numItems;
    }

    @Override
    public MListModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MListModel item = items.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.schools, parent, false);
        TextView pschool = (TextView) rowView.findViewById(R.id.tvschool);
        TextView prank= (TextView) rowView.findViewById(R.id.tvrank);
        TextView pstatus= (TextView) rowView.findViewById(R.id.tvstatus);

        String mschool= item.getSchool();
        String mrank= item.getRank();
        String mstatus= item.getStatus();

        pschool.setText(mschool);
        prank.setText(mrank);
        pstatus.setText(mstatus);

        return rowView;
    }


}
