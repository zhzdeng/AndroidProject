package com.example.deng.experimentfour;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by deng on 2016/10/19.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Fruit> list;

    public MyAdapter(Context context, List<Fruit> l) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(R.id.fruitName);
            viewHolder.picture = (ImageView)convertView.findViewById(R.id.fruitPicture);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.name.setText(list.get(position).getName());
        viewHolder.picture.setImageResource(list.get(position).getPictureId());
        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public ImageView picture;
    }
}
