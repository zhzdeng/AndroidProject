package com.example.deng.experimentfour;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by deng on 2016/10/19.
 */
public class StaticActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.static_broadcast);

        // 初始化列表

        final List<Fruit> list = new ArrayList<Fruit>();
        list.add(new Fruit("apple", R.mipmap.apple));
        list.add(new Fruit("banana", R.mipmap.banana));
        list.add(new Fruit("cherry", R.mipmap.cherry));
        list.add(new Fruit("coco", R.mipmap.coco));
        list.add(new Fruit("kiwi", R.mipmap.kiwi));
        list.add(new Fruit("orange", R.mipmap.orange));
        list.add(new Fruit("pear", R.mipmap.pear));
        list.add(new Fruit("strawberry", R.mipmap.strawberry));
        list.add(new Fruit("watermelon", R.mipmap.watermelon));
        final MyAdapter myAdapter = new MyAdapter(this, list);
        ListView staticListView = (ListView)findViewById(R.id.staticListView);
        staticListView.setAdapter(myAdapter);

        staticListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 广播
                Intent intent = new Intent("staticBroadcast");
                Bundle bundle = new Bundle();
                bundle.putParcelable("fruit", list.get(position));
                intent.putExtras(bundle);
                sendBroadcast(intent);
            }
        });

    }
}
