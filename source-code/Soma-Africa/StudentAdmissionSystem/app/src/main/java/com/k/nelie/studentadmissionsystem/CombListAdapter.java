package com.k.nelie.studentadmissionsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nelie K on 2/28/2015.
 */
public class CombListAdapter extends BaseAdapter {

    private List<CombListModel> items;
    private Context context;
    private int numItems = 0;

    public CombListAdapter(List<CombListModel> items, Context context) {
        this.items = items;
        this.context = context;
        this.numItems = items.size();
    }

    @Override
    public int getCount() {
        return numItems;
    }

    @Override
    public CombListModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CombListModel item = items.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.courselist, parent, false);
        TextView personComb = (TextView) rowView.findViewById(R.id.tvcomb);
        TextView personCut= (TextView) rowView.findViewById(R.id.tvcut);

        String comb= item.getComb();
        String cut= item.getCut();

        personComb.setText(comb);
        personCut.setText(cut);

        return rowView;
    }
}
