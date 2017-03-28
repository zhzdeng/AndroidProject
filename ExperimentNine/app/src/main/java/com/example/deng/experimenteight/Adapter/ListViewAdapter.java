package com.example.deng.experimenteight.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.deng.experimenteight.MainActivity;
import com.example.deng.experimenteight.R;
import com.example.deng.experimenteight.entity.SuggestInfo;

import java.util.List;

/**
 * Created by deng on 2016/11/24.
 */
public class ListViewAdapter extends BaseAdapter {
        private Context context;
        private List<SuggestInfo> list;

    public ListViewAdapter(Context context, List<SuggestInfo> l) {
        list = l;
        this.context = context;
    }


    @Override
    public int getCount() {
        if (list == null) return 0;
        return list.size();
    }

    @Override
    public SuggestInfo getItem(int position) {
        if (list == null) return null;
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View convertView;
        ViewHolder viewHolder;

        if (view == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listviewitem, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.listFirstName);
            viewHolder.suggest = (TextView)convertView.findViewById(R.id.listSecondName);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.suggest.setText(list.get(position).getSuggest());
        return convertView;
    }

    private class ViewHolder {
        public TextView title;
        public TextView suggest;
    }
}
