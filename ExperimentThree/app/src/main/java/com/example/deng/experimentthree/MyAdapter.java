package com.example.deng.experimentthree;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.Context;

import java.util.List;

/**
 * Created by deng on 2016/10/13.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Contact> list;

    public MyAdapter(Context context, List<Contact> l) {
        list = l;
        this.context = context;
    }


    @Override
    public int getCount() {
        if (list == null) return 0;
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list == null) return null;
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
//      新声明一个View变量和ViewHolder变量
        View convertView;
        ViewHolder viewHolder;

        if (view == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.mainlistitem, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(R.id.mainListName);
            viewHolder.firstName = (TextView)convertView.findViewById(R.id.mainListFirstName);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.name.setText(list.get(position).getName());
        viewHolder.firstName.setText(list.get(position).getFirstName());
        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView firstName;
    }
}
































