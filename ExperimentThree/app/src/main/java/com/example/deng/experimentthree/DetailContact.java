package com.example.deng.experimentthree;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by deng on 2016/10/13.
 */
public class DetailContact extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        // 点击星星的业务逻辑

        ImageButton startButton = (ImageButton)findViewById(R.id.star);
        startButton.setTag("0");
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton star = (ImageButton)v;
                if (((String)star.getTag()).equals("0")) {
                    // 0 表示 空心
                    star.setImageResource(R.mipmap.full_star);
                    star.setTag("1");
                } else {
                    star.setImageResource(R.mipmap.empty_star);
                    star.setTag("0");
                }
            }
        });


        // 设置返回的业务逻辑
        ImageButton back = (ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回上一层页面的逻辑
                finish();
            }
        });



        ListView operationsList = (ListView)findViewById(R.id.detailList);
        String[] operations = new String[] {"编辑联系人", "分享联系人", "加入黑名单", "删除联系人"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.detaillistitem, operations);
        operationsList.setAdapter(arrayAdapter);

//      获得intent的数据
        Contact personal = null;
        try {
            personal = (Contact) getIntent().getSerializableExtra("contact");
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView name = (TextView)findViewById(R.id.detailName);
        TextView phone = (TextView)findViewById(R.id.detailPhone);
        TextView type = (TextView)findViewById(R.id.detailType);
        TextView belong = (TextView)findViewById(R.id.detailBelong);
        RelativeLayout topLayout = (RelativeLayout)findViewById(R.id.topView);

        name.setText(personal.getName());
        phone.setText(personal.getPhone());
        type.setText(personal.getType());
        belong.setText(personal.getBelong());
        topLayout.setBackgroundColor(Color.parseColor(personal.getBackgroundColor()));
    }

}
