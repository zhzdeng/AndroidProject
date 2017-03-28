package com.example.deng.experimentthree;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deng on 2016/10/13.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        final List<Contact> list = new ArrayList<Contact>();
        list.add(new Contact("Aaron", "17715523654", "手机", "江苏苏州电信", "#BB4C3B"));
        list.add(new Contact("Elvis", "18825653224", "手机", "广东揭阳移动", "#c48d30"));
        list.add(new Contact("Edwin", "18854211875", "手机", "山东青岛移动", "#20A17B"));
        list.add(new Contact("Frank", "13955188541", "手机", "安徽合肥移动", "#BB4C3B"));
        list.add(new Contact("Joshua", "13621574410", "手机", "江苏苏州移动", "#c48d30"));
        list.add(new Contact("Ivan", "15684122771", "手机", "山东烟台联通", "#4469b0"));
        list.add(new Contact("Mark", "17765213579", "手机", "广东珠海电信", "#20A17B"));
        list.add(new Contact("Joseph", "13315466578", "手机", "河北石家庄电信", "#BB4C3B"));
        list.add(new Contact("Phoebe", "17895466428", "手机", "山东东营移动", "#c48d30"));
        final MyAdapter myAdapter = new MyAdapter(this, list);

        ListView mainList = (ListView)findViewById(R.id.mainList);
        mainList.setAdapter(myAdapter);

        // 长按事件
        final int[] index = {0};
        mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                index[0] = position;
                Dialog alertDelete = new AlertDialog.Builder(MainActivity.this).setTitle("删除联系人")
                        .setMessage("确定删除联系人" + list.get(position).getName() + "?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(index[0]);
                                myAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 不处理
                            }
                        })
                        .create();
                alertDelete.show();
                return true;
            }
        });

        // 单击事件处理
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailContact.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact", list.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

}