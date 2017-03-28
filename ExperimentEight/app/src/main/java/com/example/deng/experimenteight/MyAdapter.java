package com.example.deng.experimenteight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deng.experimenteight.entity.BirthInfo;

import java.util.List;

/**
 * Created by deng on 2016/11/16.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<BirthInfo> list;

    public MyAdapter(Context context, List<BirthInfo> l) {
        list = l;
        this.context = context;
    }


    @Override
    public int getCount() {
        if (list == null) return 0;
        return list.size();
    }

    @Override
    public BirthInfo getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(R.id.itemName);
            viewHolder.birthday = (TextView)convertView.findViewById(R.id.itemBirthday);
            viewHolder.gift = (TextView)convertView.findViewById(R.id.itemGift);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.name.setText(list.get(position).getName());
        viewHolder.birthday.setText(list.get(position).getBirth());
        viewHolder.gift.setText(list.get(position).getGift());
        return convertView;
    }

    public void remove(int id) {
        if (list == null) return;
        list.remove(id);
        this.notifyDataSetChanged();
    }

    public List<BirthInfo> getList() {
        return list;
    }

    public void updateBirth(int id, String birth) {
        list.get(id).setBirth(birth);
        this.notifyDataSetChanged();
    }

    public void updateGift(int id, String gift) {
        list.get(id).setGift(gift);
        this.notifyDataSetChanged();
    }

    private class ViewHolder {
        public TextView name;
        public TextView birthday;
        public TextView gift;
    }
}
